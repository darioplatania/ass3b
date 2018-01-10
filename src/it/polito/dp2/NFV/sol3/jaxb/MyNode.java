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
 *       &lt;sequence>
 *         &lt;element ref="{http://www.example.org/NfvDeployer/}MyLink" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="nodeName" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *       &lt;attribute name="hostName" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *       &lt;attribute name="functionaltypeId" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "myLink"
})
@XmlRootElement(name = "MyNode")
public class MyNode {

    @XmlElement(name = "MyLink")
    protected List<MyLink> myLink;
    @XmlAttribute(name = "nodeName", required = true)
    protected String nodeName;
    @XmlAttribute(name = "hostName", required = true)
    protected String hostName;
    @XmlAttribute(name = "functionaltypeId", required = true)
    protected String functionaltypeId;

    /**
     * Gets the value of the myLink property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the myLink property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMyLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MyLink }
     * 
     * 
     */
    public List<MyLink> getMyLink() {
        if (myLink == null) {
            myLink = new ArrayList<MyLink>();
        }
        return this.myLink;
    }

    /**
     * Recupera il valore della proprietà nodeName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Imposta il valore della proprietà nodeName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeName(String value) {
        this.nodeName = value;
    }

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

}
