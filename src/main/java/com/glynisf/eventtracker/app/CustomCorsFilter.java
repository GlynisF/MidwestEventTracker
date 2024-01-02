package com.glynisf.eventtracker.app;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CustomCorsFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		MultivaluedMap<String, Object> headers = responseContext.getHeaders();

		// Allow cross-origin resource sharing for all origins, methods, and headers
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		headers.add("Access-Control-Allow-Credentials", "true");
	}
}