package com.civislocaltrack.backend.Execption;

public class AdministratorException extends RuntimeException {

    public enum AdministratorErrorCode{
        ADMINISTRATOR_NOT_FOUND,
        ADMINISTRATOR_ALREADY_EXIST,
        ADMINISTRATOR_NOT_AUTHORIZED,
        ADMINISTRATOR_NOT_CREATED,
        ADMINISTRATOR_NOT_UPDATED,
        ADMINISTRATOR_NOT_DELETED
    }

    private AdministratorErrorCode errorCodeAdministrator;

    public AdministratorException(String message, AdministratorErrorCode errorCode) {
        super(message);
        this.errorCodeAdministrator = errorCode;
    }

    public AdministratorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AdministratorErrorCode getErrorCodeAdministrator() {
        return errorCodeAdministrator;
    }
    
}
