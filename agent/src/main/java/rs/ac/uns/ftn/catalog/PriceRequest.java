//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.12 at 04:24:39 AM CEST 
//


package rs.ac.uns.ftn.catalog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="priceListItem" type="{http://www.ftn.uns.ac.rs/advertisement}priceListItem"/&gt;
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
    "priceListItem"
})
@XmlRootElement(name = "priceRequest")
public class PriceRequest {

    @XmlElement(required = true)
    protected PriceListItem priceListItem;

    /**
     * Gets the value of the priceListItem property.
     * 
     * @return
     *     possible object is
     *     {@link PriceListItem }
     *     
     */
    public PriceListItem getPriceListItem() {
        return priceListItem;
    }

    /**
     * Sets the value of the priceListItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceListItem }
     *     
     */
    public void setPriceListItem(PriceListItem value) {
        this.priceListItem = value;
    }

}
