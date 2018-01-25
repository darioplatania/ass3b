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
	
	public NfvResources() throws DatatypeConfigurationException, Exception, NfvReaderException{
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
	 * @throws Exception 
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
	public HostC getReachableHosts(@PathParam("id") String id) throws Exception{
		//try {
			
		NodeImpl node  = neo4j.getNode(id);
		if(node == null)
			throw new NotFoundException();
			
		HostC retval = new HostC(); 
		retval.getHostImpl().addAll(neo4j.getReachableHosts(id));
	
		return retval;
		
		//}catch(ServiceException e) {
			//throw new InternalServerErrorException();
		//}

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
	 * @throws Exception 
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
	public NffgImpl addNffg(NffgImpl nffg) throws Exception{
		//serviceException & NfvReaderException le uso la prima nella create node e la seconda nei check dell'host
		
		//try{
			synchronized(Neo4JDB.getSynchObject()) {
				
				//vedo se nell'xml esiste il nome della nffg
				if (nffg.getNameNffg() == null || nffg.getNameNffg().equals(""))
					throw new ForbiddenException();
				
				//vedo se già esiste
				if (Neo4JDB.nffgs.containsKey(nffg.getNameNffg()))
					throw new ForbiddenException();
				
				/*//DestinationNode non presente nei nodi all'interno di questa Nffg
				for(NodeImpl n1 : nffg.getNodeImpl()) {
					System.out.println("Nodo nome: " + n1.getNodeName());
					for(NodeImpl n2 : nffg.getNodeImpl()) {
						for(LinkImpl l1 : n2.getLinkImpl()) {
							System.out.println("Link name + dest node: " + l1.getLinkName() + l1.getDestinationNode());
							if (!Neo4JDB.Nodes.containsKey(l1.getDestinationNode()))
								throw new ForbiddenException();
						}
					}
				}*/

				//controllo se nell'xml della post nffg esistono due nodi uguali
				int count = 0;
				
				for(NodeImpl n : nffg.getNodeImpl()) {
					System.out.println("Ciclo il nodo lista1: " + n.getNodeName());
					for(NodeImpl n2 : nffg.getNodeImpl()) {
						System.out.println("Ciclo il nodo lista2: " + n2.getNodeName());
						if( (n.getNodeName().equals(n2.getNodeName()))) {
							System.out.println("ho trovato me stesso");
							count+=1;						
						}
						if(count >= 2 ) {
							System.out.println("Nodi duplicati all'interno della post nffg");
							throw new ForbiddenException();
						}
					}
					count = 0;
				}
				
				if (!neo4j.loadnffg(nffg))
					throw new InternalServerErrorException();
				return Neo4JDB.nffgs.get(nffg.getNameNffg());
				
				
			}
			// neo4j.loadnffg(nffg);
		}//catch(NfvReaderException | ServiceException e){
				//throw new InternalServerErrorException();
			
		//}	           		
	//}
	
	/**
	 * This method allows to add a node in the NFFg
	 * @param the Nffg and Node
	 * @throws Exception 
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
	public NodeImpl addNode(@PathParam("id") String id, NodeImpl node) throws Exception{
		
		//try {
			synchronized(Neo4JDB.getSynchObject()) {

				if (!Neo4JDB.nffgs.containsKey(id))
					throw new NotFoundException("Nffg Not Found!"); // Nffg Not Found
				
				//vedo se nell'xml esiste il nome del nodo
				if (node.getNodeName() == null ||node.getNodeName().equals(""))
					throw new ForbiddenException();
	
				if (Neo4JDB.Nodes.containsKey(node.getNodeName()))
					throw new ForbiddenException(); // Node Present
				
				if(node.getHostName().equals(""))
					throw new ForbiddenException(); //non esiste il nome dell'host
	
				/* vedo se nell'xml c'è l'host */
				if ((node.getHostName() != null)) {
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
						else
							return Neo4JDB.Nodes.get(node.getNodeName());
					}
				}
				//se non c'è lo metto in un host a piacere
				else {
					System.out.println("Non c'è host nell'xml lo scelgo casualmente");
	
					String host_casuale = neo4j.searchHost();
					
					NffgImpl nffg = Neo4JDB.nffgs.get(id);
					if (!neo4j.loadNode(nffg,node,host_casuale))
						throw new InternalServerErrorException();
					else
						return Neo4JDB.Nodes.get(node.getNodeName());
				}
			}


		} //catch (NfvReaderException | ServiceException e) {
			//throw new InternalServerErrorException();
		//}

       
