package org.example.metricas;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Estadisticas {

    private final AtomicInteger clientesGenerados = new AtomicInteger();
    private final AtomicInteger clientesAtendidos = new AtomicInteger();
    private final AtomicInteger clientesAbandonados = new AtomicInteger();

    private final AtomicLong totalTiempoEspera = new AtomicLong();
    private final AtomicLong totalTiempoAtencion = new AtomicLong();

    private final ConcurrentHashMap<Integer, AtomicInteger> clientesPorCaja = new ConcurrentHashMap<>();

    public void registrarGenerado() {
        clientesGenerados.incrementAndGet();
    }

    public void registrarAbandonado() {
        clientesAbandonados.incrementAndGet();
    }

    public void registrarAtendido(int idCaja, long espera, long atencion) {
        clientesAtendidos.incrementAndGet();
        totalTiempoEspera.addAndGet(espera);
        totalTiempoAtencion.addAndGet(atencion);

        clientesPorCaja
                .computeIfAbsent(idCaja, k -> new AtomicInteger())
                .incrementAndGet();
    }

    public void mostrarInforme(long tiempoTotalSimulacion) {

        System.out.println("\n--- INFORME FINAL ---");

        int generados = clientesGenerados.get();
        int atendidos = clientesAtendidos.get();

        System.out.println("Clientes generados: " + generados);
        System.out.println("Clientes atendidos: " + atendidos);
        System.out.println("Clientes abandonados: " + clientesAbandonados.get());

        if (atendidos > 0) {
            System.out.println("Tiempo medio de espera: "
                    + (totalTiempoEspera.get() / atendidos) / 1000.0 + " s");

            System.out.println("Tiempo medio de atención: "
                    + (totalTiempoAtencion.get() / atendidos) / 1000.0 + " s");
        }

        System.out.println("Tiempo total simulación: "
                + tiempoTotalSimulacion / 1000.0 + " s");

        clientesPorCaja.forEach((caja, total) ->
                System.out.println("Caja " + caja + " -> " + total.get() + " clientes"));
    }
}
