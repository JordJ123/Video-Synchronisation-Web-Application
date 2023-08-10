package server;

import jakarta.inject.Singleton;
import server.base.User;
import server.base.web.WebPage;
import server.base.serverResponse.ErrorResponse;
import server.base.serverResponse.ServerResponse;
import server.base.serverResponse.WebPageResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Server for the Login web page.
 */
@Singleton
@Path("/login")
public final class LoginServer {

    //WEB PAGE
    private static final String LOGIN_WEB_PAGE_NAME = "login";

    //ERRORS
    private static final String DETAILS_ERROR
        = "Username and/or password is incorrect";
    private static final String LOGGED_IN_ERROR
        = "This user is already logged into the server";

    /**
     * Gets the Login web page.
     * @return Login web page
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getWebPage() throws IOException {
        return new WebPageResponse(new WebPage(BaseServer.getWebsite(),
            LOGIN_WEB_PAGE_NAME)).getResponse();
    }

    /**
     * Logs a new user onto the server.
     * @param username Name of the user account
     * @param password Password of the user account
     * @return Response stating if the login was a success or an error (message
     *         provided for the latter)
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postActiveUser(
        @FormParam("username") String username,
        @FormParam("password") String password) {
        try {
            User user = new User(username, password);
            User.getReadWriteLock().writeLock().lock();
            if (user.areDetailsCorrect()) {
                return new ServerResponse(Response.Status.OK,
                    MediaType.APPLICATION_JSON, user).getResponse();
            } else {
                return new ErrorResponse(Response.Status.NOT_FOUND,
                    DETAILS_ERROR).getResponse();
            }
        } catch (Exception error) {
            return new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR,
                error.getMessage()).getResponse();
        } finally {
            User.getReadWriteLock().writeLock().unlock();
        }
    }

}
