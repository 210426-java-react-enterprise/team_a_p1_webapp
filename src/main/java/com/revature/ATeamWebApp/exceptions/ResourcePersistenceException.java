package com.revature.ATeamWebApp.exceptions;

public class ResourcePersistenceException extends DataSourceException {

    public ResourcePersistenceException() {
        super("There was a problem when trying to persist the resource. Check the logs for more details.");
    }

    public ResourcePersistenceException(String message) {
        super(message);
    }

}
