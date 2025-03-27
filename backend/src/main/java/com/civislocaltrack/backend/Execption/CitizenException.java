package com.civislocaltrack.backend.Execption;

public class CitizenException extends RuntimeException {

    public enum CitizenErrorCode{
        CITIZEN_NOT_FOUND,
        CITIZEN_ALREADY_EXIST,
        CITIZEN_NOT_AUTHORIZED,
        CITIZEN_NOT_CREATED,
        CITIZEN_NOT_UPDATED,
        CITIZEN_NOT_DELETED
    }

    private CitizenErrorCode errorCodeCitizen;

    public CitizenException(String message, CitizenErrorCode errorCode) {
        super(message);
        this.errorCodeCitizen = errorCode;
    }

    public CitizenException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CitizenErrorCode getErrorCodeCitizen() {
        return errorCodeCitizen;
    }
    
}
