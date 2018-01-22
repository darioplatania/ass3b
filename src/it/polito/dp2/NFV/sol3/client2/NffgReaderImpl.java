package it.polito.dp2.NFV.sol3.client2;

import java.util.*;

import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NodeReader;


public class NffgReaderImpl implements NffgReader {
	
	 String name;
	 Calendar DeployTime;
	 private HashMap<String,NodeReader> nodes;	
	 //private Set<NodeReader> nodes;
		
	public NffgReaderImpl(String name,Calendar DeployTime){
		this.name = name;
		this.DeployTime = DeployTime;
		this.nodes = new HashMap<>();
		//this.nodes = new HashSet<>();
		
	}
		
	 
	 public void addNode(NodeReaderImpl n){
			if(n!=null)
				nodes.put(n.getName(), n);
				//this.nodes.add(n);
		}
	 
	 @Override
	 public Calendar getDeployTime() {
		 return DeployTime;
	 }
	 
	 @Override
	 public Set<NodeReader> getNodes(){
		 return new HashSet<NodeReader>(nodes.values());
		 //return this.nodes;
	 }
	 
	 @Override
		public NodeReader getNode(String arg0) {
		 return nodes.get(arg0);
	}
	 
	 @Override
	 public String getName() {
		 return name;
	 }
	 
}
