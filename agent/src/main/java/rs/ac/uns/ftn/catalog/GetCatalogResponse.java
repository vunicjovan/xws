//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.22 at 11:14:36 PM CEST 
//


package rs.ac.uns.ftn.catalog;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="brands" type="{http://www.ftn.uns.ac.rs/catalog}brand" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="models" type="{http://www.ftn.uns.ac.rs/catalog}model" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="fuelTypes" type="{http://www.ftn.uns.ac.rs/catalog}fuelType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="gearboxTypes" type="{http://www.ftn.uns.ac.rs/catalog}gearboxType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="vehicleClasses" type="{http://www.ftn.uns.ac.rs/catalog}vehicleClass" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "brands",
    "models",
    "fuelTypes",
    "gearboxTypes",
    "vehicleClasses"
})
@XmlRootElement(name = "getCatalogResponse", namespace = "http://www.ftn.uns.ac.rs/catalog")
public class GetCatalogResponse {

    @XmlElement(nillable = true)
    protected List<Brand> brands;
    @XmlElement(nillable = true)
    protected List<Model> models;
    @XmlElement(nillable = true)
    protected List<FuelType> fuelTypes;
    @XmlElement(nillable = true)
    protected List<GearboxType> gearboxTypes;
    @XmlElement(nillable = true)
    protected List<VehicleClass> vehicleClasses;

    /**
     * Gets the value of the brands property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the brands property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBrands().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Brand }
     * 
     * 
     */
    public List<Brand> getBrands() {
        if (brands == null) {
            brands = new ArrayList<Brand>();
        }
        return this.brands;
    }

    /**
     * Gets the value of the models property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the models property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Model }
     * 
     * 
     */
    public List<Model> getModels() {
        if (models == null) {
            models = new ArrayList<Model>();
        }
        return this.models;
    }

    /**
     * Gets the value of the fuelTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fuelTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFuelTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FuelType }
     * 
     * 
     */
    public List<FuelType> getFuelTypes() {
        if (fuelTypes == null) {
            fuelTypes = new ArrayList<FuelType>();
        }
        return this.fuelTypes;
    }

    /**
     * Gets the value of the gearboxTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gearboxTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGearboxTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GearboxType }
     * 
     * 
     */
    public List<GearboxType> getGearboxTypes() {
        if (gearboxTypes == null) {
            gearboxTypes = new ArrayList<GearboxType>();
        }
        return this.gearboxTypes;
    }

    /**
     * Gets the value of the vehicleClasses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vehicleClasses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVehicleClasses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VehicleClass }
     * 
     * 
     */
    public List<VehicleClass> getVehicleClasses() {
        if (vehicleClasses == null) {
            vehicleClasses = new ArrayList<VehicleClass>();
        }
        return this.vehicleClasses;
    }

}
