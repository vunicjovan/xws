//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.07 at 12:19:11 AM CEST 

package rs.ac.uns.ftn.advertisement;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="priceListItem" type="{http://www.ftn.uns.ac.rs/advertisement}priceListItem"/>
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
