package it.polito.dp2.NFV.sol3.service;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.sol3.jaxb.*;
import it.polito.dp2.NFV.sol3.service.neo4j.Labels;
import it.polito.dp2.NFV.sol3.service.neo4j.Node;
import it.polito.dp2.NFV.sol3.service.neo4j.Properties;
import it.polito.dp2.NFV.sol3.service.neo4j.Property;
import it.polito.dp2.NFV.sol3.service.neo4j.Relationship;

public class Neo4JDB {
	
	private Catalog 	catalogimpl = new Catalog();
	private String BaseURI;
	private String url;
	private Client client;
	private WebTarget target;
	private Response resp;
	
	//private NfvDeployer nfv;
	//private final static String PROPERTY = "it.polito.dp2.NFV.lab2.URL";
	private final static String PROPERTY ="it.polito.dp2.NFV.lab3.Neo4JSimpleXMLURL";
	
	protected static   	Map<String, NodeImpl> Nodes   			     = new ConcurrentHashMap<>();
	protected static		Map<String,NffgImpl> nffgs 				     = new ConcurrentHashMap<>();
	private static 	    Map<String, Node> Neo4JNodes 		         = new ConcurrentHashMap<>();
	private static  		Map<String, Node> Neo4JHost 		 		     = new ConcurrentHashMap<>();
	protected static	    Map<String,Relationship> linkmap 	 			 = new ConcurrentHashMap<>();
	protected static	    Map<String,HostImpl> hostmap_appoggio 	     = new ConcurrentHashMap<>();
	protected static	    Map<String,FtypeImpl> ftypemap_appoggio 	     = new ConcurrentHashMap<>();
	protected static	    Map<String,PerformanceImpl> perfmap_appoggio 	 = new ConcurrentHashMap<>();
	private static	    Map<String,HostImpl> reach 	 			     = new ConcurrentHashMap<>();

	public Neo4JDB() {
     
     // create the basic URL as a String
	BaseURI = System.getProperty(PROPERTY);

   /*  if (BaseURI == null)
         BaseURI = "http://localhost:8080/Neo4JSimpleXML/webapi/data";
    else
     	BaseURI+="/data";*/
	
	if(BaseURI == null || BaseURI.equals("")) {
		BaseURI = "http://localhost:8080/Neo4JSimpleXML/rest/data";
	}
	 else
	     	BaseURI+="/data";
	
	url = BaseURI;
	
	/*Init Client*/
	client = ClientBuilder.newClient();
	target = client.target(getBaseURI(BaseURI));
	
	}
	
	private static URI getBaseURI(String url) {
		return UriBuilder.fromUri(url).build();
	}

public static Object getSynchObject(){
	return nffgs;
}
	

public boolean loadnffg(NffgImpl nffg) throws NfvReaderException, Exception {
	
	for(NodeImpl n : nffg.getNodeImpl()) {
		if(Nodes.containsKey(n.getNodeName()))
			return false;
	}
	
	/*Correct Load*/
    //NffgMap.put(nffgReader.getName(), nffgReader);

	/*Start isLoaded*/
    //boolean res_nffg = isLoaded(nffg.getNameNffg());
    //if(!res_nffg) {
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
		        	else
		        		return false;
	        	}
	    }
	    
	   	for(Map.Entry<String, NodeImpl> node : Nodes.entrySet()){	
	        for (LinkImpl link : node.getValue().getLinkImpl()) {            		
	            Relationship rel_return = NodeLinkRel(node.getValue().getNodeName(), link.getDestinationNode());	    
	            
	            linkmap.put(link.getLinkName(), rel_return);
	            
	        }
	    }
	   	
	   	nffgs.put(nffg.getNameNffg(), nffg);
	   	
	   	return true;
	   
    //}
   	
}


/*
 **********************
 * NODE,HOST,RELAZIONI
 **********************
 */


