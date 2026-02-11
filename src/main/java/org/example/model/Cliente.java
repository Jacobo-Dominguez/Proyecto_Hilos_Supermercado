package org.example.model;

public class Cliente {

    private final int id;
    private final int numeroArticulos;
    private final long instanteLlegada;
    private long instanteInicioAtencion;
    private long instanteSalida;

    public Cliente(int id, int numeroArticulos) {
        this.id = id;
        this.numeroArticulos = numeroArticulos;
        this.instanteLlegada = System.currentTimeMillis();
    }

    public int getId() { return id; }
    public int getNumeroArticulos() { return numeroArticulos; }
    public long getInstanteLlegada() { return instanteLlegada; }

    public void setInstanteInicioAtencion(long instanteInicioAtencion) {
        this.instanteInicioAtencion = instanteInicioAtencion;
    }

    public void setInstanteSalida(long instanteSalida) {
        this.instanteSalida = instanteSalida;
    }

    public long getTiempoEspera() {
        return instanteInicioAtencion - instanteLlegada;
    }

    public long getTiempoAtencion() {
        return instanteSalida - instanteInicioAtencion;
    }
}
