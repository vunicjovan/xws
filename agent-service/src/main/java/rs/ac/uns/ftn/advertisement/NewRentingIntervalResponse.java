//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.22 at 11:14:13 PM CEST 
//


package rs.ac.uns.ftn.advertisement;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rentingInterval" type="{http://www.ftn.uns.ac.rs/advertisement}rentingInterval"/>
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
    "rentingInterval"
})
@XmlRootElement(name = "newRentingIntervalResponse")
public class NewRentingIntervalResponse {

    @XmlElement(required = true)
    protected RentingInterval rentingInterval;

    /**
     * Gets the value of the rentingInterval property.
     * 
     * @return
     *     possible object is
     *     {@link RentingInterval }
     *     
     */
    public RentingInterval getRentingInterval() {
        return rentingInterval;
    }

    /**
     * Sets the value of the rentingInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link RentingInterval }
     *     
     */
    public void setRentingInterval(RentingInterval value) {
        this.rentingInterval = value;
    }

}
