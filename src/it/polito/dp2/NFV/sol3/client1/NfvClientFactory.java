package it.polito.dp2.NFV.sol3.client1;

import it.polito.dp2.NFV.lab3.NfvClient;
import it.polito.dp2.NFV.lab3.NfvClientException;

public class NfvClientFactory extends it.polito.dp2.NFV.lab3.NfvClientFactory{

	@Override
	public NfvClient newNfvClient() throws NfvClientException {
		NfvClient  nfvClientImpl= new NfvClientImpl();
		return nfvClientImpl;
	}

}
