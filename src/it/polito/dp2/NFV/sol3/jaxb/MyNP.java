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
 *         &lt;element ref="{http://www.example.org/NfvDeployer/}MyNffg" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.example.org/NfvDeployer/}MyIn"/>
 *         &lt;element ref="{http://www.example.org/NfvDeployer/}MyCatalog"/>
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
    "myNffg",
    "myIn",
    "myCatalog"
})
@XmlRootElement(name = "MyNP")
public class MyNP {

    @XmlElement(name = "MyNffg")
    protected List<MyNffg> myNffg;
    @XmlElement(name = "MyIn", required = true)
    protected MyIn myIn;
    @XmlElement(name = "MyCatalog", required = true)
    protected MyCatalog myCatalog;

    /**
     * Gets the value of the myNffg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the myNffg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMyNffg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MyNffg }
     * 
     * 
     */
    public List<MyNffg> getMyNffg() {
        if (myNffg == null) {
            myNffg = new ArrayList<MyNffg>();
        }
        return this.myNffg;
    }

    /**
     * Recupera il valore della proprietà myIn.
     * 
     * @return
     *     possible object is
     *     {@link MyIn }
     *     
     */
    public MyIn getMyIn() {
        return myIn;
    }

    /**
     * Imposta il valore della proprietà myIn.
     * 
     * @param value
     *     allowed object is
     *     {@link MyIn }
     *     
     */
    public void setMyIn(MyIn value) {
        this.myIn = value;
    }

    /**
     * Recupera il valore della proprietà myCatalog.
     * 
     * @return
     *     possible object is
     *     {@link MyCatalog }
     *     
     */
    public MyCatalog getMyCatalog() {
        return myCatalog;
    }

    /**
     * Imposta il valore della proprietà myCatalog.
     * 
     * @param value
     *     allowed object is
     *     {@link MyCatalog }
     *     
     */
    public void setMyCatalog(MyCatalog value) {
        this.myCatalog = value;
    }

}
