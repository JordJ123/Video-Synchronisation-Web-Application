package server;

import jakarta.inject.Singleton;
import server.base.User;
import server.base.web.WebPage;
import server.base.room.Room;
import server.base.serverResponse.ErrorResponse;
import server.base.serverResponse.ServerResponse;
import server.base.serverResponse.WebPageResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Represents the server for the home page.
 */
@Singleton
@Path("/home")
public final class HomeServer {

    //CONSTANTS
    private static final String HOME_WEB_PAGE_NAME = "home";
    private static final String KICK_ERROR
        = "You can't join. You have been kicked from this room";
    private static final String LOGGED_IN_ERROR
        = "This user account is already logged into this room";
    private static final String DELETION_ERROR
        = "Failed to delete the user from the server";

    /**
     * Gets the Home web page.
     * @return Home web page
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getWebPage() throws IOException {
        return new WebPageResponse(new WebPage(BaseServer.getWebsite(),
            HOME_WEB_PAGE_NAME)).getResponse();
    }

    /**
     * Logs the user into the room that has the code given.
     * @param roomCode Code for the room to be logged in
     * @param user User to login into a room
     * @return Response stating if the login was a success or an error (message
     *         provided for the latter)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postRoomUser(@QueryParam("roomCode") String roomCode,
        User user) {
        try {
            Room.getReadWriteLock().writeLock().lock();
            if (Room.getRooms().get(roomCode) == null) {
                Room.getRooms().put(roomCode, new Room(roomCode));
            }
            Room room = Room.getRooms().get(roomCode);
            if (room.isBlockedUser(user)) {
                return new ErrorResponse(Response.Status.FORBIDDEN, KICK_ERROR)
                    .getResponse();
            }
            if (room.addUser(user)) {
                return new ServerResponse(Response.Status.OK).getResponse();
            } else {
                return new ErrorResponse(Response.Status.CONFLICT,
                    LOGGED_IN_ERROR).getResponse();
            }
        } catch (Exception error) {
            return new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR,
                error.getMessage()).getResponse();
        } finally {
            Room.getReadWriteLock().writeLock().unlock();
        }
    }

}
