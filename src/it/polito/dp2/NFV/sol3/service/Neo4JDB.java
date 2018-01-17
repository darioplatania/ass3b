package it.polito.dp2.NFV.sol3.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.sol3.jaxb.*;
import it.polito.dp2.NFV.sol3.service.neo4j.Labels;
import it.polito.dp2.NFV.sol3.service.neo4j.Node;
import it.polito.dp2.NFV.sol3.service.neo4j.Properties;
import it.polito.dp2.NFV.sol3.service.neo4j.Property;
import it.polito.dp2.NFV.sol3.service.neo4j.Relationship;

public class Neo4JDB {
	
	private Catalog 	catalogimpl = new Catalog();
	private String BaseURI;
	private Client client;
	//private final static String PROPERTY = "it.polito.dp2.NFV.lab2.URL";
	private final static String PROPERTY ="it.polito.dp2.NFV.lab3.Neo4JSimpleXMLURL";
	
	private static   	Map<String, NodeImpl> Nodes   			 = new ConcurrentHashMap<>();
	protected static		Map<String,NffgImpl> nffgs 				 = new ConcurrentHashMap<>();
	private static 	    Map<String, Node> Neo4JNodes 		     = new ConcurrentHashMap<>();
	private static  		Map<String, Node> Neo4JHost 		 		 = new ConcurrentHashMap<>();
	private static	    Map<String,HostImpl> hostmap_appoggio 	 = new ConcurrentHashMap<>();
	private static	    Map<String,HostImpl> reach 	 			 = new ConcurrentHashMap<>();
	

	
	
