package server.base.serverResponse;

import server.base.web.WebPage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;

/**
 * Represents a server response that sends a web page.
 * @author Jordan Jones
 */
public class WebPageResponse extends ServerResponse {

    /**
     * Creates a web page response.
     * @param webPage Web page to send
     */
    public WebPageResponse(WebPage webPage) throws FileNotFoundException {
        super(Response.Status.OK, MediaType.TEXT_HTML, webPage.getContents());
    }

}
