package it.polito.dp2.NFV.sol3.service;

import javax.xml.datatype.DatatypeConfigurationException;

import it.polito.dp2.NFV.NfvReaderException;

public class NfvInitializer {

	private static NfvInitializer nfvInit = null;
	
	public static NfvInitializer newInstance() throws NfvReaderException, DatatypeConfigurationException, Exception{
		if(nfvInit == null) {
			nfvInit = new NfvInitializer();
			init();
		}
		return nfvInit;
	}
	
	private NfvInitializer(){
	}
	
	private static void init() throws NfvReaderException, DatatypeConfigurationException, Exception {
		NfvDeployer dev = new NfvDeployer();
		dev.serializer();
	}
	
	
}
