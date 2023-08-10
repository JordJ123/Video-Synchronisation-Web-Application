package server.base.web;

import server.base.file.HTMLFile;

import java.io.IOException;

/**
 * Class that represents a web page.
 */
public class WebPage extends HTMLFile {

    //CONSTANTS
    private static final String WEB_PAGE_PATH = "%s\\%s\\%s.html";

    /**
     * Creates a web page object.
     * @param website Website page is linked to
     * @param webPageName name of the web page
     */
    public WebPage(Website website, String webPageName) throws IOException {
        super(String.format(WEB_PAGE_PATH,
            website.getFilePath(), webPageName, webPageName));
    }

}
