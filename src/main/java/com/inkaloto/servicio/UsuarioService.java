/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.servicio;

import com.inkaloto.dao.UsuarioDAO;
import com.inkaloto.modelo.Usuario;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario login(String numeroDocumento, String contrasenaPlano) {
        String hash = contrasenaPlano;
        return usuarioDAO.buscarPorDocumentoYContrasena(numeroDocumento, hash);
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuarioDAO.existeDocumento(usuario.getNumero_documento())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese documento.");
        }
        if (usuarioDAO.existeCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo.");
        }

        usuarioDAO.registrar(usuario);
    }

    // NUEVO: actualizar usuario
    public void actualizarUsuario(Usuario usuario) {
        usuarioDAO.actualizar(usuario);
    }
}
