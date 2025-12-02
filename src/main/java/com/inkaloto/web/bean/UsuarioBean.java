/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.web.bean;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import java.sql.*;

@ManagedBean
@ViewScoped
public class UsuarioBean {
    private String nombres;
    private String apellidos;
    private String correo;
    private String celular;
    private String mensaje;
private String tipoDocumento;
private String numeroDocumento;


    public UsuarioBean() {
        // Cargar datos del usuario (usando el usuario de prueba con id=1)
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuario WHERE id_usuario = 1");
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                this.nombres = rs.getString("nombres");
                this.apellidos = rs.getString("apellidos");
                this.correo = rs.getString("correo");
                this.celular = rs.getString("celular");
                // Y en cargarDatosUsuario() agrega:
this.tipoDocumento = rs.getString("tipo_documento");
this.numeroDocumento = rs.getString("numero_documento");
                System.out.println("? Datos cargados: " + nombres + " " + apellidos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mensaje = "Error al cargar datos: " + e.getMessage();
        }
    }

    public void actualizarDatos() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "UPDATE usuario SET nombres=?, apellidos=?, correo=?, celular=? WHERE id_usuario=1")) {
            
            stmt.setString(1, nombres);
            stmt.setString(2, apellidos);
            stmt.setString(3, correo);
            stmt.setString(4, celular);
            
            int filas = stmt.executeUpdate();
            
            if (filas > 0) {
                mensaje = "¡Datos actualizados correctamente!";
                System.out.println("? Datos actualizados en BD");
            } else {
                mensaje = "Error: No se pudo actualizar los datos";
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getErrorCode() == 1062) {
                mensaje = "Error: El correo electrónico ya está en uso";
            } else {
                mensaje = "Error de base de datos: " + e.getMessage();
            }
        }
    }

    private Connection getConnection() throws SQLException {
        // Ajusta estas credenciales según tu base de datos
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/prueba1", 
            "root", 
            "" // tu password de MySQL
        );
    }

    // Getters y Setters
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    
    public String getTipoDocumento() { return tipoDocumento; }
public String getNumeroDocumento() { return numeroDocumento; }
}