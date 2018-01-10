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
 *       &lt;attribute name="linkName" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *       &lt;attribute name="sourceNode" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *       &lt;attribute name="destinationNode" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *       &lt;attribute name="min_throughput" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="max_latency" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "MyLink")
public class MyLink {

    @XmlAttribute(name = "linkName", required = true)
    protected String linkName;
    @XmlAttribute(name = "sourceNode", required = true)
    protected String sourceNode;
    @XmlAttribute(name = "destinationNode", required = true)
    protected String destinationNode;
    @XmlAttribute(name = "min_throughput")
    protected Float minThroughput;
    @XmlAttribute(name = "max_latency")
    protected Integer maxLatency;

    /**
     * Recupera il valore della proprietà linkName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkName() {
        return linkName;
    }

    /**
     * Imposta il valore della proprietà linkName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkName(String value) {
        this.linkName = value;
    }

    /**
     * Recupera il valore della proprietà sourceNode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceNode() {
        return sourceNode;
    }

    /**
     * Imposta il valore della proprietà sourceNode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceNode(String value) {
        this.sourceNode = value;
    }

    /**
     * Recupera il valore della proprietà destinationNode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationNode() {
        return destinationNode;
    }

    /**
     * Imposta il valore della proprietà destinationNode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationNode(String value) {
        this.destinationNode = value;
    }

    /**
     * Recupera il valore della proprietà minThroughput.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMinThroughput() {
        return minThroughput;
    }

    /**
     * Imposta il valore della proprietà minThroughput.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMinThroughput(Float value) {
        this.minThroughput = value;
    }

    /**
     * Recupera il valore della proprietà maxLatency.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxLatency() {
        return maxLatency;
    }

    /**
     * Imposta il valore della proprietà maxLatency.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxLatency(Integer value) {
        this.maxLatency = value;
    }

}
