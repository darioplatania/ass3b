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
 *       &lt;attribute name="functionaltypeId" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *       &lt;attribute name="functionalTypeName" use="required" type="{http://www.example.org/NfvDeployer/}fType" />
 *       &lt;attribute name="required_memory" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="required_storage" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "MyFunctionalType")
public class MyFunctionalType {

    @XmlAttribute(name = "functionaltypeId", required = true)
    protected String functionaltypeId;
    @XmlAttribute(name = "functionalTypeName", required = true)
    protected FType functionalTypeName;
    @XmlAttribute(name = "required_memory", required = true)
    protected int requiredMemory;
    @XmlAttribute(name = "required_storage", required = true)
    protected int requiredStorage;

    /**
     * Recupera il valore della proprietà functionaltypeId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionaltypeId() {
        return functionaltypeId;
    }

    /**
     * Imposta il valore della proprietà functionaltypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionaltypeId(String value) {
        this.functionaltypeId = value;
    }

    /**
     * Recupera il valore della proprietà functionalTypeName.
     * 
     * @return
     *     possible object is
     *     {@link FType }
     *     
     */
    public FType getFunctionalTypeName() {
        return functionalTypeName;
    }

    /**
     * Imposta il valore della proprietà functionalTypeName.
     * 
     * @param value
     *     allowed object is
     *     {@link FType }
     *     
     */
    public void setFunctionalTypeName(FType value) {
        this.functionalTypeName = value;
    }

    /**
     * Recupera il valore della proprietà requiredMemory.
     * 
     */
    public int getRequiredMemory() {
        return requiredMemory;
    }

    /**
     * Imposta il valore della proprietà requiredMemory.
     * 
     */
    public void setRequiredMemory(int value) {
        this.requiredMemory = value;
    }

    /**
     * Recupera il valore della proprietà requiredStorage.
     * 
     */
    public int getRequiredStorage() {
        return requiredStorage;
    }

    /**
     * Imposta il valore della proprietà requiredStorage.
     * 
     */
    public void setRequiredStorage(int value) {
        this.requiredStorage = value;
    }

}