	public Neo4JDB() {
     
     // create the basic URL as a String
	BaseURI = System.getProperty(PROPERTY);

     if (BaseURI == null)
         BaseURI = "http://localhost:8080/Neo4JSimpleXML/webapi/data";
    else
     	BaseURI+="/data";


     /*Init Client*/
	client = ClientBuilder.newClient();
	}
	
	

public void loadnffg(NffgImpl nffg) throws ServiceException, NfvReaderException {
	
	/*Correct Load*/
    //NffgMap.put(nffgReader.getName(), nffgReader);

	/*Start isLoaded*/
    boolean res_nffg = isLoaded(nffg.getNameNffg());
    if(!res_nffg) {
    		//throw new NfvReaderException("Nffg Loaded Exception!");
		
		//CONTROL FUNCTION
		checkResource(nffg);
		
		nffg.setDeployTime(getCurrentTime());
	    
	    /*Upload all node*/
	    for (NodeImpl node : nffg.getNodeImpl()) {
	        	if(node != null) {
	        		boolean res_node = NodePropertiesCreate(node.getNodeName());
		        	if(res_node == true) {
		        		Nodes.put(node.getNodeName(), node);	  
		            HostPropertiesCreate(node.getHostName());	          
		            NodeHostRel(node.getNodeName(),node.getHostName());
		        }
	        	}
	    }
	    
	   	for(Map.Entry<String, NodeImpl> node : Nodes.entrySet()){	
	        for (LinkImpl link : node.getValue().getLinkImpl()) {            		
	            NodeLinkRel(node.getValue().getNodeName(), link.getDestinationNode());
	        }
	    }
	   	
	   	nffgs.put(nffg.getNameNffg(), nffg);
    }
   	
}


/*
 **********************
 * NODE,HOST,RELAZIONI
 **********************
 */


private boolean NodePropertiesCreate(String nodeName) throws ServiceException {
	System.out.println("*** Init Node Properties Create ***");
	
	if(Neo4JNodes.get(nodeName)==null) {
		
		System.out.println("NON ESISTE IL NODO");
		
		Node node = new Node();
	
		//a node has to be created for each NFFG node, with a property named “name” whose value is the NFFG name;
		Property name_property = new Property();
		Properties pr = new Properties();
		
		name_property.setName("name");
		name_property.setValue(nodeName);
		
		pr.getProperty().add(name_property);
		
		node.setProperties(pr);
		
		Labels labels = new Labels();
		labels.getLabel().add("Node");
		node.setLabels(labels);

		/*Post properties*/
		Response resp = client.target(BaseURI+"/node").request(MediaType.APPLICATION_XML).post(Entity.entity(node,MediaType.APPLICATION_XML),Response.class);
		
		if(resp.getStatus()!=201)
			throw new ServiceException("Node not create " + resp.getStatus() + " URI: " + BaseURI);

		node.setId(resp.readEntity(Node.class).getId());
		
		/*Post label*/
		resp = client.target(BaseURI+"/node/"+ node.getId()  +"/labels").request(MediaType.APPLICATION_XML).post(Entity.entity(labels,MediaType.APPLICATION_XML),Response.class);
		
		if(resp.getStatus()!=204)
			throw new ServiceException("Label is not created" + resp.getStatus());
		
		/*aggiorno la mappa*/
		Neo4JNodes.put(name_property.getValue(), node);
		//Neo4JNodes.put(node.getId(), node);
		System.out.println("DIMENSIONE MAPPA NODI NODEPROP: " + Neo4JNodes.size());
		System.out.println("*** End Node Properties Create ***");
		
		return true;
	}
	else {
		
		System.out.println("ESISTE IL NODO");
		return false;
	}
	
}


private void NodeLinkRel(String srcNode, String destNode) throws ServiceException {
	
	System.out.println("*** Init Node Link Rel ***");
	
    Relationship rel = new Relationship();
    rel.setType("ForwardsTo");
    rel.setDstNode(Neo4JNodes.get(destNode).getId());

     /*Post Relationship */
    Response  resp = client.target(BaseURI+"/node/"+Neo4JNodes.get(srcNode).getId()+"/relationships").request(MediaType.APPLICATION_XML).post(Entity.entity(rel,MediaType.APPLICATION_XML),Response.class);

    if ((resp.getStatus() != 200) && (resp.getStatus() != 201)) {
        throw new ServiceException("Error creating relationship");
    }
    System.out.println("*** End Node Link Rel ***");
}

private void HostPropertiesCreate(String hostName) throws ServiceException {
	System.out.println("*** Init Host Properties Create ***");
		
		if(Neo4JHost.get(hostName) == null) {
	
		Node node = new Node();
		
		/*a node has to be created for each NFFG node, with a property named “name” whose value is the NFFG name*/
		Property name_property = new Property();
		Properties pr = new Properties();
		
		name_property.setName("name");
		name_property.setValue(hostName);
		
		pr.getProperty().add(name_property);
		
		node.setProperties(pr);
	
		Labels labels = new Labels();
		labels.getLabel().add("Host");
		node.setLabels(labels);

		/*Post properties*/
		Response resp = client.target(BaseURI+"/node").request(MediaType.APPLICATION_XML).post(Entity.entity(node,MediaType.APPLICATION_XML),Response.class);
		
		if(resp.getStatus()!=201)
			throw new ServiceException("Node not create" + resp.getStatus());

		node.setId(resp.readEntity(Node.class).getId());

		/*Post label*/
		resp = client.target(BaseURI+"/node/"+ node.getId()  +"/labels").request(MediaType.APPLICATION_XML).post(Entity.entity(labels,MediaType.APPLICATION_XML),Response.class);
		
		if(resp.getStatus()!=204)
			throw new ServiceException("Label is not created" + resp.getStatus());
		
		/*aggiorno la mappa*/
		Neo4JHost.put(name_property.getValue(), node);
		
		System.out.println("*** End Host Properties Create ***");
		
	}

}

private void NodeHostRel(String srcNode, String destNode) throws ServiceException {
	
	System.out.println("*** Init Node Host Relationship ***");

    Relationship rel = new Relationship();
    rel.setType("AllocatedOn");
    rel.setDstNode(Neo4JHost.get(destNode).getId());
  
     /* Insert Relationship */
    Response  resp = client.target(BaseURI+"/node/"+Neo4JNodes.get(srcNode).getId()+"/relationships").request(MediaType.APPLICATION_XML).post(Entity.entity(rel,MediaType.APPLICATION_XML),Response.class);

    if ((resp.getStatus() != 200) && (resp.getStatus() != 201)) {
        throw new ServiceException("Error creating relationship");
    }
    System.out.println("*** End Node Host Relationship ***");
}

/*
* **********************
* My Function Check
* **********************
*/
	
public void checkResource(NffgImpl nffgimpl) throws NfvReaderException {
	
	int mem_compl_nodi;
	int storage_compl;
	int vnf_compl;
	
	NfvDeployer nfv_dep = new NfvDeployer();

	//CHECK MAX VNF NUMBER
	for(Map.Entry<String, HostImpl> host_r : nfv_dep.hostmap.entrySet()) {
		vnf_compl = 0;
		 for(int i = 0; i< nffgimpl.getNodeImpl().size();i++) {
	         if(host_r.getValue().getHostName().equals(nffgimpl.getNodeImpl().get(i).getHostName())) {
	           vnf_compl += 1;
				if(vnf_compl > host_r.getValue().getNumberVNFs()) {
					System.out.println("DENTRO IF");
	             	throw new NfvReaderException("Eccesso VNF!!!!!");
				}     
	         }
	         else {
	           System.out.println("DENTRO ELSE");
	           continue;
	         }
	       }
	}
	
	//CHECH MEM
	for (Map.Entry<String, HostImpl> host_r : nfv_dep.hostmap.entrySet()) {
			
	   mem_compl_nodi = 0;

      for(int i = 0; i< nffgimpl.getNodeImpl().size();i++) {
        if(host_r.getValue().getHostName().equals(nffgimpl.getNodeImpl().get(i).getHostName())) {

       	   String nodo_ftype_id = nffgimpl.getNodeImpl().get(i).getFunctionaltypeId();
       	   
       	   if(nodo_ftype_id.equals(catalogimpl.getFtypeImpl().get(i).getFunctionaltypeId())) 
       		   mem_compl_nodi+=catalogimpl.getFtypeImpl().get(i).getRequiredMemory();
          
          if(host_r.getValue().getMemory()<mem_compl_nodi) {
            System.out.println("DENTRO IF");
            throw new NfvReaderException("Eccesso MEMORY!!!!!");
          }
        }
        else {
          System.out.println("DENTRO ELSE");
          continue;
        }
      }
    }
	
	//CHECH STORAGE
	for (Map.Entry<String, HostImpl> host_r : nfv_dep.hostmap.entrySet()) {
			
		storage_compl = 0;

      for(int i = 0; i< nffgimpl.getNodeImpl().size();i++) {
        if(host_r.getValue().getHostName().equals(nffgimpl.getNodeImpl().get(i).getHostName())) {
          
       	   String nodo_ftype_id = nffgimpl.getNodeImpl().get(i).getFunctionaltypeId();
       	   
       	   if(nodo_ftype_id.equals(catalogimpl.getFtypeImpl().get(i).getFunctionaltypeId())) 
       		   storage_compl+=catalogimpl.getFtypeImpl().get(i).getRequiredStorage();
          
          if(host_r.getValue().getDiskStorage()<storage_compl) {
            System.out.println("DENTRO IF");
            throw new NfvReaderException("Eccesso MEMORY!!!!!");
          }
            
        }
        else {
          System.out.println("DENTRO ELSE");
          continue;
        }
      }
    }
}


/**
 * @return the CurrentTime in XMLGregorianCalendar Format
 */
public XMLGregorianCalendar getCurrentTime(){
	GregorianCalendar gregorianCalendar = new GregorianCalendar();
	XMLGregorianCalendar xgc = null;
	try {
		xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	} catch (DatatypeConfigurationException e) {
		e.printStackTrace();
	}
	return xgc;
}

public boolean isLoaded(String nffgName) throws NfvReaderException {
	// TODO Auto-generated method stub
	System.out.println("*** Init isLoaded ***");
	
	if(nffgs.get(nffgName) != null) {
		System.out.println("Nffg già caricata");
		return true;
	}
	else {
		System.out.println("Nffg non caricata ---> Start");
		return false;
	}


}

/**
 * @return a collection of Nffg saved in the service
 */
public Collection<NffgImpl> getNffgs() {
	return nffgs.values();
}

/**
 * @return a single Nffg saved in the service
 */

public NffgImpl getNffg(String name){
	
	NffgImpl retval = new NffgImpl();
	
	retval = nffgs.get(name);
	
	return retval;

}


/**
 * @return a Nodes saved in the service
 */

public NodeImpl getNode(String name){
	
	NodeImpl retval = new NodeImpl();
	
	retval = Nodes.get(name);
	
	if(retval == null)
		System.out.println("RETVAL NULL");
	
	return retval;

}

/**
 * @return reachableHost
 */

public Collection<HostImpl> getReachableHosts(String id){
	
	Node node = new Node();
	HostImpl hostimpl = new HostImpl();	
	String nome_nodo;
	String nome_host;
	
	node = Neo4JNodes.get(id);
	
	Response resp = client.target(BaseURI+"/node/"+ node.getId() + "/reachableNodes?relationshipTypes=ForwardsTo&NodeLabel=Node").request(MediaType.APPLICATION_XML).get(Response.class);
	
	//if(resp.getStatus()!= 200)
		//throw new ServiceException("getExtendedNodes Failed");

	List<Node> list_node = resp.readEntity(new GenericType<List<Node>>(){});
	
	Iterator<Node> iterator = list_node.iterator();
	
	while(iterator.hasNext()) {
		Node node1 = (Node)iterator.next();
		nome_nodo = node1.getProperties().getProperty().get(0).getValue();
		nome_host = Nodes.get(nome_nodo).getHostName();
		
		
		hostimpl = hostmap_appoggio.get(nome_host);
		reach.put(hostimpl.getHostName(), hostimpl);
		
		
	}
	
		return reach.values();
}

/**
 * 
 * @addNode
 */

public void loadNode(NffgImpl nffg, NodeImpl node) {
	
	if(nffgs.get(nffg.getNameNffg()) ==null)
		throw new NotFoundException(); // Nffg Not Present
	
	for(Map.Entry<String, NffgImpl> nffg_c : nffgs.entrySet()) {
		
	}
		

	
	
}

	
public void map_passed(Map<String, HostImpl> hostmap) {
	for(HostImpl host : hostmap.values()) {
		hostmap_appoggio.put(host.getHostName(), host);
		
	}
}
	
	
	
	

	
	
}
