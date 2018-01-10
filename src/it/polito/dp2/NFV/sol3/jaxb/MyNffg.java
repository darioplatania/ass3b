//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.10 alle 03:23:51 PM CET 
//


package it.polito.dp2.NFV.sol3.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.example.org/NfvDeployer/}MyNode" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="nffgName" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *       &lt;attribute name="deployTime" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "myNode"
})
@XmlRootElement(name = "MyNffg")
public class MyNffg {

    @XmlElement(name = "MyNode")
    protected List<MyNode> myNode;
    @XmlAttribute(name = "nffgName", required = true)
    protected String nffgName;
    @XmlAttribute(name = "deployTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deployTime;

    /**
     * Gets the value of the myNode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the myNode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMyNode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MyNode }
     * 
     * 
     */
    public List<MyNode> getMyNode() {
        if (myNode == null) {
            myNode = new ArrayList<MyNode>();
        }
        return this.myNode;
    }

    /**
     * Recupera il valore della proprietà nffgName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNffgName() {
        return nffgName;
    }

    /**
     * Imposta il valore della proprietà nffgName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNffgName(String value) {
        this.nffgName = value;
    }

    /**
     * Recupera il valore della proprietà deployTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeployTime() {
        return deployTime;
    }

    /**
     * Imposta il valore della proprietà deployTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeployTime(XMLGregorianCalendar value) {
        this.deployTime = value;
    }

}