private boolean NodePropertiesCreate(String nodeName) throws Exception{
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
		resp = target.path("node").request(MediaType.APPLICATION_XML).post(Entity.entity(node,MediaType.APPLICATION_XML),Response.class);
		
		if(resp.getStatus()!=201)
			throw new Exception("Node not create " + resp.getStatus() + " URI: " + BaseURI);

		node.setId(resp.readEntity(Node.class).getId());
		
		/*Post label*/
		resp = target.path("node/"+ node.getId()  +"/labels").request(MediaType.APPLICATION_XML).post(Entity.entity(labels,MediaType.APPLICATION_XML),Response.class);
		
		if(resp.getStatus()!=204)
			throw new Exception("Label is not created" + resp.getStatus());
		
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


private Relationship NodeLinkRel(String srcNode, String destNode) throws Exception{
	
	System.out.println("*** Init Node Link Rel ***");
	
    Relationship rel = new Relationship();
    rel.setType("ForwardsTo");
    rel.setDstNode(Neo4JNodes.get(destNode).getId());	

     /*Post Relationship */
     resp = target.path("node/"+Neo4JNodes.get(srcNode).getId()+"/relationships").request(MediaType.APPLICATION_XML).post(Entity.entity(rel,MediaType.APPLICATION_XML),Response.class);

    if ((resp.getStatus() != 200) && (resp.getStatus() != 201)) {
        throw new Exception("Error creating relationship");
    }
    
    rel.setId(resp.readEntity(Relationship.class).getId());

    System.out.println("*** End Node Link Rel ***");
    
    return rel;
}

private void HostPropertiesCreate(String hostName) throws Exception{
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
		resp = target.path("node").request(MediaType.APPLICATION_XML).post(Entity.entity(node,MediaType.APPLICATION_XML),Response.class);
		
		if(resp.getStatus()!=201)
			throw new Exception("Node not create" + resp.getStatus());

		node.setId(resp.readEntity(Node.class).getId());

		/*Post label*/
		resp = target.path("node/"+ node.getId()  +"/labels").request(MediaType.APPLICATION_XML).post(Entity.entity(labels,MediaType.APPLICATION_XML),Response.class);
		
		if(resp.getStatus()!=204)
			throw new Exception("Label is not created" + resp.getStatus());
		
		/*aggiorno la mappa*/
		Neo4JHost.put(name_property.getValue(), node);
		
		System.out.println("*** End Host Properties Create ***");
		
	}

}

