package server.base.serverResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServerResponse {

    Response response;

    public ServerResponse(Response.Status status, String mediaType,
        Object entity) {
        setResponse(status, mediaType, entity);
    }

    public ServerResponse(Response.Status status) {
        setResponse(status, MediaType.TEXT_PLAIN, null);
    }

    private void setResponse(Response.Status status, String mediaType,
        Object entity) {
        response = Response
            .status(status)
            .header("Access-Control-Allow-Origin", "*")
            .type(mediaType)
            .entity(entity)
            .build();
    }

    public Response getResponse() {
        return response;
    }

}
