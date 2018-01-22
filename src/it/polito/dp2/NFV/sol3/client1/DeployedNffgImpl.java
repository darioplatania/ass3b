package it.polito.dp2.NFV.sol3.client1;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.lab3.AllocationException;
import it.polito.dp2.NFV.lab3.DeployedNffg;
import it.polito.dp2.NFV.lab3.LinkAlreadyPresentException;
import it.polito.dp2.NFV.lab3.NoNodeException;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.sol3.client2.*;
import it.polito.dp2.NFV.sol3.jaxb.*;

public class DeployedNffgImpl implements DeployedNffg{
	
	private String BaseURI;
	private Client client;
	private Response resp;
	
	private  int count_node = 0;
	private  int count_link = 0;
	
	
	 
	 private Map<String, NffgReaderImpl> nffgMap;
	 private Map<String, VNFTypeReaderImpl> vnfsMap;
	 private Map<String, HostReaderImpl> hostMap;
	
	
	
	private final static String PROPERTY ="it.polito.dp2.NFV.lab3.URL";
	
	NffgImpl nffgimpl;
	
	public DeployedNffgImpl(NffgImpl nffg) {
		BaseURI = System.getProperty(PROPERTY);
	
	     if (BaseURI == null)
	         BaseURI = "http://localhost:8080/NfvDeployer/rest";
	 
	     /*Init Client*/
		client = ClientBuilder.newClient();

		this.nffgimpl = nffg;
		
		nffgMap = new HashMap<>();
		vnfsMap = new HashMap<>();
		hostMap = new HashMap<>();
		
		
	}
	
	@Override
	public NodeReader addNode(VNFTypeReader type, String hostName) throws AllocationException, ServiceException {
		// TODO Auto-generated method stub
		
		resp = client.target(BaseURI + "/np/nffgs/" + nffgimpl.getNameNffg()).request(MediaType.APPLICATION_XML).get(Response.class);	
		
		if(resp.getStatus() != 200)
			throw new ServiceException("Get AddNode Error " + resp.getStatus());
		
		
		NffgImpl nffgimpl = resp.readEntity(new GenericType<NffgImpl>(){});		
		
		count_node = nffgimpl.getNodeImpl().size();

		count_node++;
		
		NodeImpl node = new NodeImpl();
		
		node.setNodeName(type.getName() + count_node + nffgimpl.getNameNffg());
		node.setFunctionaltypeId(type.getName());
		node.setHostName(hostName);
		node.setNffgRif(nffgimpl.getNameNffg());
		
		// Request for posting the Link
		resp = client.target(BaseURI + "/np/" + nffgimpl.getNameNffg() + "/addNode").request(MediaType.APPLICATION_XML).post(Entity.entity(node, MediaType.APPLICATION_XML), Response.class);	
		
		if(resp.getStatus() == 400 || resp.getStatus() == 403)
			throw new AllocationException();
		if(resp.getStatus() == 500)
			throw new ServiceException("Post DeployNffg Client1 Error " + resp.getStatus());
		
		NodeImpl respNode = resp.readEntity(new GenericType<NodeImpl>(){});		
		/*Map<String, NffgReaderImpl> nffgMap = new HashMap<>();
		Map<String, VNFTypeReaderImpl> vnfsMap = new HashMap<>();
		Map<String, HostReaderImpl> hostMap = new HashMap<>();*/
		
		getCache();
		
		return new NodeReaderImpl(respNode.getNodeName(), hostMap.get(respNode.getHostName()),nffgMap.get(respNode.getNffgRif()),vnfsMap.get(respNode.getFunctionaltypeId()));
	

	}

	@Override
	public LinkReader addLink(NodeReader source, NodeReader dest, boolean overwrite) throws NoNodeException, LinkAlreadyPresentException, ServiceException {
		// TODO Auto-generated method stub
		
		try {
			
			resp = client.target(BaseURI + "/np/nffgs/" + nffgimpl.getNameNffg()).request(MediaType.APPLICATION_XML).get(Response.class);	
			
		}catch(ForbiddenException e) {
			throw new LinkAlreadyPresentException();
		}
		
		
		if(resp.getStatus() != 200)
			throw new LinkAlreadyPresentException("Get AddLink Client1 Error " + resp.getStatus());
		
		NffgImpl nffgimpl = resp.readEntity(new GenericType<NffgImpl>(){});		
		
		
		for(NodeImpl n : nffgimpl.getNodeImpl()) {
			count_link += n.getLinkImpl().size();
		}
		
		count_link++;
		
		
		
		LinkImpl link = new LinkImpl();
		count_link++;
		
		link.setLinkName("Link" + count_link);
		link.setSourceNode(source.getName());
		link.setDestinationNode(dest.getName());
		link.setOverwrite(overwrite);
		
		
		// Request for posting the Link
		resp = client.target(BaseURI + "/np/" + nffgimpl.getNameNffg() + "/addLink").request(MediaType.APPLICATION_XML).post(Entity.entity(link, MediaType.APPLICATION_XML), Response.class);	
		
		if(resp.getStatus() == 400)
			throw new NoNodeException("Nodo non presente");
		if(resp.getStatus() == 500)
			throw new ServiceException("Post addLink Client1 Error " + resp.getStatus());
		if(resp.getStatus() == 403)
			throw new LinkAlreadyPresentException("Link already exists in this nffg");
		
		LinkImpl resLink = resp.readEntity(new GenericType<LinkImpl>(){});		
		
		
		/*Map<String, NffgReaderImpl> nffgMap = new HashMap<>();
		Map<String, VNFTypeReaderImpl> vnfsMap = new HashMap<>();
		Map<String, HostReaderImpl> hostMap = new HashMap<>();*/
		
		getCache();
		
		int lat;
		float thr;
		if(resLink.getMaxLatency() == null)
			lat = 0;
		else
			lat = resLink.getMaxLatency();
		if(resLink.getMinThroughput() == null)
			thr = (float)0.0;
		else
			thr = resLink.getMinThroughput(); 
		LinkReader link_r = new LinkReaderImpl(resLink.getLinkName(), nffgMap.get(nffgimpl.getNameNffg()).getNode(resLink.getSourceNode()), nffgMap.get(nffgimpl.getNameNffg()).getNode(resLink.getDestinationNode()), lat, thr);
		return link_r;
	
		
	}

