package server.base.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class HTMLFile extends FileExtended {

    private static final String[] MIME_TYPES = {"text/html"};
    private static final String FILE_ERROR = "File is not a html file";

    /**
     * Creates a html file object.
     * @param filePath Path of the file object
     */
    public HTMLFile(String filePath) throws IOException {
        super(filePath);
        checkIfExists();
    }

    /**
     * Checks if the mime type is a text mime type.
     * @return True if the mime type is a text mime type
     */
    public static boolean isHTMLMimeType(String mimeType) {
        for (String possibleType : MIME_TYPES) {
            if (mimeType.equals(possibleType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the contents of the html file.
     * @return Contents of the html file
     * @throws FileNotFoundException Error if the file can't be found
     */
    public File getContents() throws FileNotFoundException {
        return getFileObject();
    }

    /**
     * Checks if the file exists, and if not, creates it.
     */
    @Override
    protected void checkIfExists() throws IOException {
        if (getFileObject().exists()) {
            if (!isHTMLMimeType(getMimeType(getFileObject()))) {
                throw new IllegalArgumentException(FILE_ERROR);
            }
        } else {
            getFileObject().createNewFile();
        }
    }

}
