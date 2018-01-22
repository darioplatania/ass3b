package it.polito.dp2.NFV.sol3.client2;

import java.util.*;

import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol3.jaxb.*;

public class HostReaderImpl implements HostReader {
	
	private String name;
	private int availablememory;
	private int availablestorage;
	private int maxvnf;
	private Map<String,NodeReader> nodes;
	
	public HostReaderImpl(HostImpl host){
		this.name = host.getHostName();
		this.availablememory = host.getMemory();
		this.availablestorage = host.getDiskStorage();
		this.maxvnf = host.getNumberVNFs();
		this.nodes = new HashMap<>();
	}

	@Override
	public int getAvailableMemory() {
		return availablememory;
	}

	@Override
	public int getAvailableStorage() {
		return availablestorage;
	}

	@Override
	public int getMaxVNFs() {
		return maxvnf;
	}

	@Override
	public Set<NodeReader> getNodes() {
		return new HashSet<NodeReader>(nodes.values());
	}
	
	 @Override
	 public String getName() {
		 return name;
	 }
	
	 public void addNode(NodeReader node) {
		 if(node != null) {
			 this.nodes.put(node.getName(), node);
		 }
	}
	
}
