
package it.unitn.lsde.ass3.assignment3;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "Exception", targetNamespace = "http://assignment3.ass3.lsde.unitn.it/")
public class Exception_Exception
    extends java.lang.Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private it.unitn.lsde.ass3.assignment3.Exception faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public Exception_Exception(String message, it.unitn.lsde.ass3.assignment3.Exception faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public Exception_Exception(String message, it.unitn.lsde.ass3.assignment3.Exception faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: it.unitn.lsde.ass3.assignment3.Exception
     */
    public it.unitn.lsde.ass3.assignment3.Exception getFaultInfo() {
        return faultInfo;
    }

}
