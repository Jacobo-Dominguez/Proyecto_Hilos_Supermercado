# Simulación Concurrente de un Supermercado en Java

## Descripción del proyecto

Este proyecto implementa una simulación concurrente de un sistema de atención al cliente en un supermercado, utilizando programación multihilo en Java.

El objetivo principal es modelar un escenario realista donde múltiples procesos se ejecutan en paralelo, gestionando correctamente recursos compartidos y evitando condiciones de carrera, además de obtener métricas fiables sobre el rendimiento del sistema.

La simulación se ejecuta completamente por consola.

## Objetivos del ejercicio

- Aplicar el patrón **Productor–Consumidor**
- Utilizar correctamente `BlockingQueue`
- Gestionar hilos mediante `ExecutorService`
- Evitar condiciones de carrera usando clases atómicas y colecciones concurrentes
- Cerrar el sistema de forma ordenada
- Calcular métricas estadísticas reales del sistema

## Arquitectura del sistema

El sistema está estructurado de forma modular, separando claramente responsabilidades.

### Estructura de paquetes

- `modelo`        → Entidades del proyecto (Cliente)
- `concurrencia`  → Lógica multihilo (productores y consumidores)
- `metricas`      → Gestión de estadísticas

---

### Componentes del sistema

#### Funcionamiento de la simulación
- Se crea la cola compartida.
- Se inicializan las estadísticas.
- Se lanza el generador de clientes.
- Se lanzan múltiples cajas en paralelo.
- Los clientes llegan, esperan en cola y son atendidos.
- Finalizado el tiempo de simulación:
- Se detiene el generador.
- Las cajas terminan de atender clientes pendientes.
- Se imprime el informe final.

### Métricas calculadas
Al finalizar la simulación se muestra un informe como:

```
--- INFORME FINAL ---
Clientes generados: 21
Clientes atendidos: 21
Clientes abandonados: 0
Tiempo medio de espera: 0.0 s
Tiempo medio de atención: 0.503 s
Tiempo total simulación: 10.495 s
Caja 1 -> 8 clientes
Caja 2 -> 7 clientes
Caja 3 -> 6 clientes
```
Se calculan:
- Número total de clientes generados
- Número total de clientes atendidos
- Número de clientes que abandonan por cola llena
- Tiempo medio de espera
- Tiempo medio de atención
- Tiempo total de simulación
- Número de clientes atendidos por cada caja

### Comportamiento observable
Dependiendo de la configuración:
- Más cajas → menor tiempo de espera
- Menos cajas → mayor congestión
- Cola pequeña → posibles abandonos