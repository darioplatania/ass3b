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
 *       &lt;attribute name="avg_throughput" use="required" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="latency" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="sourceHost" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *       &lt;attribute name="destinationHost" use="required" type="{http://www.example.org/NfvDeployer/}name" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "MyPerformance")
public class MyPerformance {

    @XmlAttribute(name = "avg_throughput", required = true)
    protected float avgThroughput;
    @XmlAttribute(name = "latency", required = true)
    protected int latency;
    @XmlAttribute(name = "sourceHost", required = true)
    protected String sourceHost;
    @XmlAttribute(name = "destinationHost", required = true)
    protected String destinationHost;

    /**
     * Recupera il valore della proprietà avgThroughput.
     * 
     */
    public float getAvgThroughput() {
        return avgThroughput;
    }

    /**
     * Imposta il valore della proprietà avgThroughput.
     * 
     */
    public void setAvgThroughput(float value) {
        this.avgThroughput = value;
    }

    /**
     * Recupera il valore della proprietà latency.
     * 
     */
    public int getLatency() {
        return latency;
    }

    /**
     * Imposta il valore della proprietà latency.
     * 
     */
    public void setLatency(int value) {
        this.latency = value;
    }

    /**
     * Recupera il valore della proprietà sourceHost.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceHost() {
        return sourceHost;
    }

    /**
     * Imposta il valore della proprietà sourceHost.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceHost(String value) {
        this.sourceHost = value;
    }

    /**
     * Recupera il valore della proprietà destinationHost.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationHost() {
        return destinationHost;
    }

    /**
     * Imposta il valore della proprietà destinationHost.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationHost(String value) {
        this.destinationHost = value;
    }

}
