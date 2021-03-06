//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.12 at 01:50:12 AM CEST 
//


package rs.ac.uns.ftn.catalog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vehicle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vehicle"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="kilometersTraveled" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="childSeatNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="hasAndroid" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="fuelTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="gearboxTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="vehicleClassId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vehicle", propOrder = {
    "id",
    "kilometersTraveled",
    "childSeatNumber",
    "hasAndroid",
    "fuelTypeId",
    "gearboxTypeId",
    "vehicleClassId",
    "modelId"
})
public class Vehicle {

    protected long id;
    protected int kilometersTraveled;
    protected int childSeatNumber;
    protected boolean hasAndroid;
    protected long fuelTypeId;
    protected long gearboxTypeId;
    protected long vehicleClassId;
    protected long modelId;

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
     * Gets the value of the kilometersTraveled property.
     * 
     */
    public int getKilometersTraveled() {
        return kilometersTraveled;
    }

    /**
     * Sets the value of the kilometersTraveled property.
     * 
     */
    public void setKilometersTraveled(int value) {
        this.kilometersTraveled = value;
    }

    /**
     * Gets the value of the childSeatNumber property.
     * 
     */
    public int getChildSeatNumber() {
        return childSeatNumber;
    }

    /**
     * Sets the value of the childSeatNumber property.
     * 
     */
    public void setChildSeatNumber(int value) {
        this.childSeatNumber = value;
    }

    /**
     * Gets the value of the hasAndroid property.
     * 
     */
    public boolean isHasAndroid() {
        return hasAndroid;
    }

    /**
     * Sets the value of the hasAndroid property.
     * 
     */
    public void setHasAndroid(boolean value) {
        this.hasAndroid = value;
    }

    /**
     * Gets the value of the fuelTypeId property.
     * 
     */
    public long getFuelTypeId() {
        return fuelTypeId;
    }

    /**
     * Sets the value of the fuelTypeId property.
     * 
     */
    public void setFuelTypeId(long value) {
        this.fuelTypeId = value;
    }

    /**
     * Gets the value of the gearboxTypeId property.
     * 
     */
    public long getGearboxTypeId() {
        return gearboxTypeId;
    }

    /**
     * Sets the value of the gearboxTypeId property.
     * 
     */
    public void setGearboxTypeId(long value) {
        this.gearboxTypeId = value;
    }

    /**
     * Gets the value of the vehicleClassId property.
     * 
     */
    public long getVehicleClassId() {
        return vehicleClassId;
    }

    /**
     * Sets the value of the vehicleClassId property.
     * 
     */
    public void setVehicleClassId(long value) {
        this.vehicleClassId = value;
    }

    /**
     * Gets the value of the modelId property.
     * 
     */
    public long getModelId() {
        return modelId;
    }

    /**
     * Sets the value of the modelId property.
     * 
     */
    public void setModelId(long value) {
        this.modelId = value;
    }

}
