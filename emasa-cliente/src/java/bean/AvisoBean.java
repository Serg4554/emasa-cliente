/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;


import avisows.AvisoWS_Service;
import avisows.Aviso;
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
    private AvisoWS_Service service;
   
    avisows.Aviso avisoSeleccionado; 
    String emailUsuario;
    List<avisows.Aviso> listaAvisosUsuario;
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

    private java.util.List<avisows.Aviso> findAvisoPorUsuario(java.lang.String s) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        avisows.AvisoWS port = service.getAvisoWSPort();
        return port.findAvisoPorUsuario(s);
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
}
