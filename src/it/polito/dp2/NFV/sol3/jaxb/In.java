//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.26 alle 07:14:55 PM CET 
//


package it.polito.dp2.NFV.sol3.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element ref="{http://www.example.org/nfvInfo}hostImpl" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.example.org/nfvInfo}performanceImpl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "hostImpl",
    "performanceImpl"
})
@XmlRootElement(name = "in")
public class In {

    @XmlElement(required = true)
    protected List<HostImpl> hostImpl;
    protected List<PerformanceImpl> performanceImpl;

    /**
     * Gets the value of the hostImpl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostImpl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostImpl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostImpl }
     * 
     * 
     */
    public List<HostImpl> getHostImpl() {
        if (hostImpl == null) {
            hostImpl = new ArrayList<HostImpl>();
        }
        return this.hostImpl;
    }

    /**
     * Gets the value of the performanceImpl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the performanceImpl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerformanceImpl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerformanceImpl }
     * 
     * 
     */
    public List<PerformanceImpl> getPerformanceImpl() {
        if (performanceImpl == null) {
            performanceImpl = new ArrayList<PerformanceImpl>();
        }
        return this.performanceImpl;
    }

}
