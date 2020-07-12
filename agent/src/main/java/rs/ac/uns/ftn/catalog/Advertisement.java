//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.12 at 01:50:12 AM CEST 
//


package rs.ac.uns.ftn.catalog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for advertisement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="advertisement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="priceListItemId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="kilometersPerDayLimit" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="collisionDamageWaiver" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="rating" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vehicle" type="{http://www.ftn.uns.ac.rs/advertisement}vehicle"/&gt;
 *         &lt;element name="ownerId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "advertisement", propOrder = {
    "id",
    "price",
    "priceListItemId",
    "kilometersPerDayLimit",
    "collisionDamageWaiver",
    "rating",
    "description",
    "location",
    "vehicle",
    "ownerId"
})
public class Advertisement {

    protected long id;
    protected double price;
    protected long priceListItemId;
    protected int kilometersPerDayLimit;
    protected boolean collisionDamageWaiver;
    protected double rating;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String location;
    @XmlElement(required = true)
    protected Vehicle vehicle;
    protected long ownerId;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the price property.
     * 
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(double value) {
        this.price = value;
    }

    /**
     * Gets the value of the priceListItemId property.
     * 
     */
    public long getPriceListItemId() {
        return priceListItemId;
    }

    /**
     * Sets the value of the priceListItemId property.
     * 
     */
    public void setPriceListItemId(long value) {
        this.priceListItemId = value;
    }

    /**
     * Gets the value of the kilometersPerDayLimit property.
     * 
     */
    public int getKilometersPerDayLimit() {
        return kilometersPerDayLimit;
    }

    /**
     * Sets the value of the kilometersPerDayLimit property.
     * 
     */
    public void setKilometersPerDayLimit(int value) {
        this.kilometersPerDayLimit = value;
    }

    /**
     * Gets the value of the collisionDamageWaiver property.
     * 
     */
    public boolean isCollisionDamageWaiver() {
        return collisionDamageWaiver;
    }

    /**
     * Sets the value of the collisionDamageWaiver property.
     * 
     */
    public void setCollisionDamageWaiver(boolean value) {
        this.collisionDamageWaiver = value;
    }

    /**
     * Gets the value of the rating property.
     * 
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     * 
     */
    public void setRating(double value) {
        this.rating = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the vehicle property.
     * 
     * @return
     *     possible object is
     *     {@link Vehicle }
     *     
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Sets the value of the vehicle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vehicle }
     *     
     */
    public void setVehicle(Vehicle value) {
        this.vehicle = value;
    }

    /**
     * Gets the value of the ownerId property.
     * 
     */
    public long getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the value of the ownerId property.
     * 
     */
    public void setOwnerId(long value) {
        this.ownerId = value;
    }

}
