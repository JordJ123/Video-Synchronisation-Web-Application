package server.base.file;

import java.io.IOException;

public class SoundFile extends FileExtended {

    //CONSTANTS
    private static final String[] MIME_TYPES = {"audio/mpeg"};
    private static final String FILE_ERROR = "File is not a sound file";

    /**
     * Creates a sound file object.
     * @param filePath Path of the file object
     */
    public SoundFile(String filePath) throws IOException {
        super(filePath);
        checkIfExists();
    }

    /**
     * Checks if the mime type is a text mime type.
     * @return True if the mime type is a text mime type
     */
    public static boolean isSoundMimeType(String mimeType) {
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
            if (!isSoundMimeType(getMimeType(getFileObject()))) {
                throw new IllegalArgumentException(FILE_ERROR);
            }
        } else {
            getFileObject().createNewFile();
        }
    }

}
