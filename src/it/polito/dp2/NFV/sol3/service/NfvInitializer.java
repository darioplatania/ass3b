package it.polito.dp2.NFV.sol3.service;

import javax.xml.datatype.DatatypeConfigurationException;

import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.lab3.ServiceException;

public class NfvInitializer {

	private static NfvInitializer nfvInit = null;
	
	public static NfvInitializer newInstance() throws NfvReaderException, DatatypeConfigurationException, ServiceException{
		if(nfvInit == null) {
			nfvInit = new NfvInitializer();
			init();
		}
		return nfvInit;
	}
	
	private NfvInitializer(){
	}
	
	private static void init() throws NfvReaderException, DatatypeConfigurationException, ServiceException {
		NfvDeployer dev = new NfvDeployer();
		dev.serializer();
	}
	
	
}
