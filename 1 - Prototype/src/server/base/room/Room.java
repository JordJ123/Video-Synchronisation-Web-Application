package server.base.room;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import server.base.User;
import server.base.file.VideoFile;

/**
 * Represents a room on the server.
 */
public class Room {

    //CONSTANTS
    private static final String FILE_PATH = "C:\\Users\\Jorda\\glassfish5\\"
        + "glassfish\\domains\\domain1\\docroot\\videos\\%s.mp4";
    private static final String URL
        = "http://192.168.1.249:8080/videos/%s.mp4";

    //Static Attributes
    private static HashMap<String, Room> rooms = new HashMap<>();
    private static ReentrantReadWriteLock readWriteLock
        = new ReentrantReadWriteLock();

    //Attributes
    private String code;
    private Map<String, Boolean> users = new HashMap<>();
    private ArrayList<User> blockedUsers = new ArrayList<>();
    private String videoURL;

    /**
     * Creates a room.
     * @param code Code for the room
     */
    public Room(String code) {
        setCode(code);
        setVideoURL(String.format(URL, code));
    }

    /**
     * Gets the rooms on the server.
     * @return Rooms on the server
     */
    public static HashMap<String, Room> getRooms() {
        return rooms;
    }

    /**
     * Gets the read write lock for the rooms data.
     * @return Read write lock for the rooms data
     */
    public static ReentrantReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    /**
     * Sets the code for the room.
     * @param code Code for the room
     */
    private void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the url of the video file.
     * @param videoURL Video file url
     */
    private void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    /**
     * Gets the code for the room.
     * @return Code for the room
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the users logged into the room.
     * @return Users logged into the room
     */
    public Map<String, Boolean> getUsers() {
        return users;
    }

    /**
     * Gets the url of the video file.
     * @return Url of the video file
     */
    public String getVideoURL() {
        return videoURL;
    }

    /**
     * Logs a user from the room.
     * @param user User
     * @return True if successfully added the user to the room
     */
    public boolean addUser(User user) throws IOException {
        if (!users.containsKey(user.getUsername())) {
            if (isEmpty()) {
                users.put(user.getUsername(), true);
            } else {
                users.put(user.getUsername(), false);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes a user from the room.
     * @param user user
     * @return True if successfully deleted the user from the room
     */
    public boolean deleteUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return users.remove(user.getUsername()) != null;
        } else {
            return false;
        }

    }

    /**
     * Checks if the room has the user.
     * @param user User to check
     * @return True if the user is logged into this room
     */
    public boolean hasUser(User user) {
        return users.containsKey(user.getUsername());
    }

    /**
     * Checks if the user is an admin in this room.
     * @param user User to check
     * @return True if the user is an admin
     */
    public boolean isUserAdmin(User user) {
        return users.get(user.getUsername());
    }

    /**
     * Checks if the room has no users.
     * @return True if room has no users
     */
    public boolean isEmpty() {
        return users.isEmpty();
    }

    /**
     * Blocks the user from logging into this room.
     * @param user User to block
     */
    public void blockUser(User user) {
        blockedUsers.add(user);
    }

    /**
     * Checks if a user is blocked.
     * @param user User to check
     * @return True if the user is blocked
     */
    public boolean isBlockedUser(User user) {
        return blockedUsers.contains(user);
    }

    /**
     * Updates the video for the room.
     * @param inputStream Video stream
     * @param fileDetail Video details
     * @throws IOException Error if can't find directory
     */
    public void updateVideo(InputStream inputStream,
        FormDataContentDisposition fileDetail) throws IOException {
        deleteVideo();
        String filePath = String.format(FILE_PATH, getCode());
        try {
            int read;
            byte[] bytes = new byte[1024];
            OutputStream out = new FileOutputStream(filePath);
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        new VideoFile(filePath);
        setVideoURL(String.format(URL, getCode()));
    }

    /**
     * Deletes the video from the room.
     * @return True if the video was successfully deleted
     */
    public boolean deleteVideo() {
        return new File(String.format(FILE_PATH, getCode())).delete();
    }

}
