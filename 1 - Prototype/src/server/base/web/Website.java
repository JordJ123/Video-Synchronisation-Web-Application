package server.base.web;

import server.base.file.Directory;

/**
 * Class that represents a website.
 */
public class Website extends Directory {

    /**
     * Creates a website object.
     * @param directoryPath Path of the website directory
     */
    public Website(String directoryPath) {
        super(directoryPath);
    }

}
