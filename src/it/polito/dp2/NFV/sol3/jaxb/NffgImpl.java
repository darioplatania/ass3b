//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.23 alle 12:23:08 AM CET 
//


package it.polito.dp2.NFV.sol3.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.example.org/nfvInfo}nodeImpl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name_nffg" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *       &lt;attribute name="deploy_time" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nodeImpl"
})
@XmlRootElement(name = "nffgImpl")
public class NffgImpl {

    protected List<NodeImpl> nodeImpl;
    @XmlAttribute(name = "name_nffg", required = true)
    protected String nameNffg;
    @XmlAttribute(name = "deploy_time", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deployTime;

    /**
     * Gets the value of the nodeImpl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nodeImpl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNodeImpl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NodeImpl }
     * 
     * 
     */
    public List<NodeImpl> getNodeImpl() {
        if (nodeImpl == null) {
            nodeImpl = new ArrayList<NodeImpl>();
        }
        return this.nodeImpl;
    }

    /**
     * Recupera il valore della proprietxE0 nameNffg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameNffg() {
        return nameNffg;
    }

    /**
     * Imposta il valore della proprietxE0 nameNffg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameNffg(String value) {
        this.nameNffg = value;
    }

    /**
     * Recupera il valore della proprietxE0 deployTime.
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
     * Imposta il valore della proprietxE0 deployTime.
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
