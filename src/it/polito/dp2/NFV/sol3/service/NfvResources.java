package it.polito.dp2.NFV.sol3.service;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;

import io.swagger.annotations.*;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.sol3.jaxb.*;


@Path("np")
public class NfvResources {
	
	//private NfvInitializer nfvInitializer = null;
	private Neo4JDB neo4j;
	private NfvDeployer nfv;
	
	private static boolean loadnffg = false;
	
	public NfvResources() throws DatatypeConfigurationException, ServiceException, NfvReaderException{
		//nfvInitializer = NfvInitializer.newInstance();
		
		if(neo4j == null)
			neo4j = new Neo4JDB();
		if(!loadnffg) {
			if(nfv == null) {
				nfv = new NfvDeployer();
				nfv.serializer();
				loadnffg = true;
			}
		}
	}
	
	

	 /** 
	 * This method allows to get all NFFGS stored in the NffgService 
	 * Some informations are stored into NffgService memory, others into Neo4JDB
	 * @return  the List of Nffg stored in the service
	 * @throw 	InternalServerErrorException
	 */
	
	@GET
	@Path("nffgs")
	@ApiOperation(value = "Get the set of all nffgs", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
    @Produces(MediaType.APPLICATION_XML)
	public NffgC getNffgs(){
		NffgC retval = new NffgC(); 
		retval.getNffgImpl().addAll(neo4j.getNffgs());
		return retval;

	}
	
	@GET
	@Path("nffgs/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@ApiOperation(value = "Get the single Nffg", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	public NffgImpl getNffg(@PathParam("id") String id){
		
		NffgImpl 	retval  = neo4j.getNffg(id);
		
		if(retval == null)
			throw new NotFoundException();
		return retval;
		
	}
	
	@GET
	@Path("nodes/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@ApiOperation(value = "Get Nodes", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	public NodeImpl getNode(@PathParam("id") String id){
		
		NodeImpl retval  = neo4j.getNode(id);
		
		if(retval == null)
			throw new NotFoundException();
		return retval;
		
	}
	
	@GET
	@Path("nodes/{id}/reachablehosts")
	@ApiOperation(value = "Get the set of all hosts", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
    @Produces(MediaType.APPLICATION_XML)
	public HostC getReachableHosts(@PathParam("id") String id){
		HostC retval = new HostC(); 
		retval.getHostImpl().addAll(neo4j.getReachableHosts(id));
		
		return retval;

	}
	
	
	
	/** 
	 * This method allows to add a whole NFFG 
	 * @param the Nffg to store in the service
	 * @throws NfvReaderException 
	 * @throws ServiceException 
	 * @throw ForbiddenException
	 * @throw InternalServerErrorException
	 */
	@POST
	@Path("addNffg")
	@Consumes(MediaType.APPLICATION_XML)
	@ApiOperation(value = "Add single Nffg", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})	
	public void addNffg(NffgImpl nffg) throws ServiceException, NfvReaderException{
		
	        neo4j.loadnffg(nffg);   		
	}
	
	/**
	 * @param Add Nodes
	 * @return
	 */
	@POST
	@Path("AddNode")
	@Consumes(MediaType.APPLICATION_XML)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})	
	public void loadNode(NffgImpl nffg, NodeImpl node) {
		
	       
	}
	
	
	
	@GET
	@Path("prova")
	@Produces(MediaType.TEXT_PLAIN)
	public String prova() {
		return "hello world";
	}
	
	
	
	
	
	
}

