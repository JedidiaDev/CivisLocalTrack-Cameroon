package com.civislocaltrack.backend.Execption;


/*Cette class Java contient toutes les erreurs qu'on peut
 * trouver lors de la manipulation d'un fichier par un
 * citoyen ou un administrateur
 */
public class FileException extends RuntimeException {

    public enum FileErrorCode{
        OVER_SIZE,
        INVALID_FORMAT,
        FILE_NOT_FOUND,
        UPLOAD_FAILED,
        DELETE_FAILED,
        DOWNLOAD_FAILED,
        CONTENT_FILE_NOT_AUTHORIZED
    }

    private FileErrorCode errorCodeFile;

    public FileException(String message, FileErrorCode errorCode) {
        super(message);
        this.errorCodeFile = errorCode;
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public FileErrorCode getErrorCodeFile() {
        return errorCodeFile;
    }
}