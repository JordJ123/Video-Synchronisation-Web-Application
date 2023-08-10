package server;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import server.base.file.Directory;
import server.base.room.Room;
import server.base.serverResponse.ServerResponse;
import server.base.web.Website;
import server.roomServer.RoomServer;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Base of the server.
 */
@ApplicationPath("/server")
public class BaseServer extends Application {

    //CONSTANTS
    private static final String WEBSITE_PATH = "C:\\Users\\Jorda\\University\\"
        + "CSP301 - Third Year Project\\Prototype\\src\\webapp";

    //Static Attributes
    private static Website website = new Website(WEBSITE_PATH);

    /**
     * Website containing the html pages;
     * @return Gets the website
     */
    public static Website getWebsite() {
        return website;
    }

    /*
     * Servers to add the base server.
     * @return Classes of the server
     */
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();

        //My Classes
        classes.add(Room.class);
        classes.add(LoginServer.class);
        classes.add(HomeServer.class);
        classes.add(CreateUserServer.class);
        classes.add(RoomServer.class);

        classes.add(ServerResponse.class);

        //Imported Classes
        classes.add(MultiPartFeature.class);

        return classes;
    }

}