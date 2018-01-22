package it.polito.dp2.NFV.sol3.client2;

import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NodeReader;

public class LinkReaderImpl implements LinkReader {
	
	String name;
	private NodeReader source;
	private NodeReader dest;
	private int latency;
	private float throughput;
	

	public LinkReaderImpl(String name,NodeReader source, NodeReader dest,int latency,float throughput) {
		this.name = name;
		this.source = source;
		this.dest = dest;
		this.latency = latency;
		this.throughput = throughput;
	}
	
	public LinkReaderImpl(String name,NodeReader source, NodeReader dest) {
		this.name = name;
		this.source = source;
		this.dest = dest;
		this.latency = 0;
		this.throughput = (float) 0.0;
	}
	
	@Override
	public NodeReader getDestinationNode() {
		return dest;
	}
	
	@Override
	public NodeReader getSourceNode() {
		return source;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getLatency() {
		return latency;
	}

	@Override
	public float getThroughput() {
		return throughput;
	}
	
	public void setDestinationNode(NodeReader dest){
		this.dest = dest;
	}
	

}
