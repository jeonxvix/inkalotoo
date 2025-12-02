/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.modelo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario; // CAMBIADO: id_usuario ? idUsuario

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 20)
    private String tipo_documento;

    @Column(nullable = false, unique = true, length = 20)
    private String numero_documento;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha_nacimiento;

    @Column(nullable = false, length = 20)
    private String genero;

    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    @Column(nullable = false, length = 15)
    private String celular;

    @Column(nullable = false, length = 50)
    private String departamento;

    @Column(nullable = false, length = 50)
    private String provincia;

    @Column(nullable = false, length = 255)
    private String contrasena_hash;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha_registro;

    public Usuario() {
        this.fecha_registro = new Date();
    }

    // ==== Getters y Setters ACTUALIZADOS ====

    public Integer getIdUsuario() { // CAMBIADO
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) { // CAMBIADO
        this.idUsuario = idUsuario;
    }

    // Los demás getters y setters se mantienen igual...
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getNumero_documento() {
        return numero_documento;
    }

    public void setNumero_documento(String numero_documento) {
        this.numero_documento = numero_documento;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getContrasena_hash() {
        return contrasena_hash;
    }

    public void setContrasena_hash(String contrasena_hash) {
        this.contrasena_hash = contrasena_hash;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}