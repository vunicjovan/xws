//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.07 at 12:19:11 AM CEST 
//


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
 *         &lt;element name="rentingInterval" type="{http://www.ftn.uns.ac.rs/advertisement}rentingInterval"/>
 *         &lt;element name="advertisementId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "rentingInterval",
    "advertisementId"
})
@XmlRootElement(name = "newRentingIntervalRequest")
public class NewRentingIntervalRequest {

    @XmlElement(required = true)
    protected RentingInterval rentingInterval;
    protected long advertisementId;

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

    /**
     * Gets the value of the advertisementId property.
     * 
     */
    public long getAdvertisementId() {
        return advertisementId;
    }

    /**
     * Sets the value of the advertisementId property.
     * 
     */
    public void setAdvertisementId(long value) {
        this.advertisementId = value;
    }

}
