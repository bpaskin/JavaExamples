
package com.ibm.jaxws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ibm.jaxws.client package. 
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

    private final static QName _GetRandomResponse_QNAME = new QName("http://services.jaxws.ibm.com/", "getRandomResponse");
    private final static QName _GetRandom_QNAME = new QName("http://services.jaxws.ibm.com/", "getRandom");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ibm.jaxws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetRandomResponse }
     * 
     */
    public GetRandomResponse createGetRandomResponse() {
        return new GetRandomResponse();
    }

    /**
     * Create an instance of {@link GetRandom_Type }
     * 
     */
    public GetRandom_Type createGetRandom_Type() {
        return new GetRandom_Type();
    }

    /**
     * Create an instance of {@link ResponseMessage }
     * 
     */
    public ResponseMessage createResponseMessage() {
        return new ResponseMessage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRandomResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.jaxws.ibm.com/", name = "getRandomResponse")
    public JAXBElement<GetRandomResponse> createGetRandomResponse(GetRandomResponse value) {
        return new JAXBElement<GetRandomResponse>(_GetRandomResponse_QNAME, GetRandomResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRandom_Type }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.jaxws.ibm.com/", name = "getRandom")
    public JAXBElement<GetRandom_Type> createGetRandom(GetRandom_Type value) {
        return new JAXBElement<GetRandom_Type>(_GetRandom_QNAME, GetRandom_Type.class, null, value);
    }

}
