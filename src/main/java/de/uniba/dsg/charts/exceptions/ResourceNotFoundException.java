package de.uniba.dsg.charts.exceptions;

import de.uniba.dsg.charts.models.ErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ResourceNotFoundException extends WebApplicationException {
    public ResourceNotFoundException(ErrorMessage message) {
        super(Response.status(404).entity(message).build());
    }
}
