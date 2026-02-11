package org.example;

import org.example.concurrencia.Caja;
import org.example.concurrencia.GeneradorClientes;
import org.example.metricas.Estadisticas;
import org.example.model.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import java.util.concurrent.atomic.AtomicInteger;

public class SupermercadoApp {

    public static void main(String[] args) throws InterruptedException {

        int numeroCajas = 3;
        int capacidadCola = 50;
        int duracionSimulacion = 10000; // 10 segundos

        BlockingQueue<Cliente> cola = new ArrayBlockingQueue<>(capacidadCola);
        Estadisticas estadisticas = new Estadisticas();
        AtomicInteger contadorId = new AtomicInteger();

        ExecutorService executor = Executors.newFixedThreadPool(numeroCajas + 1);

        long inicio = System.currentTimeMillis();

        // Generador
        executor.execute(new GeneradorClientes(
                cola, estadisticas, duracionSimulacion, contadorId));

        // Cajas
        List<Caja> cajas = new ArrayList<>();

        for (int i = 1; i <= numeroCajas; i++) {
            Caja caja = new Caja(i, cola, estadisticas);
            cajas.add(caja);
            executor.execute(caja);
        }

        Thread.sleep(duracionSimulacion);

        cajas.forEach(Caja::detener);

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long fin = System.currentTimeMillis();

        estadisticas.mostrarInforme(fin - inicio);
    }
}