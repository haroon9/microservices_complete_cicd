package de.uniba.dsg.charts;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
// based on https://stackoverflow.com/questions/45757856/how-can-i-get-the-stack-trace-when-500-server-error-happens-in-jersey/45758691#45758691
public class DebugMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable t) {
        t.printStackTrace();
        return Response.serverError()
                .entity(t.getMessage())
                .build();
    }
}