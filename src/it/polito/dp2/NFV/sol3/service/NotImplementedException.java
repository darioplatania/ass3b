package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotImplementedException extends WebApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotImplementedException(String message) {
		
		super(Response.status(Response.Status.fromStatusCode(501)).entity(message).type(MediaType.TEXT_PLAIN).build());
		
	}

}
