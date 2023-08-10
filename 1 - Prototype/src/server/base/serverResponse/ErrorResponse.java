package server.base.serverResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Represents an error version of a server response
 */
public class ErrorResponse extends ServerResponse {

    //CONSTANT
    private static final String FAMILY_ERROR = "Status must be part of the " +
        "Client or Server status families (Provided family = %s)";

    /**
     * Creates an error server response.
     * @param errorStatus Status of the error
     * @param errorMessage Message about the error
     */
    public ErrorResponse(Response.Status errorStatus, String errorMessage) {
        super(errorStatus, MediaType.TEXT_PLAIN, errorMessage);
        if (errorStatus.getFamily() != Response.Status.Family.CLIENT_ERROR
            && errorStatus.getFamily() != Response.Status.Family.SERVER_ERROR) {
            throw new IllegalArgumentException(String.format(FAMILY_ERROR,
                errorStatus.getFamily()));
        }
    }

}
