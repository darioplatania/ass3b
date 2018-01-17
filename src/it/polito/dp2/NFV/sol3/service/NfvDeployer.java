package it.polito.dp2.NFV.sol3.service;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.VNFTypeReader;

import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.sol3.jaxb.*;



public class NfvDeployer {
	

	private NfvReader monitor;
	private Neo4JDB neo4j;
	
	protected Map<String,NffgImpl> nffgmap 			= new ConcurrentHashMap<>();
	private   Map<String,PerformanceImpl> perfmap 	= new ConcurrentHashMap<>();
	protected Map<String,HostImpl> hostmap 			= new ConcurrentHashMap<>();
	private   Map<String,FtypeImpl> ftypemap 		= new ConcurrentHashMap<>();
	
	//private   Map<String, NodeImpl> Nodes;
	//private   Map<String, Node> Neo4JNodes;
	//private   Map<String, Node> Neo4JHost;
	

	
	public NfvDeployer() throws NfvReaderException {
		
		System.setProperty("it.polito.dp2.NFV.NfvReaderFactory", "it.polito.dp2.NFV.Random.NfvReaderFactoryImpl");
		monitor = NfvReaderFactory.newInstance().newNfvReader();
		
		neo4j = new Neo4JDB();

	}
	
	
	
	public void serializer() throws DatatypeConfigurationException, ServiceException, NfvReaderException {
		
		serializerHost();
		serializerPerformance();
		serializerVnf();
		serializerNffg("Nffg0");
	}

 /*
  *
  * Deploy Function
  * 
  */ 

private void serializerHost() {
	
	System.out.println("** Start Host Serializer **");
	// Get the list of Hosts
	Set<HostReader> set = monitor.getHosts();
			
	for (HostReader host_r: set) {
		System.out.println("host:" + host_r.getName()) ;
		HostImpl hostimpl = new HostImpl();
		hostimpl.setHostName(host_r.getName());
		hostimpl.setNumberVNFs(host_r.getMaxVNFs());
		hostimpl.setMemory(host_r.getAvailableMemory());
		hostimpl.setDiskStorage(host_r.getAvailableStorage());	
		hostmap.put(hostimpl.getHostName(), hostimpl);
		
		neo4j.map_passed(hostmap);
	}
	System.out.println("** Finish Host Serializer **");
	
}
	
private void serializerPerformance() {
		
		System.out.println("** Start Performance Serializer **");
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();

			for (HostReader sri: set) {
			HostImpl hostimpl = new HostImpl();		
			hostimpl.setHostName(sri.getName());
		}
		for (HostReader sri: set) {			
			for (HostReader srj: set) {			
				PerformanceImpl performance=new PerformanceImpl();
				ConnectionPerformanceReader cpr = monitor.getConnectionPerformance(sri, srj);
				performance.setAvgThroughput(cpr.getThroughput());
				performance.setLatency(cpr.getLatency());
				performance.setSourceHost(sri.getName());
				performance.setDestinationHost(srj.getName());
				String var = performance.getSourceHost() + "-" + performance.getDestinationHost();
				perfmap.put(var, performance);
			}			
		}
		System.out.println("** Finish Performance Serializer **");
	}

private void serializerVnf() {
	System.out.println("** Start VNF Serializer **");
	Set<VNFTypeReader> set = monitor.getVNFCatalog();
	//Catalog catalogimpl = new Catalog();
	
	// For each VNF type print name and class
	for (VNFTypeReader vnfType_r: set) {
		
		// Create ftype and set
		FtypeImpl ftypeimpl = new FtypeImpl();	
		ftypeimpl.setFunctionaltypeId(vnfType_r.getName());
		ftypeimpl.setFunctionalTypeName(NodeFunctionalType.valueOf(vnfType_r.getFunctionalType().toString()));
		ftypeimpl.setRequiredMemory(vnfType_r.getRequiredMemory());
		ftypeimpl.setRequiredStorage(vnfType_r.getRequiredStorage());
		ftypemap.put(ftypeimpl.getFunctionaltypeId(), ftypeimpl);
		
	}
	System.out.println("** Finish VNF Serializer **");
}

private void serializerNffg(String nffg0) throws DatatypeConfigurationException, ServiceException, NfvReaderException{
	
	System.out.println("** Start Nffg Serializer **");
	
	// Get the list of NF-FGs
	NffgReader nffg_r = monitor.getNffg(nffg0);
	
	
	NffgImpl nffgimpl = new NffgImpl();
	
	nffgimpl.setNameNffg(nffg_r.getName());

	// Set deploytime for nffg
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTime(nffg_r.getDeployTime().getTime());
	XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
	nffgimpl.setDeployTime(xgc);
	
	// Print nodes
	Set<NodeReader> nodeSet = nffg_r.getNodes();
	
	// Create nodelist
	//List<NodeImpl> nodelist = nffg.getNodeImpl();
	
	for (NodeReader nr: nodeSet) {

		// Create node,set node and add into nodelist
		NodeImpl node = new NodeImpl();
		node.setNodeName(nr.getName());
		node.setHostName(nr.getHost().getName());
		node.setFunctionaltypeId(nr.getFuncType().getName());
		//nodelist.add(node);
		
		Set<LinkReader> linkSet = nr.getLinks();
		// Create linklist
		//List<LinkImpl> linklist = node.getLinkImpl();
						
		for (LinkReader lr: linkSet) {
			if(lr.getSourceNode()!=lr.getDestinationNode()) {
			// Create link,set link and add into listlink
				LinkImpl link = new LinkImpl();
				link.setLinkName(lr.getName());
				link.setSourceNode(lr.getSourceNode().getName());
				link.setDestinationNode(lr.getDestinationNode().getName());
				link.setMinThroughput(lr.getThroughput());
				link.setMaxLatency(lr.getLatency());
				//linklist.add(link);
			
			node.getLinkImpl().add(link);
			}
			else
				System.out.println("Link Incorrect!");
			    //throw new NfvReaderException("Link Incorrect!");
		}
		nffgimpl.getNodeImpl().add(node);
	}
	
	nffgmap.put(nffg_r.getName(),nffgimpl);	
	
	if(nffgmap.get("Nffg0") != null)
		System.out.println("Dimensione mappa nffg: " + nffgmap.size());
	
	neo4j.loadnffg(nffgimpl);
	
	System.out.println("** Finish Nffg Serializer **");
}



	
}
