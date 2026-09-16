package org.example.test.Models;

import java.time.LocalDate;

public class Cliente {
    private String nombre;
    private String apellido;
    private String tipoCliente;
    private String ciudad;
    private LocalDate fechaNacimiento;
    private String tipoSolicitud;
    private String serviciosInteres;
    private String rutaFoto;



    public Cliente(String nombre, String apellido, String ciudad, String tipoCliente, LocalDate fechaNacimiento, String tipoSolicitud, String serviciosInteres, String rutaFoto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.ciudad = ciudad;
        this.tipoCliente = tipoCliente;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSolicitud = tipoSolicitud;
        this.serviciosInteres = serviciosInteres;
        this.rutaFoto = rutaFoto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getServiciosInteres() {
        return serviciosInteres;
    }

    public void setServiciosInteres(String serviciosInteres) {
        this.serviciosInteres = serviciosInteres;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }
}
