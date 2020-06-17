//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.17 at 12:16:20 PM CEST 
//


package rs.ac.uns.ftn.catalog;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rs.ac.uns.ftn.catalog package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.ac.uns.ftn.catalog
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCatalogRequest }
     * 
     */
    public GetCatalogRequest createGetCatalogRequest() {
        return new GetCatalogRequest();
    }

    /**
     * Create an instance of {@link GetCatalogResponse }
     * 
     */
    public GetCatalogResponse createGetCatalogResponse() {
        return new GetCatalogResponse();
    }

    /**
     * Create an instance of {@link Brand }
     * 
     */
    public Brand createBrand() {
        return new Brand();
    }

    /**
     * Create an instance of {@link Model }
     * 
     */
    public Model createModel() {
        return new Model();
    }

    /**
     * Create an instance of {@link FuelType }
     * 
     */
    public FuelType createFuelType() {
        return new FuelType();
    }

    /**
     * Create an instance of {@link GearboxType }
     * 
     */
    public GearboxType createGearboxType() {
        return new GearboxType();
    }

    /**
     * Create an instance of {@link VehicleClass }
     * 
     */
    public VehicleClass createVehicleClass() {
        return new VehicleClass();
    }

}
