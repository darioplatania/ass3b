/*package it.polito.dp2.NFV.sol3.client2;

import it.polito.dp2.NFV.*;
import javax.xml.bind.*;
import javax.xml.validation.*;
import java.io.*;
import java.util.*;
import it.polito.dp2.NFV.sol3.jaxb.*;
import org.xml.sax.SAXException;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class NfvReaderImpl implements NfvReader {
	
	private Np np = null;
	
	
	 * MyList
	 * 
	    
    private HashMap<String, NffgReaderImpl> nffgs = new HashMap<>();
    private HashMap<String, HostReaderImpl> host_list = new HashMap<>();
    private HashMap<String, VNFTypeReaderImpl> vnf_list = new HashMap<>();
    private HashMap<String, ConnectionPerformanceReaderImpl> cpr_list = new HashMap<>();
    private HashMap<String, NffgReaderImpl> nffg_cp = new HashMap<>();
	
	
	private static final String XSD_FOLDER = "xsd/";
    private static final String XSD_FILE = "nfvInfo.xsd";
    private static final String PACKAGE = "it.polito.dp2.NFV.sol1.jaxb";
    
    private int mem_compl_nodi;
    private int storage_compl;
    private int vnf_compl;
    
    
	public NfvReaderImpl() throws NfvReaderException {
		
		
		
		
		List<NffgType> nffgTypes = this.np.getNffg();
					
		if(nffgTypes.isEmpty())
			throw new NfvReaderException("No Nffgs are present");
		
		
		 * Create Hosts List
		 *
		 	
		for(HostType host : this.np.getIn().getHost()) {
			HostReaderImpl hri = new HostReaderImpl(host);
			host_list.put(host.getHostName(), hri);
		}
		if(host_list.isEmpty())
			throw new NfvReaderException("Host List is empty!");
	
		
		
		 * Create VNFs List
		 * 
		 
		for(FType ft : this.np.getCatalog().getFunctionaltype()) {
			VNFTypeReaderImpl myvnf = new VNFTypeReaderImpl(ft);
			vnf_list.put(ft.getFunctionaltypeId(),myvnf);
		}
		if(vnf_list.isEmpty())
			throw new NfvReaderException("VNF List is empty!");
		
		
		
		 *NF-FG/NODES/LINK 
		 * 
		 
		for(NffgType nffgType:nffgTypes){
			
			NffgReaderImpl nffgImpl = new NffgReaderImpl(nffgType.getNameNffg(),nffgType.getDeployTime().toGregorianCalendar());
			ArrayList<NodeReaderImpl> nodes = new ArrayList<NodeReaderImpl>();
			
			
			for(NodeType n:nffgType.getNode()){
				HostReaderImpl host_reader = cercaHost(n);
            		VNFTypeReaderImpl vnfri = cercaVnf(n);
            		
            		NodeReaderImpl newNode = new NodeReaderImpl(n.getNodeName(),host_reader,nffgImpl,vnfri);			
				
				if(!n.getLink().isEmpty()){
					
					for(LinkType l: n.getLink()){
						if(l.getSourceNode()!=l.getDestinationNode()) {
							NodeReaderImpl dstnode = cercaNodo(l.getDestinationNode(),nffgType,nffgImpl);
							if(dstnode == null)
								throw new NfvReaderException("Destination Node not found");
							
							LinkReaderImpl link_impl = new LinkReaderImpl(l.getLinkName(),newNode,dstnode,l.getMaxLatency(),l.getMinThroughput());
							newNode.addLink(link_impl);
						}
						else
							throw new NfvReaderException("Link Incorrect!");
					}
					
					 Vado a vedere se ci sono link che puntano a un destNode col mio stesso Id, ossia
					 * se esistono link che puntano a me che sono in uno stato incosistente.
					 * In tal caso vado ad aggiornare tale link.
					 
					
					for(int i=0;i<nodes.size();i++){						
						NodeReaderImpl tmp = nodes.get(i);
						if(!(tmp.getLinks().isEmpty())){
							for(LinkReader lr:tmp.getLinks()){
								if(lr.getDestinationNode().getName().equals(newNode.getName()))
									if(lr.getDestinationNode().getFuncType()==null){
										((LinkReaderImpl)nodes.get(i).getLink(lr.getName())).setDestinationNode(newNode);									
									}
							}
						}						
					}										
				}
				
				
				 * Add this node into set of nodes
				 							
				nodes.add(newNode);
				
				
				 * Add this node into NF-FG
				 	
				nffgImpl.addNode(newNode);				
			}

			
			 * Control Method
			 * 
			 
			
			//Check Memory
			System.out.println("**********CHECK MEM **********");
			System.out.println("NFFG NAME: " + nffgType.getNameNffg());
			
			for (Map.Entry<String, HostReaderImpl> host_r : host_list.entrySet()) {
					
			   mem_compl_nodi = 0;
			   System.out.println("Memoria Iniziale: " + mem_compl_nodi);
			   System.out.println("Numero di nodi: " + nodes.size());
			   System.out.println("numero di Host: " + host_list.size());
			   
			   System.out.println("Controllo Host nome: " + host_r.getValue().getName());
			   
		       for(int i = 0; i< nodes.size();i++) {
		         if(host_r.getValue().getName().equals(nodes.get(i).getHost().getName())) {
		           System.out.println("Host: " + host_r.getValue().getName() + "---Nodo host: " + nodes.get(i).getHost().getName() + "---Nodo nome: " + nodes.get(i).getName());
		           mem_compl_nodi+=nodes.get(i).getFuncType().getRequiredMemory();
		           System.out.println("Memoria Host: " + host_r.getValue().getAvailableMemory());
		           System.out.println("Memoria Complessiva Nodi: " + mem_compl_nodi);
		           if(host_r.getValue().getAvailableMemory()<mem_compl_nodi) {
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
			
			//Check Storage
			System.out.println("**********CHECK STORAGE **********");
			System.out.println("NFFG NAME: " + nffgType.getNameNffg());
			
			for (Map.Entry<String, HostReaderImpl> host_r : host_list.entrySet()) {
				
				   storage_compl = 0;
				   System.out.println("Storage Iniziale: " + storage_compl);
				   System.out.println("Numero di nodi: " + nodes.size());
				   System.out.println("numero di Host: " + host_list.size());
				   
				   System.out.println("Controllo Host nome: " + host_r.getValue().getName());
				   
			       for(int i = 0; i< nodes.size();i++) {
			         if(host_r.getValue().getName().equals(nodes.get(i).getHost().getName())) {
			           System.out.println("Host: " + host_r.getValue().getName() + "---Nodo host: " + nodes.get(i).getHost().getName() + "---Nodo nome: " + nodes.get(i).getName());
			           storage_compl+=nodes.get(i).getFuncType().getRequiredStorage();
			           System.out.println("Storage Host: " + host_r.getValue().getAvailableStorage());
			           System.out.println("Storage Complessivo: " + storage_compl);
			           if(host_r.getValue().getAvailableStorage()<storage_compl) {
			             System.out.println("DENTRO IF");
			             throw new NfvReaderException("Eccesso STORAGE!!!!!");
			           }
			             
			         }
			         else {
			           System.out.println("DENTRO ELSE");
			           continue;
			         }
			       }
			     }
			
			//Check VNF
			System.out.println("**********CHECK VNF **********");
			System.out.println("NFFG NAME: " + nffgType.getNameNffg());
			
			for (Map.Entry<String, HostReaderImpl> host_r : host_list.entrySet()) {
				
				vnf_compl = 0;
				System.out.println("VNF Iniziale: " + vnf_compl);
				System.out.println("Numero di nodi: " + nodes.size());
				System.out.println("numero di Host: " + host_list.size());
						
				System.out.println("Controllo Host nome: " + host_r.getValue().getName());
				 for(int i = 0; i< nodes.size();i++) {
			         if(host_r.getValue().getName().equals(nodes.get(i).getHost().getName())) {
			           System.out.println("Host: " + host_r.getValue().getName() + "---Nodo host: " + nodes.get(i).getHost().getName() + "---Nodo nome: " + nodes.get(i).getName());
			           vnf_compl += 1;
			           System.out.println("VNF Host: " + host_r.getValue().getMaxVNFs());
			           System.out.println("VNF Complessivo: " + vnf_compl);
						if(vnf_compl > host_r.getValue().getMaxVNFs()) {
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

			
			 * Add NF-FG into set of NetworkProvider NF-FG 
			 	
			this.nffgs.put(nffgType.getNameNffg(),nffgImpl);			
		}
		
		
		 * Host
		 * 
		 	
		for (Map.Entry<String, HostReaderImpl> host_r : host_list.entrySet()) {
			for(Map.Entry<String, NffgReaderImpl> nffg : nffgs.entrySet()) {
				for(NodeReader node : nffg.getValue().getNodes()) {
					if(node.getHost().getName().equals(host_r.getValue().getName())) {					
						host_r.getValue().addNode((NodeReaderImpl)node);		
					}					
				}
			}
		}

		
		 * Performance
		 * 
		 			
		for(PerformanceType pf : this.np.getIn().getPerformance()) {		
			ConnectionPerformanceReaderImpl cpri = new ConnectionPerformanceReaderImpl(pf);
			String var = pf.getSourceHost() + "-" + pf.getDestinationHost();
			cpr_list.put(var,cpri);			
		}		            
	}

	@Override
	public ConnectionPerformanceReader getConnectionPerformance(HostReader arg0, HostReader arg1)  {
		
		String var = arg0.getName() + "-" + arg1.getName();	
		return this.cpr_list.get(var);
			
	}

	@Override
	public HostReader getHost(String arg0) {
		for(Map.Entry<String, HostReaderImpl> hr : host_list.entrySet()){
			if(hr.getValue().getName().equals(arg0))
				return (HostReader) hr;
		}
		return null;
	}

	@Override
	public Set<HostReader> getHosts() {
		return new LinkedHashSet<HostReader>(this.host_list.values());
	}

	@Override
	public NffgReader getNffg(String arg0) {
		for(Map.Entry<String, NffgReaderImpl> nffgr:this.nffgs.entrySet()){
			if(nffgr.getValue().getName().equals(arg0))
				return (NffgReader) nffgr;
		}
		return null;
	}

	@Override
	public Set<NffgReader> getNffgs(Calendar arg0) {
		if(arg0 == null)
			return new LinkedHashSet<NffgReader>(this.nffgs.values());
		else {
			for(Map.Entry<String, NffgReaderImpl> nffgr:this.nffgs.entrySet()) {
				if(nffgr.getValue().getDeployTime().after(arg0))
					this.nffg_cp.put(nffgr.getValue().getName(), (NffgReaderImpl) nffgr);
			}
			return new LinkedHashSet<NffgReader>(this.nffg_cp.values());
		}			
	}

	@Override
	public Set<VNFTypeReader> getVNFCatalog() {
		return new LinkedHashSet<VNFTypeReader>(this.vnf_list.values());
	}
	
	 private NetworkProvider unmarshallDocument(File inputFile) throws JAXBException, SAXException, IllegalArgumentException {
	        JAXBContext myJAXBContext = JAXBContext.newInstance(PACKAGE);

	        SchemaFactory mySchemaFactory;
	        Schema mySchema;

			
	         * - creating the XML schema to validate the XML file before read it -
			 
	        mySchemaFactory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
	        mySchema = mySchemaFactory.newSchema(new File(XSD_FOLDER + XSD_FILE));

	        Unmarshaller myUnmarshaller = myJAXBContext.createUnmarshaller();
	        myUnmarshaller.setSchema(mySchema);

	        return (NetworkProvider) myUnmarshaller.unmarshal(inputFile);
	    }
	 
	 METHOD
	 
	 
	  *Search Node Method
	  * 
	  	 
	 private NodeReaderImpl cercaNodo(String node, NffgType nffg, NffgReaderImpl nffgri) {
	        for (NodeType nodeType : nffg.getNode()) {
	            if (nodeType.getNodeName().equals(node)) {
	            	HostReaderImpl hr = cercaHost(nodeType);
	            	VNFTypeReaderImpl vnf = cercaVnf(nodeType);
	                return new NodeReaderImpl(node,hr,nffgri,vnf);
	            }
	        }
	        return null;
	    }
	 
	 
	  *Search Host Method
	  * 
	  	 
	 private HostReaderImpl cercaHost(NodeType node) {
		 for(Map.Entry<String, HostReaderImpl> host : host_list.entrySet()  ) {
			 if(node.getHostName().equals(host.getValue().getName())) {
				 return host.getValue();
			 }
		 }		 
		 return null;		 
	 }
	 
	 
	  *Search VNF Method
	  * 
	  	 
	 private VNFTypeReaderImpl cercaVnf( NodeType node) {
		 for(Map.Entry<String, VNFTypeReaderImpl> vnf : vnf_list.entrySet()) {			 		 
			 if(vnf.getValue().getName().equals(node.getFunctionaltypeId())) {
				 return vnf.getValue();
			 }
		 }
		 return null;
	 }		 
}
*/