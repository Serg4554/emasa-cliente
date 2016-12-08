/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import avisows.Aviso;
import avisows.AvisoWS_Service;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceRef;
import operacionws.Operacion;
import operacionws.OperacionWS_Service;
//import operacionws.Operacion;
//import operacionws.OperacionWS_Service;
import usuariows.Usuario;
import usuariows.UsuarioWS_Service;

/**
 *
 * @author shiba
 */
@Named(value = "controlBean")
@SessionScoped
public class ControlBean implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/Emasa-Soap-war/OperacionWS.wsdl")
    private OperacionWS_Service service_2;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/Emasa-Soap-war/UsuarioWS.wsdl")
    private UsuarioWS_Service service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/Emasa-Soap-war/AvisoWS.wsdl")
    private AvisoWS_Service service;

    Aviso avisoSeleccionado;
    String latitud, longitud, error, ubicacion, dia, mes, anyo, observaciones;
    String emailUsuario;
    List<Aviso> listaAvisosUsuario;
    List<Operacion> listaOperaciones;
    Usuario usuarioActual;

    /**
     * Creates a new instance of ControlBean
     */
    public ControlBean() {
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = anyo;
    }

    private java.util.List<avisows.Aviso> findAvisoPorUsuario(java.lang.String s) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        avisows.AvisoWS port = service.getAvisoWSPort();
        return port.findAvisoPorUsuario(s);
    }

    private Usuario find(java.lang.Object id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        usuariows.UsuarioWS port = service_1.getUsuarioWSPort();
        return port.find(id);
    }

    public Aviso getAvisoSeleccionado() {
        return avisoSeleccionado;
    }

    public void setAvisoSeleccionado(Aviso avisoSeleccionado) {
        this.avisoSeleccionado = avisoSeleccionado;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public List<Aviso> getListaAvisosUsuario() {
        return listaAvisosUsuario;
    }

    public void setListaAvisosUsuario(List<Aviso> listaAvisosUsuario) {
        this.listaAvisosUsuario = listaAvisosUsuario;
    }

    public List<Operacion> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(List<Operacion> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String mostrarAvisos() {
        //obtenemos la lista de avisos del usuario
        listaAvisosUsuario = findAvisoPorUsuario(emailUsuario);
        return "mostrarAvisos";
    }

    public String verAviso(Aviso aviso) {
        avisoSeleccionado = aviso;
        listaOperaciones = getListaOperacionesAviso();
        return "detalleAviso";
    }

    private List<operacionws.Operacion> getListaOperacionesAviso() {
        return this.findListaOperaciones(avisoSeleccionado.getId());
    }

    private java.util.List<operacionws.Operacion> findListaOperaciones(int id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        operacionws.OperacionWS port = service_2.getOperacionWSPort();
        return port.findListaOperaciones(id);
    }

    public void comprobarUsuario() {
        usuarioActual = find(emailUsuario);
        if (usuarioActual == null) {
            usuarioActual = new Usuario();
            usuarioActual.setEmail(emailUsuario);
            usuarioActual.setOperador(false);
            create(usuarioActual);
        }
        avisows.Usuario usuarioAviso = new avisows.Usuario();
        usuarioAviso.setEmail(emailUsuario);
        usuarioAviso.setOperador(false);
        avisoSeleccionado.setUsuarioemail(usuarioAviso);
    }

    private void create(usuariows.Usuario entity) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        usuariows.UsuarioWS port = service_1.getUsuarioWSPort();
        port.create(entity);
    }

    public String crearAviso() {
        error="";
        return "crearAviso";
    }

    public String doGuardar() {
        error="";
        String fecha;
        avisoSeleccionado = new Aviso();
        if(!dia.isEmpty() && !mes.isEmpty() && !anyo.isEmpty()){
            fecha = dia + "-" + mes + "-" + anyo;
        }else{
            error="Fecha no válida";
            return "crearAviso";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        formatter.applyPattern("dd-MM-yyyy");
        if (fecha != null && !fecha.trim().isEmpty()) {
            try {
                Date date = formatter.parse(fecha);
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(date);
                avisoSeleccionado.setFechacreacion(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
            } catch (DatatypeConfigurationException e) {
                error = "El formato de fecha debe ser dd-MM-yyyy";
                return "crearAviso";
            } catch (ParseException ex) {
                error = "Fecha no válida";
                return "crearAviso";
            }
        }
        if(ubicacion==null || ubicacion.isEmpty()){
            error="La ubicación no puede ser vacía";
            return "crearAviso";
        }else{
            avisoSeleccionado.setUbicacion(ubicacion);
        }
        if(observaciones==null || observaciones.isEmpty()){
            error="El campo de observaciones no puede estar vacío";
            return "crearAviso";
        }else{
            avisoSeleccionado.setObservaciones(observaciones);
        }
        
        if(latitud==null || latitud.isEmpty() || longitud == null || longitud.isEmpty()){
            error="Los campos del posicionamiento GPS no pueden ser vacíos";
            return "crearAviso";
        }else{
            double lat = Double.parseDouble(latitud);
            double longi = Double.parseDouble(longitud);
            avisoSeleccionado.setPosGPS(lat+";"+longi);
        }
        avisoSeleccionado.setEstado("En espera de confirmación");
        comprobarUsuario();
        create_1(avisoSeleccionado);
        listaAvisosUsuario.add(avisoSeleccionado);
        return "mostrarAvisos";
    }

    private void create_1(avisows.Aviso entity) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        avisows.AvisoWS port = service.getAvisoWSPort();
        port.create(entity);
    }

    private int count() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        avisows.AvisoWS port = service.getAvisoWSPort();
        return port.count();
    }

    private Aviso find_1(java.lang.Object id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        avisows.AvisoWS port = service.getAvisoWSPort();
        return port.find(id);
    }

    
    
    
    
}
