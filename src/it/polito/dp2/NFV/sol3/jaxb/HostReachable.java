//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.20 alle 11:32:11 AM CET 
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
 *       &lt;attribute name="nameReachable" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "hostReachable")
public class HostReachable {

    @XmlAttribute(name = "nameReachable", required = true)
    protected String nameReachable;

    /**
     * Recupera il valore della proprietxE0 nameReachable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameReachable() {
        return nameReachable;
    }

    /**
     * Imposta il valore della proprietxE0 nameReachable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameReachable(String value) {
        this.nameReachable = value;
    }

}
