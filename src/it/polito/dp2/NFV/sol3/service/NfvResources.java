package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;

import io.swagger.annotations.*;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.lab3.LinkAlreadyPresentException;
import it.polito.dp2.NFV.lab3.NoNodeException;
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
	
	/** 
	 * This method allows to get NFFG by id stored in the NffgService 
	 * Some informations are stored into NffgService memory, others into Neo4JDB
	 * @return  the Nffg stored in the service
	 */
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
		
		NffgImpl retval  = neo4j.getNffg(id);
		
		if(retval == null)
			throw new NotFoundException();
		return retval;
		
	}
	
	/** 
	 * This method allows to get node by id stored in the NffgService 
	 * @return  the node stored in the service
	 */
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
	
	/** 
	 * This method allows to get reachablehosts by existed node stored in the NffgService 
	 * @return  the reachablehosts stored in the service
	 * @throws ServiceException 
	 */
	@GET
	@Path("nodes/{id}/reachablehosts")
	@ApiOperation(value = "Get the set of all hosts", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
    @Produces(MediaType.APPLICATION_XML)
	public HostC getReachableHosts(@PathParam("id") String id){
		try {
			
		NodeImpl node  = neo4j.getNode(id);
		if(node == null)
			throw new NotFoundException();
			
		HostC retval = new HostC(); 
		retval.getHostImpl().addAll(neo4j.getReachableHosts(id));
	
		return retval;
		
		}catch(ServiceException e) {
			throw new InternalServerErrorException();
		}

	}
	
	 /** 
	 * This method allows to get all Hosts stored in the NffgService 
	 * Some informations are stored into NffgService memory, others into Neo4JDB
	 * @return  the List of Hosts stored in the service
	 */
	@GET
	@Path("hosts")
	@ApiOperation(value = "Get the set of all hosts", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
    @Produces(MediaType.APPLICATION_XML)
	public HostC getHosts(){
		HostC retval = new HostC(); 
		retval.getHostImpl().addAll(neo4j.getHosts());
		return retval;

	}
	
	 /** 
	 * This method allows to get all Catalog stored in the NffgService 
	 * Some informations are stored into NffgService memory, others into Neo4JDB
	 * @return  the List of Catalog stored in the service
	 */
	@GET
	@Path("catalog")
	@ApiOperation(value = "Get catalog", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
    @Produces(MediaType.APPLICATION_XML)
	public Catalog getCatalog(){
		Catalog retval = new Catalog(); 
		retval.getFtypeImpl().addAll(neo4j.getCatalog());
		return retval;

	}
	
	/** 
	 * This method allows to get all Perfromance stored in the NffgService 
	 * Some informations are stored into NffgService memory, others into Neo4JDB
	 * @return  the List of Performance stored in the service
	 */
	@GET
	@Path("performances")
	@ApiOperation(value = "Get catalog", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
    @Produces(MediaType.APPLICATION_XML)
	public PerformanceC getPerformance(){
		PerformanceC retval = new PerformanceC(); 
		
		retval.getPerformanceImpl().addAll(neo4j.getPerformance());
		return retval;

	}
	

	
	/** 
	 * This method allows to add a whole NFFG 
	 * @param the Nffg to store in the service
	 * @throws NfvReaderException 
	 * @throws InternalServerErrorException
	 * @throws ForbiddenException
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
	public void addNffg(NffgImpl nffg){
		//serviceException & NfvReaderException le uso la prima nella create node e la seconda nei check dell'host
		
		try{
			synchronized(Neo4JDB.getSynchObject()) {
				if (Neo4JDB.nffgs.containsKey(nffg.getNameNffg()))
					throw new ForbiddenException();

				if (!neo4j.loadnffg(nffg))
					throw new InternalServerErrorException();
			}
			// neo4j.loadnffg(nffg);
		}catch(NfvReaderException | ServiceException e){
				throw new InternalServerErrorException();
			
		}	           		
	}
	
	/**
	 * This method allows to add a node in the NFFg
	 * @param the Nffg and Node
	 * @throws NfvReaderException 
	 * @throws NotFoundException
	 * @throws ForbiddenException
	 * @throws InternalServerErrorException
	 * 
	 */
	@POST
	@Path("{id}/addNode")
	@Consumes(MediaType.APPLICATION_XML)
	@ApiOperation(value = "Add single Node", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	public void addNode(@PathParam("id") String id, NodeImpl node){
		
		try {
			synchronized(Neo4JDB.getSynchObject()) {

				if (!Neo4JDB.nffgs.containsKey(id))
					throw new NotFoundException("Nffg Not Found!"); // Nffg Not Found
	
				if (Neo4JDB.Nodes.containsKey(node.getNodeName()))
					throw new ForbiddenException(); // Node Present
	
				/* vedo se nell'xml c'è l'host */
				if (node.getHostName() != null) {
					/* se c'è lo cerco nella mappa */
					if (!Neo4JDB.hostmap_appoggio.containsKey(node.getHostName())) {
						/*se nella mappa non esiste eccezzione*/
						System.out.println("Host non presente nella mappa..eccezione!");
							throw new NotFoundException(); //Host Not Found
							
					/*se nella mappa non esiste cerco un host e faccio la load
						String host_casuale = neo4j.searchHost();
						
						NffgImpl nffg = Neo4JDB.nffgs.get(id);
						if (!neo4j.loadNode(nffg,node,host_casuale))
							throw new InternalServerErrorException();*/
						
					}
					else {
						System.out.println("Host presente nella mappa..alloco!");
						NffgImpl nffg = Neo4JDB.nffgs.get(id);
						if (!neo4j.loadNode(nffg,node,node.getHostName()))
							throw new InternalServerErrorException();
					}
				}
				//else lo metto in un host a piacere
				else {
					System.out.println("Non c'è host nell'xml lo scelgo casualmente");
	
					String host_casuale = neo4j.searchHost();
					
					NffgImpl nffg = Neo4JDB.nffgs.get(id);
					if (!neo4j.loadNode(nffg,node,host_casuale))
						throw new InternalServerErrorException();
				}
			}


		} catch (NfvReaderException | ServiceException e) {
			throw new InternalServerErrorException();
		}

       
}
	
	/**
	 * This method allows to add a link in the NFFg
	 * @param the Nffg and Node
	 * @throws LinkAlreadyPresentException 
	 * @throws NfvReaderException 
	 * @throws NotFoundException
	 * @throws ForbiddenException
	 * @throws InternalServerErrorException
	 * 
	 */
	@POST
	@Path("{id}/addLink")
	@Consumes(MediaType.APPLICATION_XML)
	@ApiOperation(value = "Add single Node", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	public void addLink(@PathParam("id") String id, LinkImpl link){
	try {
		
		synchronized(Neo4JDB.getSynchObject()) {

			if (!Neo4JDB.nffgs.containsKey(id))
				throw new NotFoundException("Nffg Not Found!"); // Nffg Not Found
			
			if(link.getLinkName() == null)
				throw new InternalServerErrorException();
			if(link.getSourceNode() == null)
				throw new InternalServerErrorException();
			if(link.getDestinationNode() == null)
				throw new InternalServerErrorException();	
			
			if (!Neo4JDB.Nodes.containsKey(link.getSourceNode()))
				throw new NoNodeException(); // SourceNode not Found
			
			if (!Neo4JDB.Nodes.containsKey(link.getDestinationNode()))
				throw new NoNodeException(); // DestionationNode not Found
			
			NffgImpl nffg = Neo4JDB.nffgs.get(id);
				
			//cerco i link, se esiste vado a vedere il flag se è true o false (true aggiorno il link, false exception)
			if(neo4j.checkLink(id,link)) {
				System.out.println("Controllo il flag");
				
				if(!link.isOverwrite()) {
					//flag false quindi eccezione
					System.out.println("Flag false---eccezione");
					throw new LinkAlreadyPresentException();	
				}
				else {
			    System.out.println("Flag true");
				//aggiorno link da implmentare
				neo4j.upLink(nffg,link);
				//neo4j.loadLink(nffg, link);
				}
				
			}
			//se non esiste lo carico
			else {
				System.out.println("Nodo non esiste--lo carico");
				neo4j.loadLink(nffg,link);			
			}
		}
	}
	catch(ServiceException | LinkAlreadyPresentException | NoNodeException e) {
		throw new InternalServerErrorException();	
	}
		
	}

	
	
	
	@GET
	@Path("prova")
	@Produces(MediaType.TEXT_PLAIN)
	public String prova() {
		return "hello world";
	}
	
	
	
	
	
	
}

