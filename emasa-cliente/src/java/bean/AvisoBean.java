/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;



import AvisoWS.Aviso;
import AvisoWS.AvisoWS_Service;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author shiba
 */
@Named(value = "avisoBean")
@SessionScoped
public class AvisoBean implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/Emasa-Soap-war/AvisoWS.wsdl")
    private AvisoWS_Service service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/Emasa-Soap-war/AvisoWS.wsdl")
    private AvisoWS_Service service;
   
    Aviso avisoSeleccionado; 
    String emailUsuario;
    List<Aviso> listaAvisosUsuario;
    /**
     * Creates a new instance of AvisoBean
     */
    public AvisoBean() {
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
    
    
    
    public String mostrarAvisos ()
    {
        //obtenemos la lista de avisos del usuario
        listaAvisosUsuario = findAvisoPorUsuario(emailUsuario);
        return "mostrarAvisos";
    }
    
    
    public Aviso getAvisoSeleccionado() {
        return avisoSeleccionado;
    }

    public void setAvisoSeleccionado(Aviso avisoSeleccionado) {
        this.avisoSeleccionado = avisoSeleccionado;
    }

    public List<Aviso> getListaAvisosUsuario() {
        return listaAvisosUsuario;
    }

    public void setListaAvisosUsuario(List<Aviso> listaAvisosUsuario) {
        this.listaAvisosUsuario = listaAvisosUsuario;
    }

    private java.util.List<Aviso> findAvisoPorUsuario(java.lang.String s) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        AvisoWS.AvisoWS port = service_1.getAvisoWSPort();
        return port.findAvisoPorUsuario(s);
    }

}
