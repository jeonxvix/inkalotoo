
package com.inkaloto.web.bean;

import com.inkaloto.modelo.Usuario;
import com.inkaloto.servicio.UsuarioService;
import com.inkaloto.servicio.BilleteraService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named("registroBean")
@RequestScoped
public class RegistroBean implements Serializable {

    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private String fechaNacimiento; // texto yyyy-MM-dd
    private String genero;
    private String correo;
    private String celular;
    private String departamento;
    private String provincia;
    private String contrasena;
    private String confirmarContrasena;

    private String mensajeExito;
    private String mensajeError;

    private final UsuarioService usuarioService = new UsuarioService();
    private final BilleteraService billeteraService = new BilleteraService();

    public String registrar() {
        mensajeExito = null;
        mensajeError = null;

        try {
            // Validar contraseñas
            if (contrasena == null || confirmarContrasena == null ||
                    !contrasena.equals(confirmarContrasena)) {
                mensajeError = "Las contraseñas no coinciden.";
                return null;
            }

            // Crear entidad usuario
            Usuario usuario = new Usuario();
            usuario.setNombres(nombres);
            usuario.setApellidos(apellidos);
            usuario.setTipo_documento(tipoDocumento);
            usuario.setNumero_documento(numeroDocumento);
            usuario.setGenero(genero);
            usuario.setCorreo(correo);
            usuario.setCelular(celular);
            usuario.setDepartamento(departamento);
            usuario.setProvincia(provincia);

            if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fnac = sdf.parse(fechaNacimiento);
                usuario.setFecha_nacimiento(fnac);
            }

            // Guardar contraseña sin encriptar (por ahora)
            usuario.setContrasena_hash(contrasena);

            // Registrar usuario
            usuarioService.registrarUsuario(usuario);

            // Crear billetera para el usuario
            billeteraService.crearBilleteraParaUsuario(usuario);

            mensajeExito = "Registro exitoso. Ahora puedes iniciar sesión.";
            limpiarFormulario();
            return null;

        } catch (ParseException e) {
            mensajeError = "Fecha de nacimiento inválida. Usa formato AAAA-MM-DD.";
            return null;

        } catch (IllegalArgumentException e) {
            mensajeError = e.getMessage();
            return null;

        } catch (Exception e) {
            mensajeError = "Ocurrió un error al registrar el usuario.";
            return null;
        }
    }

    private void limpiarFormulario() {
        nombres = null;
        apellidos = null;
        tipoDocumento = null;
        numeroDocumento = null;
        fechaNacimiento = null;
        genero = null;
        correo = null;
        celular = null;
        departamento = null;
        provincia = null;
        contrasena = null;
        confirmarContrasena = null;
    }

    // GETTERS Y SETTERS

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getConfirmarContrasena() { return confirmarContrasena; }
    public void setConfirmarContrasena(String confirmarContrasena) { this.confirmarContrasena = confirmarContrasena; }
    public String getMensajeExito() { return mensajeExito; }
    public void setMensajeExito(String mensajeExito) { this.mensajeExito = mensajeExito; }
    public String getMensajeError() { return mensajeError; }
    public void setMensajeError(String mensajeError) { this.mensajeError = mensajeError; }
}
