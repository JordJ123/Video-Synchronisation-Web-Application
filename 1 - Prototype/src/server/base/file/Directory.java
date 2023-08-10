package server.base.file;

import server.BaseServer;

import java.io.File;
import java.io.IOException;

/**
 * Represents a directory on a file system.
 */
public class Directory extends FileExtended {

    //CONSTANTS
    private static final String DIRECTORY_ERROR = "File is not a directory";
    private static final String CLASS_ERROR
        = "No class for given mime type (%s)";

    /**
     * Creates a directory object.
     * @param directoryPath Path of the directory
     */
    public Directory(String directoryPath) {
        super(directoryPath);
        checkIfExists();
    }

    /**
     * Gets the files found in the directory.
     * @return Files in the directory
     * @throws IOException Error if a file can't be found
     * @throws ClassNotFoundException Error if there is no class for that file
     */
    public FileExtended[] getFiles() throws IOException, ClassNotFoundException {
        File[] directoryFiles = getFileObject().listFiles();
        FileExtended[] files = new FileExtended[directoryFiles.length];
        for (int i = 0; i < directoryFiles.length; i++) {
            files[i] = getFile(directoryFiles[i]);
        }
        return files;
    }

    public FileExtended getFile(int index) throws IOException,
        ClassNotFoundException {
        return getFile(getFileObject().listFiles()[index]);
    }

    private FileExtended getFile(File file) throws ClassNotFoundException,
        IOException {
        if (file.isDirectory()) {
            return new Directory(file.getAbsolutePath());
        } else {
            String mimeType = getMimeType(file);
            if (HTMLFile.isHTMLMimeType(mimeType)) {
                return new HTMLFile(file.getAbsolutePath());
            } else if (SoundFile.isSoundMimeType(mimeType)) {
                return new SoundFile(file.getAbsolutePath());
            } else if (TextFile.isTextMimeType(mimeType)) {
                return new TextFile(file.getAbsolutePath());
            } else if (VideoFile.isVideoMimeType(mimeType)) {
                return new VideoFile(file.getAbsolutePath());
            } else {
                throw new ClassNotFoundException(
                    String.format(CLASS_ERROR, mimeType));
            }
        }
    }

    /**
     * Checks if the directory exists and if not, creates it.
     */
    protected void checkIfExists() {
        if (getFileObject().exists()) {
            if (!getFileObject().isDirectory()) {
                throw new IllegalArgumentException(DIRECTORY_ERROR);
            }
        } else {
            getFileObject().mkdirs();
        }
    }

}