//}
	
	/**
	 * This method allows to add a link in the NFFg
	 * @param the Nffg and Node 
	 * @throws Exception 
	 * @throws NfvReaderException 
	 * @throws NotFoundException
	 * @throws ForbiddenException
	 * @throws InternalServerErrorException
	 * 
	 */
	@POST
	@Path("{id}/addLink")
	@Consumes(MediaType.APPLICATION_XML)
	@ApiOperation(value = "Add a Link", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	public LinkImpl addLink(@PathParam("id") String id, LinkImpl link) throws Exception{
	//try {
		
		synchronized(Neo4JDB.getSynchObject()) {

			if (!Neo4JDB.nffgs.containsKey(id))
				throw new NotFoundException("Nffg Not Found!"); // Nffg Not Found
			
			if(link.getLinkName() == null || link.getLinkName().equals(""))
				throw new NotFoundException("LinkName Problems");
			if(link.getSourceNode() == null || link.getLinkName().equals(""))
				throw new NotFoundException("SourceNode Problems");
			if(link.getDestinationNode() == null || link.getLinkName().equals(""))
				throw new NotFoundException("DestNode Problems");	
			
			if (!Neo4JDB.Nodes.containsKey(link.getSourceNode()))
				throw new NotFoundException("SourceNode non presente nella mappa"); // SourceNode not Found
			
			if (!Neo4JDB.Nodes.containsKey(link.getDestinationNode()))
				throw new NotFoundException("DestNode non presente nella mappa"); // DestionationNode not Found
			
			
			NffgImpl nffg = Neo4JDB.nffgs.get(id);
			
			/*int count = 0;
			
			for(NodeImpl n : nffg.getNodeImpl()) {
				//controllo se nell'xml della post link esistono due link uguali

				for(LinkImpl l : n.getLinkImpl()) {
					System.out.println("Ciclo il link lista1: " +l.getLinkName());
					for(LinkImpl l2 : n.getLinkImpl()) {
						System.out.println("Ciclo il nodo lista2: " + l2.getLinkName());
						if( (l.getLinkName().equals(l2.getLinkName()))) {
							System.out.println("ho trovato me stesso");
							count+=1;						
						}
						if(count >= 2 ) {
							System.out.println("Link duplicati all'interno della post nffg");
							throw new ForbiddenException();
						}
					}
					count = 0;
				}
				
			}*/
			
				
			//cerco i link, se esiste vado a vedere il flag se è true o false (true aggiorno il link, false exception)
			if(neo4j.checkLink(id,link)) {
				System.out.println("Controllo il flag");
				
				if(!link.isOverwrite()) {
					//flag false quindi eccezione
					System.out.println("Flag false---eccezione");
					throw new ForbiddenException();	
				}
				else {
			    System.out.println("Flag true");
				//aggiorno link da implmentare
				LinkImpl link_n = neo4j.upLink(nffg,link);
				
				return link_n;
				//neo4j.loadLink(nffg, link);
				}
				
			}
			//se non esiste lo carico
			else {
				System.out.println("Nodo non esiste--lo carico");
				LinkImpl link_n = neo4j.loadLink(nffg,link);		
				
				return link_n;
			}
		}
	}
	//catch(ServiceException | LinkAlreadyPresentException e) {
		//throw new ForbiddenException();	
	//}
		
	//}
	
	
	/*@DELETE
	@Path("{id}/delNode")
	@ApiOperation(value = "Delete Node", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 501, message = "Not Implemented")
	})
    @Produces(MediaType.APPLICATION_XML)
	public NffgImpl deleteNode() throws NotImplementedException{
		
		throw new NotImplementedException("Method not Implemented");

	}
	
	@DELETE
	@Path("{id}/delLink")
	@ApiOperation(value = "Delete Link", notes = "xml format")
	@ApiResponses(value = {
			@ApiResponse(code = 501, message = "Not Implemented")
	})
    @Produces(MediaType.APPLICATION_XML)
	public NffgImpl deleteLink() throws NotImplementedException {
		
		throw new NotImplementedException("Method not Implemented");

	}*/
	
	@DELETE
	@Path("{id_nffg}/{id_link}/delLink")
	@ApiOperation(value = "Delete Link", notes = "xml format")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = "OK")
	})
    @Produces(MediaType.APPLICATION_XML)
	public void deleteLink(@PathParam("id_nffg") String nffg_name,@PathParam("id_link") String link_name) throws Exception{
		
		if (!Neo4JDB.nffgs.containsKey(nffg_name))
			throw new NotFoundException("Nffg Not Found!"); // Nffg Not Found
		
		
		if(!Neo4JDB.linkmap.containsKey(link_name))
			throw new NotFoundException("Link Not Found!"); // Link Not Found
		
		neo4j.delete(nffg_name, link_name);
		
	}


	
}

