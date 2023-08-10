package server;

import jakarta.inject.Singleton;
import server.base.User;
import server.base.web.WebPage;
import server.base.database.exception.DatabaseException;
import server.base.serverResponse.ErrorResponse;
import server.base.serverResponse.ServerResponse;
import server.base.serverResponse.WebPageResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static server.base.database.exception.DatabaseException.DUPLICATE_VALUE;

/**
 * Server for the Create User web page.
 * @author Jordan Jones
 */
@Singleton
@Path("/createUser")
public final class CreateUserServer {

    //CONSTANTS
    private static final String CREATE_USER_WEB_PAGE_NAME = "createUser";
    private static final String DUPLICATE_USERNAME_ERROR = "Username already in"
        + " use";

    /**
     * Gets the Create User web page.
     * @return Create User web page
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getWebPage() throws IOException {
        return new WebPageResponse(new WebPage(BaseServer.getWebsite(),
            CREATE_USER_WEB_PAGE_NAME)).getResponse();
    }

    /**
     * Creates a new user in the database.
     * @param username New user's username
     * @param password New user's password
     * @return Response containing if the new user was created or not
     */
    @POST
    public Response postNewUser(
        @FormParam("username") String username,
        @FormParam("password") String password) {
        try {
            User.getReadWriteLock().writeLock().lock();
            new User(username, password).save();
            return new ServerResponse(Response.Status.OK).getResponse();
        } catch (ClassNotFoundException | DatabaseException databaseError) {
            if (databaseError.getMessage().equals(
                DUPLICATE_VALUE.getErrorMessage())) {
                return new ErrorResponse(Response.Status.CONFLICT,
                    DUPLICATE_USERNAME_ERROR).getResponse();
            }
            return new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR,
                databaseError.getMessage()).getResponse();
        } finally {
            User.getReadWriteLock().writeLock().unlock();
        }
    }

}
