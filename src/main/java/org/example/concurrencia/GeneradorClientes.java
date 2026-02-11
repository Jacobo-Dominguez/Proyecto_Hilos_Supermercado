package org.example.concurrencia;

import org.example.model.Cliente;
import org.example.metricas.Estadisticas;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneradorClientes implements Runnable {

    private final BlockingQueue<Cliente> cola;
    private final Estadisticas estadisticas;
    private final int duracionSimulacion;
    private final AtomicInteger contadorId;
    private final Random random = new Random();

    public GeneradorClientes(BlockingQueue<Cliente> cola,
                             Estadisticas estadisticas,
                             int duracionSimulacion,
                             AtomicInteger contadorId) {
        this.cola = cola;
        this.estadisticas = estadisticas;
        this.duracionSimulacion = duracionSimulacion;
        this.contadorId = contadorId;
    }

    @Override
    public void run() {
        long fin = System.currentTimeMillis() + duracionSimulacion;

        while (System.currentTimeMillis() < fin) {
            try {
                Thread.sleep(random.nextInt(500) + 200);

                Cliente cliente = new Cliente(
                        contadorId.incrementAndGet(),
                        random.nextInt(10) + 1
                );

                estadisticas.registrarGenerado();

                if (!cola.offer(cliente, 1, TimeUnit.SECONDS)) {
                    estadisticas.registrarAbandonado();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
