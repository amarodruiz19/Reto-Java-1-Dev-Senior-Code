import java.util.Scanner; // Importamos la clase Scanner para entrada de datos
import java.util.Random; // Importamos Random para eventos aleatorios

public class SimuladorViajeInterplanetario {

    // Definición de constantes para los planetas y sus distancias desde la Tierra en millones de km
    static final String[] PLANETAS = {"Marte", "Júpiter", "Saturno"};
    static final double[] DISTANCIAS = {78.3, 628.7, 1275.0}; // Distancias en millones de km

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Inicialización del escáner para leer datos
        boolean salir = false; // Bandera para controlar el menú principal

        while (!salir) {
            System.out.println("\n--- Simulador de Viaje Interplanetario ---");
            System.out.println("1. Seleccionar un planeta de destino");
            System.out.println("2. Seleccionar una nave espacial");
            System.out.println("3. Iniciar simulación del viaje");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt(); // Leemos la opción del usuario
            scanner.nextLine(); // Limpiamos el buffer

            switch (opcion) {
                case 1:
                    seleccionarDestino(scanner);
                    break;
                case 2:
                    seleccionarNave(scanner);
                    break;
                case 3:
                    iniciarSimulacion(scanner);
                    break;
                case 4:
                    salir = true;
                    System.out.println("¡Gracias por usar el simulador! Hasta la próxima.");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }

        scanner.close(); // Cerramos el escáner al finalizar el programa
    }

    // Variables globales para guardar el estado del programa
    static int planetaSeleccionado = -1;
    static double velocidadNave = 0;
    static int pasajeros = 0;

    // Método para seleccionar el destino
    public static void seleccionarDestino(Scanner scanner) {
        System.out.println("\n--- Selección de Destino ---");
        for (int i = 0; i < PLANETAS.length; i++) {
            System.out.printf("%d. %s (%.1f millones de km)%n", i + 1, PLANETAS[i], DISTANCIAS[i]);
        }
        System.out.print("Seleccione un planeta por su número: ");
        int seleccion = scanner.nextInt();

        if (seleccion > 0 && seleccion <= PLANETAS.length) {
            planetaSeleccionado = seleccion - 1; // Guardamos el índice del planeta seleccionado
            System.out.printf("Ha seleccionado %s como destino.%n", PLANETAS[planetaSeleccionado]);
        } else {
            System.out.println("Selección no válida. Intente de nuevo.");
        }
    }

    // Método para seleccionar la nave espacial
    public static void seleccionarNave(Scanner scanner) {
        if (planetaSeleccionado == -1) {
            System.out.println("Debe seleccionar un planeta primero.");
            return;
        }

        System.out.println("\n--- Selección de Nave Espacial ---");
        System.out.println("1. Nave Rápida (Velocidad: 200,000 km/h)");
        System.out.println("2. Nave Balanceada (Velocidad: 150,000 km/h)");
        System.out.println("3. Nave Lenta (Velocidad: 100,000 km/h)");
        System.out.print("Seleccione una nave por su número: ");

        int seleccion = scanner.nextInt();
        switch (seleccion) {
            case 1:
                velocidadNave = 200000; // Velocidad en km/h
                break;
            case 2:
                velocidadNave = 150000;
                break;
            case 3:
                velocidadNave = 100000;
                break;
            default:
                System.out.println("Selección no válida.");
                return;
        }

        System.out.print("Ingrese la cantidad de pasajeros: ");
        pasajeros = scanner.nextInt();
        if (pasajeros > 0) {
            System.out.println("Nave y pasajeros configurados correctamente.");
        } else {
            System.out.println("Debe ingresar un número positivo para pasajeros.");
        }
    }

    // Método para iniciar la simulación del viaje
    public static void iniciarSimulacion(Scanner scanner) {
        if (planetaSeleccionado == -1 || velocidadNave == 0 || pasajeros <= 0) {
            System.out.println("Debe completar la selección de destino y nave antes de iniciar el viaje.");
            return;
        }

        double distancia = DISTANCIAS[planetaSeleccionado] * 1_000_000; // Convertir a km
        double duracion = distancia / velocidadNave; // Duración en horas
        double combustible = distancia * 0.5; // Cantidad de combustible requerida (arbitrario)
        double oxigeno = pasajeros * (duracion / 24) * 10; // Oxígeno requerido (arbitrario)

        System.out.println("\n--- Iniciando Viaje ---");
        System.out.printf("Destino: %s%n", PLANETAS[planetaSeleccionado]);
        System.out.printf("Distancia: %.1f km%n", distancia);
        System.out.printf("Duración estimada: %.1f horas (%.1f días)%n", duracion, duracion / 24);
        System.out.printf("Combustible necesario: %.1f litros%n", combustible);
        System.out.printf("Oxígeno necesario: %.1f unidades%n", oxigeno);

        Random random = new Random();
        double progreso = 0;

        while (progreso < 100) {
            progreso += 10; // Incrementamos el progreso
            System.out.printf("Progreso del viaje: %.0f%%%n", progreso);

            if (random.nextInt(100) < 20) { // Simulamos un evento aleatorio con probabilidad del 20%
                System.out.println("¡Evento inesperado! Fallo en los sistemas. ¿Desea reparar? (s/n)");
                char respuesta = scanner.next().charAt(0);
                if (respuesta != 's') {
                    System.out.println("La nave no pudo continuar. El viaje ha fallado.");
                    return;
                } else {
                    System.out.println("Reparación exitosa. Continuando el viaje.");
                }
            }

            try {
                Thread.sleep(1000); // Pausa para simular tiempo real
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("¡Llegada exitosa al destino!");
    }
}
