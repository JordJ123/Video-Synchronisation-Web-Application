package server.base.file;

import org.apache.tika.Tika;
import java.io.*;

/**
 * Represents a file with extra methods.
 */
public abstract class FileExtended {

    //Attributes
    private File file;

    /**
     * Creates a extended file object.
     * @param filePath Path of the file object
     */
    public FileExtended(String filePath) {
        setFileObject(filePath);
    }

    /**
     * Sets the file that is being extended upon.
     * @param filePath Path of the file
     */
    protected void setFileObject(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Gets the file that is being extended upon.
     * @return filePath File that is being extended upon
     */
    protected File getFileObject() {
        return file;
    }

    /**
     * Gets the file path from the file extended upon.
     * @return file path from the file extended upon
     */
    public String getFilePath() {
        return getFileObject().getAbsolutePath();
    }

    /**
     * Gets the file name from the file extended upon.
     * @return File name from the file extended upon
     */
    public String getFileName() {
        return getFileObject().getName();
    }

    /**
     * Gets the file type of the given file.
     * @return File type
     * @throws IOException Error if file does not actually exist
     */
    protected static String getMimeType(File file) throws IOException {
        return new Tika().detect(file);
    }

    /**
     * Checks if the file exists, and if not, creates it.
     */
    protected abstract void checkIfExists() throws IOException;

}
