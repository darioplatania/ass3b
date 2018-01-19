package it.polito.dp2.NFV.sol3.client2;

import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.sol3.jaxb.*;

public class ConnectionPerformanceReaderImpl implements ConnectionPerformanceReader {
	
	private float throughput;
	private int latency;
	private String myhrsource;
	private String myhrdest;
	
	public ConnectionPerformanceReaderImpl(PerformanceImpl pf) {
		this.throughput = pf.getAvgThroughput();
		this.latency = (int) pf.getLatency();
		this.myhrsource = pf.getSourceHost();
		this.myhrdest = pf.getDestinationHost();
	}
	
	@Override
	public int getLatency() {
		return this.latency;
	}

	@Override
	public float getThroughput() {
		return this.throughput;
	}
	
	public String getsourceHost() {
		return this.myhrsource;
	}
	
	public String getdestHost() {
		return this.myhrdest;
	}
}
