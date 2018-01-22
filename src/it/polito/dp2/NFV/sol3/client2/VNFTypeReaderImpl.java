package it.polito.dp2.NFV.sol3.client2;


import it.polito.dp2.NFV.FunctionalType;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol3.jaxb.*;

public class VNFTypeReaderImpl implements VNFTypeReader {
	
	private String name;
	private int requiredmemory;
	private int requiredstorage;	
	private FunctionalType functionalType;
	private NodeFunctionalType fType;
		
	public VNFTypeReaderImpl(FtypeImpl functionalType) {
		this.name = functionalType.getFunctionaltypeId();
		this.requiredmemory = functionalType.getRequiredMemory();
		this.requiredstorage = functionalType.getRequiredStorage();
		this.functionalType = FunctionalType.valueOf(functionalType.getFunctionalTypeName().toString());
		this.fType = functionalType.getFunctionalTypeName();
	}

	@Override
	public FunctionalType getFunctionalType() {
		return functionalType;
	}

	@Override
	public int getRequiredMemory() {
		return requiredmemory;
	}

	@Override
	public int getRequiredStorage() {
		return requiredstorage;
	}
	
	public String getName() {
		return name;
	}			
}
