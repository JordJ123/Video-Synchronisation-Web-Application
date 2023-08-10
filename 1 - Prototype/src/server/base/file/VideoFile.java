package server.base.file;

import java.io.IOException;

/**
 * Represents a video file.
 */
public class VideoFile extends FileExtended {

    //CONSTANTS
    private static final String[] MIME_TYPES = {"video/mp4"};
    private static final String FILE_ERROR = "File is not a video file";

    /**
     * Creates a extended file object.
     * @param filePath Path of the file object
     */
    public VideoFile(String filePath) throws IOException {
        super(filePath);
        checkIfExists();
    }

    /**
     * Checks if the mime type is a video mime type.
     * @param mimeType File mimeType
     * @return True if the mime type is a video mime type
     */
    public static boolean isVideoMimeType(String mimeType) {
        for (String possibleType : MIME_TYPES) {
            if (mimeType.equals(possibleType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the file exists, and if not, creates it.
     */
    @Override
    protected void checkIfExists() throws IOException {
        if (getFileObject().exists()) {
            if (!isVideoMimeType(getMimeType(getFileObject()))) {
                throw new IllegalArgumentException(FILE_ERROR);
            }
        } else {
            getFileObject().createNewFile();
        }
    }

}
