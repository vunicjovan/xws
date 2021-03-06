//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.08 at 02:03:22 PM CEST 
//


package rs.ac.uns.ftn.advertisement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for priceListItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="priceListItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dailyPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="cdwPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="debtPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="serviceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "priceListItem", propOrder = {
    "dailyPrice",
    "cdwPrice",
    "debtPrice",
    "serviceId",
    "agentId"
})
public class PriceListItem {

    protected double dailyPrice;
    protected double cdwPrice;
    protected double debtPrice;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long serviceId;
    protected long agentId;

    /**
     * Gets the value of the dailyPrice property.
     * 
     */
    public double getDailyPrice() {
        return dailyPrice;
    }

    /**
     * Sets the value of the dailyPrice property.
     * 
     */
    public void setDailyPrice(double value) {
        this.dailyPrice = value;
    }

    /**
     * Gets the value of the cdwPrice property.
     * 
     */
    public double getCdwPrice() {
        return cdwPrice;
    }

    /**
     * Sets the value of the cdwPrice property.
     * 
     */
    public void setCdwPrice(double value) {
        this.cdwPrice = value;
    }

    /**
     * Gets the value of the debtPrice property.
     * 
     */
    public double getDebtPrice() {
        return debtPrice;
    }

    /**
     * Sets the value of the debtPrice property.
     * 
     */
    public void setDebtPrice(double value) {
        this.debtPrice = value;
    }

    /**
     * Gets the value of the serviceId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * Sets the value of the serviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setServiceId(Long value) {
        this.serviceId = value;
    }

    /**
     * Gets the value of the agentId property.
     * 
     */
    public long getAgentId() {
        return agentId;
    }

    /**
     * Sets the value of the agentId property.
     * 
     */
    public void setAgentId(long value) {
        this.agentId = value;
    }

}
