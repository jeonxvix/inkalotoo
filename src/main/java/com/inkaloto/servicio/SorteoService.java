/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.servicio;

import com.inkaloto.dao.SorteoDAO;
import com.inkaloto.modelo.Sorteo;

import java.util.Random;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class SorteoService {

    private final SorteoDAO sorteoDAO = new SorteoDAO();

    // ? Ajusta este id al id_juego de tu juego SORTEO en la tabla `juego`
    private static final int ID_JUEGO_SORTEO = 2;

    /**
     * Obtiene el sorteo PROGRAMADO actual para el juego SORTEO.
     * Si no existe, crea uno nuevo en BD y lo devuelve.
     */
    public Sorteo obtenerOcrearSorteoActual() {
        Sorteo s = sorteoDAO.buscarUltimoProgramadoPorJuego(ID_JUEGO_SORTEO);
        if (s != null) {
            return s;
        }

        // No hay sorteo PROGRAMADO, creamos uno nuevo
        s = new Sorteo();
        s.setIdJuego(ID_JUEGO_SORTEO);
        // fechaProgramada y estadoSorteo se setean en el constructor
        s.setNumerosGanadores(null);

        sorteoDAO.crear(s);
        return s;
    }

    /**
     * Ejecuta el sorteo: genera números ganadores y marca como EJECUTADO.
     */
    public Sorteo ejecutarSorteo(Integer idSorteo) {

        Sorteo sorteo;

        if (idSorteo != null) {
            sorteo = sorteoDAO.obtenerPorId(idSorteo);
        } else {
            // si no nos pasan id, usamos el sorteo actual o creamos uno
            sorteo = obtenerOcrearSorteoActual();
        }

        if (sorteo == null) {
            throw new IllegalStateException("No se encontró el sorteo con id " + idSorteo);
        }

        if ("EJECUTADO".equalsIgnoreCase(sorteo.getEstadoSorteo())) {
            // ya fue ejecutado, simplemente lo devolvemos
            return sorteo;
        }

        String numerosGanadores = generarNumerosGanadores();
        sorteo.setNumerosGanadores(numerosGanadores);
        sorteo.setEstadoSorteo("EJECUTADO");

        sorteoDAO.actualizar(sorteo);
        return sorteo;
    }

    // genera 6 números distintos del 1 al 45, ej: "5-12-23-31-38-44"
    private String generarNumerosGanadores() {
        Random random = new Random();
        Set<Integer> numeros = new LinkedHashSet<>();

        while (numeros.size() < 6) {
            int n = random.nextInt(45) + 1; // 1-45
            numeros.add(n);
        }

        return numeros.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("-"));
    }
}
