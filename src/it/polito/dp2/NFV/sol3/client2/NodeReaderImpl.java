package it.polito.dp2.NFV.sol3.client2;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.VNFTypeReader;

import java.util.*;

public class NodeReaderImpl implements NodeReader {
	
		private String name;
		private HostReader hosts;
		private NffgReader nffgs;
		private VNFTypeReader vnfs;		
		private HashMap<String,LinkReader> links;
	
	
	public NodeReaderImpl(String name,HostReader hosts,NffgReader nffgs,VNFTypeReader vnfs) {
		this.name = name;
		this.hosts = hosts;
		links = new HashMap<>();
		this.nffgs = nffgs;
		this.vnfs = vnfs;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public VNFTypeReader getFuncType() {
		return vnfs;
	}

	@Override
	public HostReader getHost() {
		return hosts;
	}

	@Override
	public Set<LinkReader> getLinks() {
		return new HashSet<LinkReader>(links.values());
	}

	@Override
	public NffgReader getNffg() {
		return nffgs;
	}

	public void addLink(LinkReader link){
		if(link != null)
		links.put(link.getName(), link);
	}
	
	public LinkReader getLink(String LID){
		for(Map.Entry<String, LinkReader> lr: this.links.entrySet())
			if(lr.getValue().getName().equals(LID))
				return lr.getValue();
		return null;
	}
}
