//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.19 alle 04:29:39 PM CET 
//


package it.polito.dp2.NFV.sol3.jaxb;

import java.util.ArrayList;
import java.util.List;
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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.example.org/nfvInfo}hostReachable" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.example.org/nfvInfo}linkImpl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="node_name" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *       &lt;attribute name="host_name" type="{http://www.example.org/nfvInfo}name" /&gt;
 *       &lt;attribute name="nffg_rif" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *       &lt;attribute name="functionaltypeId" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "hostReachable",
    "linkImpl"
})
@XmlRootElement(name = "nodeImpl")
public class NodeImpl {

    protected List<HostReachable> hostReachable;
    protected List<LinkImpl> linkImpl;
    @XmlAttribute(name = "node_name", required = true)
    protected String nodeName;
    @XmlAttribute(name = "host_name")
    protected String hostName;
    @XmlAttribute(name = "nffg_rif", required = true)
    protected String nffgRif;
    @XmlAttribute(name = "functionaltypeId", required = true)
    protected String functionaltypeId;

    /**
     * Gets the value of the hostReachable property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostReachable property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostReachable().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostReachable }
     * 
     * 
     */
    public List<HostReachable> getHostReachable() {
        if (hostReachable == null) {
            hostReachable = new ArrayList<HostReachable>();
        }
        return this.hostReachable;
    }

    /**
     * Gets the value of the linkImpl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the linkImpl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLinkImpl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LinkImpl }
     * 
     * 
     */
    public List<LinkImpl> getLinkImpl() {
        if (linkImpl == null) {
            linkImpl = new ArrayList<LinkImpl>();
        }
        return this.linkImpl;
    }

    /**
     * Recupera il valore della proprietxE0 nodeName.
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
     * Imposta il valore della proprietxE0 nodeName.
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
     * Recupera il valore della proprietxE0 hostName.
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
     * Imposta il valore della proprietxE0 hostName.
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
     * Recupera il valore della proprietxE0 nffgRif.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNffgRif() {
        return nffgRif;
    }

    /**
     * Imposta il valore della proprietxE0 nffgRif.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNffgRif(String value) {
        this.nffgRif = value;
    }

    /**
     * Recupera il valore della proprietxE0 functionaltypeId.
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
     * Imposta il valore della proprietxE0 functionaltypeId.
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
