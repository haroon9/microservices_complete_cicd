package de.uniba.dsg.charts.exceptions;

import de.uniba.dsg.charts.models.ErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class RemoteApiException extends WebApplicationException {
    public RemoteApiException(ErrorMessage message) {
        super(Response.status(500).entity(message).build());
    }
}
