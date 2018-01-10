//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.10 alle 03:23:51 PM CET 
//


package it.polito.dp2.NFV.sol3.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="hostName" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *       &lt;attribute name="number_VNFs" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="memory" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="disk_storage" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "MyHost")
public class MyHost {

    @XmlAttribute(name = "hostName", required = true)
    protected String hostName;
    @XmlAttribute(name = "number_VNFs", required = true)
    protected int numberVNFs;
    @XmlAttribute(name = "memory", required = true)
    protected int memory;
    @XmlAttribute(name = "disk_storage", required = true)
    protected int diskStorage;

    /**
     * Recupera il valore della proprietà hostName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Imposta il valore della proprietà hostName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostName(String value) {
        this.hostName = value;
    }

    /**
     * Recupera il valore della proprietà numberVNFs.
     * 
     */
    public int getNumberVNFs() {
        return numberVNFs;
    }

    /**
     * Imposta il valore della proprietà numberVNFs.
     * 
     */
    public void setNumberVNFs(int value) {
        this.numberVNFs = value;
    }

    /**
     * Recupera il valore della proprietà memory.
     * 
     */
    public int getMemory() {
        return memory;
    }

    /**
     * Imposta il valore della proprietà memory.
     * 
     */
    public void setMemory(int value) {
        this.memory = value;
    }

    /**
     * Recupera il valore della proprietà diskStorage.
     * 
     */
    public int getDiskStorage() {
        return diskStorage;
    }

    /**
     * Imposta il valore della proprietà diskStorage.
     * 
     */
    public void setDiskStorage(int value) {
        this.diskStorage = value;
    }

}
