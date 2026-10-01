/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cabinapeaje;

/**
 *
 * @author GIGABYTE
 */
import java.util.Random;

public class CabinaPeaje implements Runnable {

    // Variable global estatica sin proteccion de concurrencia
    public static int totalRecaudadoGlobal = 0;

    private String idCabina;
    private int vehiculosAtendidos;
    private int vehiculosAProcesar;

    public CabinaPeaje(String idCabina, int vehiculosAProcesar) {
        this.idCabina = idCabina;
        this.vehiculosAProcesar = vehiculosAProcesar;
        this.vehiculosAtendidos = 0;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < vehiculosAProcesar; i++) {
            try {
                // Simulacion de tiempo de cobro: entre 500 y 1500 ms
                int tiempoCobro = 500 + random.nextInt(1001);
                Thread.sleep(tiempoCobro);

                // Incremento de contadores
                vehiculosAtendidos++;
                totalRecaudadoGlobal += 50;

                System.out.println("[" + idCabina + "] Proceso vehiculo #" + vehiculosAtendidos);

            } catch (InterruptedException e) {
                System.err.println("La cabina " + idCabina + " fue interrumpida.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public String getIdCabina() {
        return idCabina;
    }

    public int getVehiculosAtendidos() {
        return vehiculosAtendidos;
    }

    public static void main(String[] args) {
        // Primera cabina con el apellido Ojeda
        CabinaPeaje cabina1 = new CabinaPeaje("Cabina Ojeda", 10);
        CabinaPeaje cabina2 = new CabinaPeaje("Cabina 2", 10);
        CabinaPeaje cabina3 = new CabinaPeaje("Cabina 3", 10);

        Thread hilo1 = new Thread(cabina1);
        Thread hilo2 = new Thread(cabina2);
        Thread hilo3 = new Thread(cabina3);

        System.out.println("--- INICIANDO SIMULACION DE PEAJE ---");
        hilo1.start();
        hilo2.start();
        hilo3.start();

        try {
            // Espera a que los tres hilos terminen su ejecucion
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            System.err.println("El hilo principal fue interrumpido.");
            Thread.currentThread().interrupt();
        }

        // Reporte Final
        System.out.println("\n==================================");
        System.out.println("       REPORTE DE CIERRE         ");
        System.out.println("==================================");
        System.out.println(cabina1.getIdCabina() + " atendio: " + cabina1.getVehiculosAtendidos() + " vehiculos.");
        System.out.println(cabina2.getIdCabina() + " atendio: " + cabina2.getVehiculosAtendidos() + " vehiculos.");
        System.out.println(cabina3.getIdCabina() + " atendio: " + cabina3.getVehiculosAtendidos() + " vehiculos.");
        
        int totalEsperado = (cabina1.getVehiculosAtendidos() + cabina2.getVehiculosAtendidos() + cabina3.getVehiculosAtendidos()) * 50;
        System.out.println("----------------------------------");
        System.out.println("Total esperado teorico: " + totalEsperado);
        System.out.println("Total recaudado registrado: " + totalRecaudadoGlobal);
        System.out.println("==================================");
    }
}