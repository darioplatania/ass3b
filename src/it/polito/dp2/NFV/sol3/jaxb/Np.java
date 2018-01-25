//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.25 alle 09:00:02 PM CET 
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
 *         &lt;element ref="{http://www.example.org/nfvInfo}nffgImpl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.example.org/nfvInfo}in"/&gt;
 *         &lt;element ref="{http://www.example.org/nfvInfo}catalog"/&gt;
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
    "nffgImpl",
    "in",
    "catalog"
})
@XmlRootElement(name = "np")
public class Np {

    protected List<NffgImpl> nffgImpl;
    @XmlElement(required = true)
    protected In in;
    @XmlElement(required = true)
    protected Catalog catalog;

    /**
     * Gets the value of the nffgImpl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nffgImpl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNffgImpl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NffgImpl }
     * 
     * 
     */
    public List<NffgImpl> getNffgImpl() {
        if (nffgImpl == null) {
            nffgImpl = new ArrayList<NffgImpl>();
        }
        return this.nffgImpl;
    }

    /**
     * Recupera il valore della proprietxE0 in.
     * 
     * @return
     *     possible object is
     *     {@link In }
     *     
     */
    public In getIn() {
        return in;
    }

    /**
     * Imposta il valore della proprietxE0 in.
     * 
     * @param value
     *     allowed object is
     *     {@link In }
     *     
     */
    public void setIn(In value) {
        this.in = value;
    }

    /**
     * Recupera il valore della proprietxE0 catalog.
     * 
     * @return
     *     possible object is
     *     {@link Catalog }
     *     
     */
    public Catalog getCatalog() {
        return catalog;
    }

    /**
     * Imposta il valore della proprietxE0 catalog.
     * 
     * @param value
     *     allowed object is
     *     {@link Catalog }
     *     
     */
    public void setCatalog(Catalog value) {
        this.catalog = value;
    }

}
