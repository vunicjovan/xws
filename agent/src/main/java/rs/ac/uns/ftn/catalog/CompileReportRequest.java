//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.06 at 11:32:28 PM CEST 
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
 *         &lt;element name="kilometersTraveled" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="requestID" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="advertisementID" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
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
    "kilometersTraveled",
    "content",
    "requestID",
    "advertisementID"
})
@XmlRootElement(name = "compileReportRequest", namespace = "http://www.ftn.uns.ac.rs/renting")
public class CompileReportRequest {

    protected int kilometersTraveled;
    @XmlElement(required = true)
    protected String content;
    protected long requestID;
    protected long advertisementID;

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
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the requestID property.
     * 
     */
    public long getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     */
    public void setRequestID(long value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the advertisementID property.
     * 
     */
    public long getAdvertisementID() {
        return advertisementID;
    }

    /**
     * Sets the value of the advertisementID property.
     * 
     */
    public void setAdvertisementID(long value) {
        this.advertisementID = value;
    }

}
