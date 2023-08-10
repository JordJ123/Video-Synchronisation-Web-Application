package server.roomServer;
import server.base.room.Room;
import server.base.room.RoomUpdate;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;

/**]
 * Class representing web sockets for the room web page.
 * @author Jordan Jones
 */
@ServerEndpoint("/webSocket/room/{roomCode}/{username}/{admin}")
public class RoomWebSocket {

    //CONSTANTS
    private static final String JSON_STRING
        = "{\"updateType\":\"%s\", \"information\":\"%s\"}";

    //Variables
    private static HashMap<String, HashMap<String, Session>> sessions
        = new HashMap<>();

    /**
     * Code that runs when a web socket is created.
     * @param session Web page connected to the web socket
     * @param roomCode Code of the room linked to the web page
     * @param username Username of the user linked to the web page
     */
    @OnOpen
    public void onOpen(Session session,
        @PathParam("roomCode") String roomCode,
        @PathParam("username") String username) {
        Room.getReadWriteLock().writeLock().lock();
        if (!sessions.containsKey(roomCode)) {
            sessions.put(roomCode, new HashMap<>());
        }
        sessions.get(roomCode).put(username, session);
        sendToRoomMembers(session, roomCode,
            new RoomUpdate("addUser", username));
        Room.getReadWriteLock().writeLock().unlock();
    }

    /**
     * Code that runs when the room sends a message.
     * @param session Web page connected to the web socket
     * @param roomCode Code of the room linked to the web page
     * @param roomUpdate Details of the update message
     * @throws IOException Error with the websocket
     * @throws EncodeException Error with the websocket
     */
    @OnMessage
    public void onMessage(Session session,
        @PathParam("roomCode") String roomCode, String roomUpdate)
        throws IOException, EncodeException {
        Room.getReadWriteLock().writeLock().lock();
        for (Session roomMember : sessions.get(roomCode).values()) {
            if (roomMember != session) {
                roomMember.getBasicRemote().sendObject(roomUpdate);
            }
        }
        Room.getReadWriteLock().writeLock().unlock();
    }

    /**
     * Code that runs when a web socket is closed.
     * @param session Web page connected to the web socket
     * @param roomCode Code of the room linked to the web page
     * @param username Username of the user linked to the web page
     */
    @OnClose
    public void onClose(Session session, @PathParam("roomCode") String roomCode,
        @PathParam("username") String username,
        @PathParam("admin") String admin) {
        Room.getReadWriteLock().writeLock().lock();
        sendToRoomMembers(session, roomCode,
            new RoomUpdate("deleteUser", username));
        sessions.get(roomCode).remove(username);
        if (sessions.get(roomCode).isEmpty() || admin.equals("true")) {
            sendToRoomMembers(session, roomCode, new RoomUpdate("close", ""));
            sessions.remove(roomCode);
        }
        Room.getReadWriteLock().writeLock().unlock();
    }

    /**
     * Sends a room update to all of the other web sockets.
     * @param session Web page connected to the web socket
     * @param roomCode Code of the room linked to the web page
     * @param roomUpdate Details of the update message
     */
    private void sendToRoomMembers(Session session, String roomCode,
        RoomUpdate roomUpdate) {
        for (Session roomMember : sessions.get(roomCode).values()) {
            if (roomMember != session) {
                try {
                    roomMember.getBasicRemote().sendObject(
                        String.format(JSON_STRING, roomUpdate.getUpdateType(),
                            roomUpdate.getInformation()));
                } catch (EncodeException | IOException error) {
                    //
                }
            }
        }
    }

}