	@Override
	public NffgReader getReader() throws ServiceException {
		// TODO Auto-generated method stub
		
		/*Map<String, NffgReaderImpl> nffgMap = new HashMap<>();
		Map<String, VNFTypeReaderImpl> vnfsMap = new HashMap<>();
		Map<String, HostReaderImpl> hostMap = new HashMap<>();*/

		getCache();
		
		System.out.println("SIZE NFFG MAP:" + nffgMap.values().size());
		System.out.println("SIZE VNF MAP:" + vnfsMap.values().size());
		System.out.println("SIZE HOST MAP:" + hostMap.values().size());
		System.out.println("SIZE NFFG NODES:" + nffgMap.get(nffgimpl.getNameNffg()).getNodes().size());
		
		NffgReader nffg_read = nffgMap.get(nffgimpl.getNameNffg());
		
		
		return nffg_read;
		
		
		
	}
	
	
	
	
private void getCache() throws ServiceException {
	
		nffgMap.clear();
		vnfsMap.clear();
		hostMap.clear();

		// Get Catalog
		Response resp = client.target(BaseURI + "/np/catalog").request(MediaType.APPLICATION_XML).get(Response.class);
		if(resp.getStatus() != 200)
			throw new ServiceException();
		
		Catalog catalog = resp.readEntity(new GenericType<Catalog>(){});
		
		for(FtypeImpl functionalType : catalog.getFtypeImpl())
			vnfsMap.put(functionalType.getFunctionaltypeId(),new VNFTypeReaderImpl(functionalType));
		
		// Get Hosts
		resp = client.target(BaseURI + "/np/hosts").request(MediaType.APPLICATION_XML).get(Response.class);
		
		if(resp.getStatus() != 200)
			throw new ServiceException();
		
		HostC hosts = resp.readEntity(new GenericType<HostC>(){});
		
		for(HostImpl host : hosts.getHostImpl()) {
			HostReaderImpl host_r = new HostReaderImpl(host);
			hostMap.put(host_r.getName(), host_r);
		}
		
		// Get the NFFG list
		resp = client.target(BaseURI + "/np/nffgs").request(MediaType.APPLICATION_XML).get(Response.class);
		
		if(resp.getStatus() != 200)
			throw new ServiceException();
		
		NffgC nffgs = resp.readEntity(new GenericType<NffgC>(){});
		
		for(NffgImpl nffg : nffgs.getNffgImpl()) {
			
			// Create a new NffgReader in order to store the nffg content
			NffgReaderImpl nffg_r = new NffgReaderImpl(nffg.getNameNffg(), nffg.getDeployTime().toGregorianCalendar());
			
			// The NffgReader is empty. Fill it with nodes and links
			  Map<String, NodeReaderImpl> nodeMap = new HashMap<>();
			  Map<String, LinkReaderImpl> linkMap  = new HashMap<>();
			
			for(NodeImpl node : nffg.getNodeImpl()) {
				NodeReaderImpl node_r = new NodeReaderImpl( node.getNodeName(),hostMap.get(node.getHostName()), nffg_r, vnfsMap.get(node.getFunctionaltypeId()));
				nodeMap.put(node.getNodeName(), node_r);
			}
			
			for(NodeImpl node : nffg.getNodeImpl()) {
				for(LinkImpl link : node.getLinkImpl()) {
					String linkName = link.getLinkName();
					NodeReader srcNode = nodeMap.get(link.getSourceNode());
					NodeReader dstNode = nodeMap.get(link.getDestinationNode());
					int lat = 0;
					if(link.getMaxLatency() != null)
						lat = link.getMaxLatency();
					float thr = (float)0.0;
					if(link.getMinThroughput() != null)
						thr = link.getMinThroughput();
					LinkReaderImpl link_r = new LinkReaderImpl(linkName, srcNode, dstNode, lat, thr);				
					linkMap.put(link.getLinkName(), link_r);
				}
			}
			
			
			// For each Node in the node map update the link list and add the node to the NFFG Reader
			for(NodeReaderImpl node_r : nodeMap.values()) {
				for(LinkReaderImpl link_r : linkMap.values()) {
					if(!link_r.getSourceNode().getName().equals(node_r.getName()))
						continue;
					node_r.addLink(link_r);
					//node_r.getLinks().add(link_r);
				}
				nffg_r.addNode(node_r);
				//nffg_r.getNodes().add(node_r);
			}
			nffgMap.put(nffg_r.getName(), nffg_r);
		}
		
		
		// For each Host in the host map update the node list
		for(HostReaderImpl host : hostMap.values()) {
			for(NffgReaderImpl nffg : nffgMap.values()) {
				for(NodeReader node : nffg.getNodes()) {
					if(node.getHost().getName().equals(host.getName())) {
						host.addNode(node);
					}
				}
			}
		}
	}


}
