package server.roomServer;

import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import server.BaseServer;
import server.base.User;
import server.base.web.WebPage;
import server.base.room.Room;
import server.base.serverResponse.ErrorResponse;
import server.base.serverResponse.ServerResponse;
import jakarta.inject.Singleton;
import server.base.serverResponse.WebPageResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Singleton
@Path("/room")
public final class RoomServer {

    //CONSTANTS
    private static final String ROOM_WEB_PAGE_NAME = "room";
    private static final String GET_ROOM_DETAILS_ERROR = "Failed to get room"
        + "details. Room does not exist or user is not logged into this room";
    private static final String POST_VIDEO_FILE_ERROR = "File uploaded is not "
        + "a video file";
    private static final String VIDEO_DELETION_ERROR = "Video deletion failed";
    private static final String DELETE_USER_ERROR = "Failed to delete user."
        + "Room does not exist or user is not logged into this room";

    /**
     * Gets the Room web page.
     * @return Room web page
     */
    @GET
    @Path("/{roomCode}")
    @Produces(MediaType.TEXT_HTML)
    public Response getWebPage() throws IOException {
        return new WebPageResponse(new WebPage(BaseServer.getWebsite(),
            ROOM_WEB_PAGE_NAME)).getResponse();
    }

    /**
     * Gets the details about the room of the given room code.
     * @param roomCode Code for the wanted room
     * @param userJson User attempting to log into the room
     * @return Details of the room
     */
    @GET
    @Path("/{roomCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomDetails(@PathParam("roomCode") String roomCode,
        @QueryParam("user") String userJson) throws IOException {
        User user = new ObjectMapper().readValue(userJson, User.class);
        try {
            Room.getReadWriteLock().readLock().lock();
            if (Room.getRooms().containsKey(roomCode)) {
                Room room = Room.getRooms().get(roomCode);
                if (room.hasUser(user)) {
                    return new ServerResponse(Response.Status.OK,
                        MediaType.APPLICATION_JSON, room).getResponse();
                }
            }
            return new ErrorResponse(Response.Status.NOT_FOUND,
                GET_ROOM_DETAILS_ERROR).getResponse();
        } finally {
            Room.getReadWriteLock().readLock().unlock();
        }
    }

    /**
     * Blocks a user from the given room code.
     * @param roomCode Code of the room to block user from
     * @param username Username of user to block
     * @return Response stating the block was successful
     */
    @POST
    @Path("/{roomCode}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postRoomBlock(@PathParam("roomCode") String roomCode,
        String username) {
        try {
            Room.getReadWriteLock().writeLock().lock();
            Room.getRooms().get(roomCode).blockUser(new User(username, ""));
            return new ServerResponse(Response.Status.OK).getResponse();
        } finally {
            Room.getReadWriteLock().writeLock().unlock();
        }
    }

    /**
     * Uploads a video for the given room.
     * @param roomCode Code to upload the video for
     * @param inputStream Video file
     * @param fileDetail Details about the video file
     * @return Response if the upload is successful
     */
    @POST
    @Path("/{roomCode}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response postRoomVideo(
        @PathParam("roomCode") String roomCode,
        @FormDataParam("file") InputStream inputStream,
        @FormDataParam("file") FormDataContentDisposition fileDetail) {
        try {
            Room.getReadWriteLock().writeLock().lock();
            Room.getRooms().get(roomCode).updateVideo(inputStream, fileDetail);
            return new ServerResponse(Response.Status.OK, MediaType.TEXT_PLAIN,
                Room.getRooms().get(roomCode).getVideoURL()).getResponse();
        } catch (IllegalArgumentException error) {
            Room.getRooms().get(roomCode).deleteVideo();
            return new ErrorResponse(Response.Status.NOT_ACCEPTABLE,
                POST_VIDEO_FILE_ERROR).getResponse();
        } catch (Exception error) {
            return new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR,
               error.getMessage()).getResponse();
        } finally {
            Room.getReadWriteLock().writeLock().unlock();
        }
    }

    /**
     * Removes the video from the given room code.
     * @param roomCode Code of the wanted room
     * @return Response stating if the video was deleted or not
     */
    @DELETE
    @Path("/{roomCode}")
    public Response deleteRoomVideo(
        @PathParam("roomCode") String roomCode) {
        try {
            Room.getReadWriteLock().writeLock().lock();
            if (Room.getRooms().get(roomCode).deleteVideo()) {
                return new ServerResponse(Response.Status.OK).getResponse();
            } else {
                return new ErrorResponse(Response.Status.EXPECTATION_FAILED,
                    VIDEO_DELETION_ERROR).getResponse();
            }
        } finally {
            Room.getReadWriteLock().writeLock().unlock();
        }
    }

    /**
     * Removes the user from the given room.
     * @param roomCode Code for the wanted room
     * @param userJson User to deleted
     * @return Response stating if the user was deleted or not
     */
    @DELETE
    @Path("/{roomCode}/{user}")
    public Response deleteRoomUser(@PathParam("roomCode") String roomCode,
        @PathParam("user") String userJson) throws IOException {
        User user = new ObjectMapper().readValue(userJson, User.class);
        try {
            Room.getReadWriteLock().writeLock().lock();
            if (Room.getRooms().containsKey(roomCode)) {
                Room room = Room.getRooms().get(roomCode);
                boolean isUserAdmin = room.isUserAdmin(user);
                if (room.deleteUser(user)) {
                    if (room.isEmpty() || isUserAdmin) {
                        room.deleteVideo();
                        Room.getRooms().remove(roomCode);
                    }
                    return new ServerResponse(Response.Status.OK,
                        MediaType.TEXT_PLAIN, isUserAdmin).getResponse();
                }
            }
            return new ErrorResponse(Response.Status.NOT_FOUND,
                DELETE_USER_ERROR).getResponse();
        } finally {
            Room.getReadWriteLock().writeLock().unlock();
        }
    }

}
