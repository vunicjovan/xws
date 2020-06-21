//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.21 at 07:44:03 PM CEST 
//


package rs.ac.uns.ftn.catalog;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for chat complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chat"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="senderId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="senderUsername" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="messages" type="{http://www.ftn.uns.ac.rs/message}message" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chat", namespace = "http://www.ftn.uns.ac.rs/message", propOrder = {
    "senderId",
    "senderUsername",
    "messages"
})
public class Chat {

    protected long senderId;
    @XmlElement(required = true)
    protected String senderUsername;
    @XmlElement(nillable = true)
    protected List<Message> messages;

    /**
     * Gets the value of the senderId property.
     * 
     */
    public long getSenderId() {
        return senderId;
    }

    /**
     * Sets the value of the senderId property.
     * 
     */
    public void setSenderId(long value) {
        this.senderId = value;
    }

    /**
     * Gets the value of the senderUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderUsername() {
        return senderUsername;
    }

    /**
     * Sets the value of the senderUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderUsername(String value) {
        this.senderUsername = value;
    }

    /**
     * Gets the value of the messages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Message }
     * 
     * 
     */
    public List<Message> getMessages() {
        if (messages == null) {
            messages = new ArrayList<Message>();
        }
        return this.messages;
    }

}
