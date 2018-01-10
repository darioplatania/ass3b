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
 *         &lt;element ref="{http://www.example.org/NfvDeployer/}MyHost" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.example.org/NfvDeployer/}MyPerformance" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "myHost",
    "myPerformance"
})
@XmlRootElement(name = "MyIn")
public class MyIn {

    @XmlElement(name = "MyHost")
    protected List<MyHost> myHost;
    @XmlElement(name = "MyPerformance", required = true)
    protected List<MyPerformance> myPerformance;

    /**
     * Gets the value of the myHost property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the myHost property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMyHost().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MyHost }
     * 
     * 
     */
    public List<MyHost> getMyHost() {
        if (myHost == null) {
            myHost = new ArrayList<MyHost>();
        }
        return this.myHost;
    }

    /**
     * Gets the value of the myPerformance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the myPerformance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMyPerformance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MyPerformance }
     * 
     * 
     */
    public List<MyPerformance> getMyPerformance() {
        if (myPerformance == null) {
            myPerformance = new ArrayList<MyPerformance>();
        }
        return this.myPerformance;
    }

}
