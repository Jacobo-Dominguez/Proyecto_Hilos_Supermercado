package org.example.concurrencia;

import org.example.metricas.Estadisticas;
import org.example.model.Cliente;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Caja implements Runnable {

    private final int id;
    private final BlockingQueue<Cliente> cola;
    private final Estadisticas estadisticas;
    private volatile boolean activa = true;

    public Caja(int id,
                BlockingQueue<Cliente> cola,
                Estadisticas estadisticas) {
        this.id = id;
        this.cola = cola;
        this.estadisticas = estadisticas;
    }

    public void detener() {
        activa = false;
    }

    @Override
    public void run() {
        try {
            while (activa || !cola.isEmpty()) {

                Cliente cliente = cola.poll(1, TimeUnit.SECONDS);

                if (cliente != null) {

                    cliente.setInstanteInicioAtencion(System.currentTimeMillis());

                    Thread.sleep(cliente.getNumeroArticulos() * 100);

                    cliente.setInstanteSalida(System.currentTimeMillis());

                    estadisticas.registrarAtendido(
                            id,
                            cliente.getTiempoEspera(),
                            cliente.getTiempoAtencion()
                    );
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
