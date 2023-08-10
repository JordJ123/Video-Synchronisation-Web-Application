package server.base.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Represents a text file.
 */
public class TextFile extends FileExtended {

    //CONSTANTS
    private static final String[] MIME_TYPES = {"text/plain"};
    private static final String FILE_ERROR = "File is not a text file";

    /**
     * Creates a text file object.
     * @param filePath Path of the text file
     */
    public TextFile(String filePath) {
        super(filePath);
    }

    /**
     * Checks if the mime type is a text mime type.
     * @return True if the mime type is a text mime type
     */
    public static boolean isTextMimeType(String mimeType) {
        for (String possibleType : MIME_TYPES) {
            if (mimeType.equals(possibleType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the contents of the text file.
     * @param text Text to be added to the text file
     * @throws FileNotFoundException Error if the file can't be found
     */
    public void setContents(String text) throws FileNotFoundException {
        PrintWriter textFile = new PrintWriter(getFileObject());
        textFile.print(text);
        textFile.close();
    }

    /**
     * Gets the contents of the text file.
     * @return Contents of the text file
     * @throws FileNotFoundException Error if the file can't be found
     */
    public Scanner getContents() throws FileNotFoundException {
        return new Scanner(getFileObject());
    }

    /**
     * Checks if the file exists and if not, creates it.
     */
    protected void checkIfExists() throws IOException {
        if (getFileObject().exists()) {
            if (!isTextMimeType(getMimeType(getFileObject()))) {
                throw new IllegalArgumentException(FILE_ERROR);
            }
        } else {
            getFileObject().createNewFile();
        }
    }

}
