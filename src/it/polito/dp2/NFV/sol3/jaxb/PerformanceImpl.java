//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.20 alle 12:24:11 PM CET 
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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="avg_throughput" use="required" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *       &lt;attribute name="latency" use="required" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *       &lt;attribute name="sourceHost" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *       &lt;attribute name="destinationHost" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "performanceImpl")
public class PerformanceImpl {

    @XmlAttribute(name = "avg_throughput", required = true)
    protected float avgThroughput;
    @XmlAttribute(name = "latency", required = true)
    protected float latency;
    @XmlAttribute(name = "sourceHost", required = true)
    protected String sourceHost;
    @XmlAttribute(name = "destinationHost", required = true)
    protected String destinationHost;

    /**
     * Recupera il valore della proprietxE0 avgThroughput.
     * 
     */
    public float getAvgThroughput() {
        return avgThroughput;
    }

    /**
     * Imposta il valore della proprietxE0 avgThroughput.
     * 
     */
    public void setAvgThroughput(float value) {
        this.avgThroughput = value;
    }

    /**
     * Recupera il valore della proprietxE0 latency.
     * 
     */
    public float getLatency() {
        return latency;
    }

    /**
     * Imposta il valore della proprietxE0 latency.
     * 
     */
    public void setLatency(float value) {
        this.latency = value;
    }

    /**
     * Recupera il valore della proprietxE0 sourceHost.
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
     * Imposta il valore della proprietxE0 sourceHost.
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
     * Recupera il valore della proprietxE0 destinationHost.
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
     * Imposta il valore della proprietxE0 destinationHost.
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