private Relationship NodeHostRel(String srcNode, String destNode) throws Exception{
	
	System.out.println("*** Init Node Host Relationship ***");

    Relationship rel = new Relationship();
    rel.setType("AllocatedOn");
    rel.setDstNode(Neo4JHost.get(destNode).getId());
  
     /* Insert Relationship */
     resp = target.path("node/"+Neo4JNodes.get(srcNode).getId()+"/relationships").request(MediaType.APPLICATION_XML).post(Entity.entity(rel,MediaType.APPLICATION_XML),Response.class);

    if ((resp.getStatus() != 200) && (resp.getStatus() != 201)) {
        throw new Exception("Error creating relationship");
    }
    
    rel.setId(resp.readEntity(Relationship.class).getId());
    
    System.out.println("*** End Node Host Relationship ***");
    
    return rel;
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

/*
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
*/

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
 * @return a collection of Hosts saved in the service
 */
public Collection<HostImpl> getHosts() {
	return hostmap_appoggio.values();
}


/**
 * @return a Catalog saved in the service
 */
public Collection<FtypeImpl> getCatalog() {
	return ftypemap_appoggio.values();
}

/**
 * @return a Performance saved in the service
 */
public Collection<PerformanceImpl> getPerformance() {
	return perfmap_appoggio.values();
}


/**
 * @return reachableHost
 * @throws ServiceException 
 */

public Collection<HostImpl> getReachableHosts(String id) throws Exception{
	
	Node node = new Node();
	HostImpl hostimpl = new HostImpl();	
	String nome_nodo;
	String nome_host;
	
	node = Neo4JNodes.get(id);
	
	//resp = target.path("node/"+ node.getId() + "/reachableNodes?relationshipTypes=ForwardsTo&NodeLabel=Node").request(MediaType.APPLICATION_XML).get(Response.class);
	
	resp = target.path("node/"+ node.getId() + "/reachableNodes").queryParam("relationshipTypes", "ForwardsTo").queryParam("NodeLabel", "Node").request(MediaType.APPLICATION_XML).get(Response.class);
	
	if(resp.getStatus()!= 200)
		throw new Exception("getExtendedNodes Failed");

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
 * @throws NfvReaderException 
 * @throws ServiceException 
 * @addNode
 */

public boolean loadNode(NffgImpl nffg , NodeImpl node, String hostname) throws NfvReaderException, Exception {
	
	HostImpl host = hostmap_appoggio.get(hostname);
	
	if(!allocatedHost(host)) {
		reloadNode(nffg,node);
		
		return true;
	}

	if(!allocatedHostMem(host)) {
		reloadNode(nffg,node);
		
		return true;
	}
	
	
	if(!allocatedHostStorage(host)) {
		reloadNode(nffg,node);
		
		return true;
	}
	else{
		boolean res_node = NodePropertiesCreate(node.getNodeName());
	    	if(res_node == true) {
	    		NodeHostRel(node.getNodeName(), hostname);
	    		if(!Nodes.containsKey(node.getNodeName())) {
	    			node.setHostName(hostname);
	    			Nodes.put(node.getNodeName(), node);
	    		}
	    		nffg.getNodeImpl().add(node);
	    		nffgs.put(nffg.getNameNffg(), nffg);	
	    }
	    	else
	    		return false;	
	
		/*NodePropertiesCreate(node.getNodeName());
		   NodeHostRel(node.getNodeName(), hostname);
			if(!Nodes.containsKey(node.getNodeName())) {
				node.setHostName(hostname);
				Nodes.put(node.getNodeName(), node);
			}
			nffg.getNodeImpl().add(node);
			nffgs.put(nffg.getNameNffg(), nffg);	*/
	}
	
		   
	return true;
	
}

public String searchHost() throws NfvReaderException, Exception {
	Random       random       = new Random();
	List<String> keys         = new ArrayList<String>(Neo4JDB.hostmap_appoggio.keySet());
	String       randomKey    = keys.get( random.nextInt(keys.size()) );
	HostImpl     host         = Neo4JDB.hostmap_appoggio.get(randomKey);
	
	System.out.println("HOST CASUALE: " + host.getHostName());

	return host.getHostName();
}

public void reloadNode(NffgImpl nffg, NodeImpl node) throws Exception {
	
	System.out.println("** Reload Node Start **");
	
	   for(Map.Entry<String, HostImpl> h : hostmap_appoggio.entrySet()) {
		   if(!allocatedHost(h.getValue()))
			   continue;
		   if(!allocatedHostMem(h.getValue()))
			   continue;
		   if(!allocatedHostStorage(h.getValue()))
			   continue;
		 
		   NodePropertiesCreate(node.getNodeName());
		   NodeHostRel(node.getNodeName(), h.getValue().getHostName());
			if(!Nodes.containsKey(node.getNodeName())) {
				node.setHostName(h.getValue().getHostName());
				Nodes.put(node.getNodeName(), node);
			}
			nffg.getNodeImpl().add(node);
			nffgs.put(nffg.getNameNffg(), nffg);	
			break;
	   }
}

public boolean checkLink(String nffgname,LinkImpl link){
	
	System.out.println("** Init checkLink **");
	
	//false return non esiste, true return esiste
	
	NffgImpl nffg_link = nffgs.get(nffgname);
	
	for(NodeImpl n : nffg_link.getNodeImpl()) {
		for(LinkImpl l : n.getLinkImpl()) {	
			if( (l.getSourceNode().equals(link.getSourceNode())) && (l.getDestinationNode().equals(link.getDestinationNode())) ) {
				System.out.println("Esiste un nodo con gli stessi source e dest");
				System.out.println("Nodo: " + n.getNodeName() + " Source: " + link.getSourceNode() + " Dest: " + link.getDestinationNode());
				return true;
			}
			/*if(l.getLinkName().equals(link.getLinkName())) {
				System.out.println("Il Link : " + link.getLinkName() + " esiste nel nodo: " + n.getNodeName());
				return true;	
			}*/
			//n.getLinkImpl().add(link);
		}
	}
	return false;
}

public LinkImpl loadLink(NffgImpl nffg, LinkImpl link) throws Exception{
	
	System.out.println("** Init loadLink **");
	
	
	Relationship rel_return = NodeLinkRel(link.getSourceNode(),link.getDestinationNode());
	
	NffgImpl nffg_link = nffgs.get(nffg.getNameNffg());
	
	for(NodeImpl n : nffg_link.getNodeImpl()) {
		if(n.getNodeName().equals(link.getSourceNode())) {
			n.getLinkImpl().add(link);
			linkmap.put(link.getLinkName(), rel_return);
		}
	}
	
	return link;

}

public LinkImpl upLink(NffgImpl nffg,LinkImpl link) {
	
	System.out.println("** Init upLink **");
	
	NffgImpl nffg_link = nffgs.get(nffg.getNameNffg());
	
	LinkImpl linkimpl = null;
	
	
	
	
	for(NodeImpl n : nffg_link.getNodeImpl()) {
		if(n.getNodeName().equals(link.getSourceNode())) {
			for(int i = 0; i<n.getLinkImpl().size();i++) {
				if( (n.getLinkImpl().get(i).getSourceNode().equals(link.getSourceNode())) && (n.getLinkImpl().get(i).getDestinationNode().equals(link.getDestinationNode())) ) {  					
				//if(n.getLinkImpl().get(i).getLinkName().equals(link.getLinkName())) {
					n.getLinkImpl().get(i).setLinkName(link.getLinkName());
					n.getLinkImpl().get(i).setSourceNode(n.getLinkImpl().get(i).getSourceNode());
					n.getLinkImpl().get(i).setDestinationNode(n.getLinkImpl().get(i).getDestinationNode());
					n.getLinkImpl().get(i).setMaxLatency(link.getMaxLatency());
					n.getLinkImpl().get(i).setMinThroughput(link.getMinThroughput());
					n.getLinkImpl().get(i).setOverwrite(false);
					n.getLinkImpl().set(i, n.getLinkImpl().get(i));				
					
					linkimpl = n.getLinkImpl().get(i);
				}

			}
		}/*else {
			for(int i = 0; i<n.getLinkImpl().size();i++) {
				if( (n.getLinkImpl().get(i).getSourceNode().equals(link.getSourceNode())) && (n.getLinkImpl().get(i).getDestinationNode().equals(link.getDestinationNode())) ) {  
				//if(n.getLinkImpl().get(i).getLinkName().equals(link.getLinkName())) {
					n.getLinkImpl().remove(i);
					System.out.println("Link Rimosso");
						for(NodeImpl n1 : nffg_link.getNodeImpl()) {
							if(n1.getNodeName().equals(link.getSourceNode())) {
								n1.getLinkImpl().add(link);
								System.out.println("Link Aggiunto dopo rimozione");
								break;
							}
						}
				 break;
				}
			}			
		}*/
	}
	
	return linkimpl;
	
	
}



/*
 * ******
 * MAPPE
 * ******
 */
public void map_host(Map<String, HostImpl> hostmap) {
	for(HostImpl host : hostmap.values()) {
		hostmap_appoggio.put(host.getHostName(), host);
		
	}
}

public void map_ftype(Map<String, FtypeImpl> ftypemap) {
	for(FtypeImpl ftype : ftypemap.values()) {
		ftypemap_appoggio.put(ftype.getFunctionaltypeId(),ftype);
		
	}
}

public void map_performance(Map<String, PerformanceImpl> perfmap) {
	for(PerformanceImpl perf : perfmap.values()) {
		String var = perf.getSourceHost() + "-" + perf.getDestinationHost();
		perfmap_appoggio.put(var, perf);
		
	}
}



/*
* ********************************
* My Function Check AllocatedHost
* ********************************
*/
	

public boolean allocatedHost(HostImpl host) {
	
System.out.println(" ** Init allocatedHost **");

System.out.println(" **checkVNF **");
int nodi_presenti = 1;
	
for(Map.Entry<String, NodeImpl> n : Nodes.entrySet()) {
	if(n.getValue().getHostName().equals(host.getHostName())) {
		System.out.println("DENTRO IF");
		nodi_presenti += 1;
		System.out.println("Numero Vnf nell'host: " + host.getNumberVNFs() + " numero vnf: " + nodi_presenti);   
	}
	else{
		System.out.println("DENTRO ELSE");
	    continue;
	}	
}

if(nodi_presenti > host.getNumberVNFs()) {
	System.out.println("Non puoi allocarlo qui..già pieno!");
	return false;
} 
else{

	System.out.println("Puoi allocarlo qui!!");
	System.out.println(" **Init checkMemory **");
	
	return true;
	//allocatedHostMem(host);
	
	}

	
}

public boolean allocatedHostMem(HostImpl host) {

int mem_nodi = 0;

 for(Map.Entry<String, NodeImpl> n : Nodes.entrySet()) {
   if(n.getValue().getHostName().equals(host.getHostName())) {
	   System.out.println("DENTRO IF");
	   String nodo_ftype_id = n.getValue().getFunctionaltypeId();
	   
	   
   	   
	   	if(ftypemap_appoggio.get(nodo_ftype_id)!=null) {
	   		mem_nodi+=ftypemap_appoggio.get(nodo_ftype_id).getRequiredMemory();
	   	    System.out.println("Memoria nell'host: " + host.getMemory() + " memoria vnf: " + mem_nodi);   
	   	}
	   	else
	   		continue;
	}
    else {
    		System.out.println("DENTRO ELSE");
    		continue;
    }
  }
  
	if(host.getMemory()<mem_nodi) {
		System.out.println("Non puoi allocarlo qui..memoeria piena!");
		return false;
	}
	else{
		System.out.println("Puoi allocarlo qui!!");
	
		System.out.println(" **Init checkStorage **");
		//allocatedHostStorage(host);
		return true;
	
	}		
		
}


public boolean allocatedHostStorage(HostImpl host) {

int storage_nodi = 0;

 for(Map.Entry<String, NodeImpl> n : Nodes.entrySet()) {
   if(n.getValue().getHostName().equals(host.getHostName())) {
	   System.out.println("DENTRO IF");
	   String nodo_ftype_id = n.getValue().getFunctionaltypeId();
	   
	   
   	   
	   	if(ftypemap_appoggio.get(nodo_ftype_id)!=null) {
	   		storage_nodi+=ftypemap_appoggio.get(nodo_ftype_id).getRequiredStorage();
	   	    System.out.println("Memoria nell'host: " + host.getDiskStorage() + " memoria vnf: " + storage_nodi);   
	   	}
	   	else
	   		continue;
	}
    else {
    		System.out.println("DENTRO ELSE");
    		continue;
    }
  }
  
	if(host.getDiskStorage()<storage_nodi) {
		System.out.println("Non puoi allocarlo qui..memoeria piena!");
		return false;
	}
	else{
		System.out.println("Puoi allocarlo qui!!");
		return true;
	
	}		
}
	




/*
* *****************************
* My Function Check Init Nffg
* *****************************
*/
	
public void checkResource(NffgImpl nffgimpl) throws NfvReaderException {
	
	System.out.println("Init checkResource Host");
	
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

public void delLink(String nffg_name,String link_name) throws Exception {
	
	NffgImpl nffg_link = nffgs.get(nffg_name);
	
	System.out.println("Rimuovo dalla nffg");
	
	for(NodeImpl n : nffg_link.getNodeImpl()) {
		for(LinkImpl l : n.getLinkImpl()) {
			if(l.getLinkName().equals(link_name)) {
				System.out.println("dentro if..confronto: " + l.getLinkName() + " con: " + link_name);
				n.getLinkImpl().remove(l);
				break;
			}
		}
	}
	
	
	
	resp = target.path("relationship/" + linkmap.get(link_name).getId()).request(MediaType.APPLICATION_XML).delete(Response.class);
	
	if(resp.getStatus()!= 204)
		throw new Exception("Delete Link Failed");
	
    System.out.println("LinkMap size prima della rimozione: " + linkmap.size());
	
    linkmap.remove(link_name);
	
	System.out.println("LinkMap size prima dopo rimozione: " + linkmap.size());
		
}

public void delNode(String nffg_name,String node_name) throws Exception {
	
	
NffgImpl nffg_link = nffgs.get(nffg_name);
	
	System.out.println("Rimuovo dalla nffg");
	
	for(NodeImpl n : nffg_link.getNodeImpl()) {
		if(n.getNodeName().equals(node_name)) {
			System.out.println("dentro if..confronto: " + n.getNodeName() + " con: " + node_name);
			nffg_link.getNodeImpl().remove(n);
			break;
		}		
	}
	
	

	
    System.out.println("NodesMap size prima della rimozione: " + Nodes.size());
    System.out.println("Neo4JNodes size prima della rimozione: " + Neo4JNodes.size());
	
    Nodes.remove(node_name);
    Neo4JNodes.remove(node_name);
    
	
	System.out.println("NodesMap size dopo dopo rimozione: " + Nodes.size());
	System.out.println("Neo4JNodes size dopo della rimozione: " + Neo4JNodes.size());
	
   /* resp = target.path("node/" + Neo4JNodes.get(node_name).getId()).request(MediaType.APPLICATION_XML).delete(Response.class);
	
	if(resp.getStatus()!=204)
		throw new Exception("Node Delete " + resp.getStatus() + " URI: " + BaseURI);*/
	
}

	
	
	

	
	
}
