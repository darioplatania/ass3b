package it.polito.dp2.NFV.sol3.client1;


import java.util.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


import it.polito.dp2.NFV.lab3.*;

import it.polito.dp2.NFV.sol3.jaxb.*;

public class NfvClientImpl implements NfvClient{
	
	private String BaseURI;
	private Client client;
	private Response resp;
	private final static String PROPERTY ="it.polito.dp2.NFV.lab3.URL";
	
	private int nffg_count = 1;
	
	public NfvClientImpl(){
		
	// create the basic URL as a String
	BaseURI = System.getProperty(PROPERTY);

     if (BaseURI == null)
         BaseURI = "http://localhost:8080/NfvDeployer/rest";
 
     /*Init Client*/
	client = ClientBuilder.newClient();
	}
	

	@Override
	public DeployedNffg deployNffg(NffgDescriptor nffg) throws ServiceException, AllocationException{	
					
			// TODO Auto-generated method stub
			
		    int node_count = 0;
	
			String nffgName = "Nffg"+nffg_count;
			
			
			NffgImpl nffgimpl = new NffgImpl();
			
			
			nffgimpl.setNameNffg(nffgName);
			nffgimpl.setDeployTime(getCurrentTime());
			
			System.out.println("Nome nffg: " + nffgName);
			
			Map<NodeDescriptor, String> Nodes_client = new HashMap<>();
			
			
			for(NodeDescriptor nodedesc : nffg.getNodes()) {
				String nomeNodo = nodedesc.getFuncType().getName() + node_count + nffgName;
				Nodes_client.put(nodedesc, nomeNodo );
				node_count++;
			}
			
			int link_count = 0;
			for(NodeDescriptor n : nffg.getNodes()) {
				NodeImpl node = new NodeImpl();
				
				String nomeNodo_map = Nodes_client.get(n);
				
				
				node.setNodeName(nomeNodo_map);
				node.setNffgRif(nffgName);
				node.setHostName(n.getHostName());
				node.setFunctionaltypeId(n.getFuncType().getName());
				
				System.out.println("Nome setNodo: " + node.getNodeName());
				//System.out.println("Nome setNffg: " + node.getNffgRif());
				System.out.println("Nome setHost: " + node.getHostName());
				System.out.println("Nome setFType: " + node.getFunctionaltypeId());
				
				
				
				//Nodes_client.put(node.getNodeName(), node);
				for(LinkDescriptor l : n.getLinks()){
				
					//if(l.getSourceNode()==n) {
						LinkImpl link = new LinkImpl();
						String linkName = "Link" + link_count;
						
						//if(nomeNodo_map.equals(l.getSourceNode().getFuncType().getName()+node_count+nffgName))
							//break;
						
						link.setLinkName(linkName);
						link.setSourceNode(nomeNodo_map);
						link.setDestinationNode(Nodes_client.get(l.getDestinationNode()));
						link.setMaxLatency(0);
						link.setMinThroughput(new Float (0.0));
						link.setOverwrite(false);
						
						System.out.println("Nome setLink: " + link.getLinkName());
						System.out.println("Nome setDest: " + link.getDestinationNode());
						System.out.println("Nome setSource: " + link.getSourceNode());
					
						
						node.getLinkImpl().add(link);
						
						link_count++;
					//}
				}
				
				nffgimpl.getNodeImpl().add(node);
				
			}
			
		nffg_count++;
			
		/*Post properties*/
		resp = client.target(BaseURI+"/np/addNffg").request(MediaType.APPLICATION_XML).post(Entity.entity(nffgimpl,MediaType.APPLICATION_XML),Response.class);
		
		if(resp.getStatus() == 400 || resp.getStatus() == 403)
			throw new AllocationException();
		if(resp.getStatus() == 500)
			throw new ServiceException("Post nffg client1 Error " + resp.getStatus());
		
		DeployedNffg dp_nffg = new DeployedNffgImpl(nffgimpl);
		
		return dp_nffg;
		
	}

	@Override
	public DeployedNffg getDeployedNffg(String name) throws UnknownEntityException, ServiceException {
		// TODO Auto-generated method stub
		
		resp = client.target(BaseURI+"/np/nffgs/"+name).request(MediaType.APPLICATION_XML).get(Response.class);
		if(resp.getStatus() == 400)
			throw new UnknownEntityException();
		if(resp.getStatus() != 200)
			throw new ServiceException();
		
		NffgImpl getNffg = resp.readEntity(new GenericType<NffgImpl>(){});
		
		DeployedNffg dp_nffg1 = new DeployedNffgImpl(getNffg);
		
		return dp_nffg1;
	}
	


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

}
