package Control;

import Entes.Datos_Personales;
import java.util.Scanner;

import DAO.Datos_PersonalesDAOImpl;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello word!");
        Scanner scanner = new Scanner(System.in);
        //DataBase.InicializadorDB.crearTablas(); //Crea la base de datos y las tablas.


        Datos_Personales dp = new Main().cargaDatosPersonales(scanner);

        Datos_PersonalesDAOImpl DPdao = new Datos_PersonalesDAOImpl();

        DPdao.guardar(dp);

        DPdao.borrar(DPdao.buscarPorDNI(45782737));
        
        scanner.close();

    }

    /**
     * Verifica si una cadena de texto contiene únicamente letras (mayúsculas y/o
     * minúsculas).
     * 
     * @param texto La cadena de texto a verificar.
     * @return {@code true} si la cadena contiene solo letras , {@code false} en
     *         caso contrario.
     */
    public static boolean contieneSoloLetras(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        for (char caracter : texto.toCharArray()) {
            if (!Character.isLetter(caracter)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Carga los datos personales desde la entrada estándar (consola).
     * Verifica que los nombres y apellidos contengan solo letras y que el DNI sea
     * un número entero positivo.
     * En caso de datos inválidos, solicita al usuario que reingrese los datos.
     * verifica que el dni no haya sigo ingresado previamente.
     * finalmente solicita confirmación de los datos ingresados.
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return Un objeto {@link Datos_Personales} con los datos ingresados.
     */
    public Datos_Personales cargaDatosPersonales(Scanner scanner) {
        boolean datosValidos = false;
        String nombre = "";
        String apellido = "";
        int dni = -1;
        Datos_PersonalesDAOImpl DPdao = new Datos_PersonalesDAOImpl();
        while (!datosValidos) {
            System.out.print("Ingrese el nombre: ");
            nombre = scanner.nextLine();
            while (!contieneSoloLetras(nombre)) {
                System.out.print("Nombre inválido. Ingrese solo letras: ");
                nombre = scanner.nextLine();
            }
            System.out.print("Ingrese el apellido: ");
            apellido = scanner.nextLine();
            while (!contieneSoloLetras(apellido)) {
                System.out.print("Apellido inválido. Ingrese solo letras: ");
                apellido = scanner.nextLine();
            }
            System.out.print("Ingrese el DNI: ");
            boolean dniValido = false;
            while (!dniValido) {
                try {
                    dni = scanner.nextInt();
                    dniValido = true;
                } catch (Exception e) {
                    System.out.print("DNI inválido. Ingrese un número entero: ");
                    dniValido = false;
                }
                if (dni < 0 && dniValido) {
                    System.out.print("DNI inválido. Ingrese un número entero positivo: ");
                    dniValido = false;
                }
                if (DPdao.buscarPorDNI(dni) != null && dniValido) {
                    System.out.print("DNI ya ingresado previamente. Ingrese un DNI diferente: ");
                    dniValido = false;
                }
                scanner.nextLine(); // Limpiar el buffer

            }
            System.out.println("Datos ingresados:" +
                    "\nNombre: " + nombre +
                    "\nApellido: " + apellido +
                    "\nDNI: " + dni);
            datosValidos = confirmacionDatos(scanner);
        }
        System.out.println("Datos confirmados...");
        return new Datos_Personales(nombre, apellido, dni);
    }

    /**
     * Solicita al usuario la confirmación de los datos ingresados.
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return {@code true} si el usuario confirma los datos, {@code false} si desea
     *         reingresar los datos.
     */
    public static boolean confirmacionDatos(Scanner scanner) {
        System.out.print("Confirmar datos (S/N): ");
        String confirmacion = scanner.nextLine();
        while (!confirmacion.equalsIgnoreCase("S") && !confirmacion.equalsIgnoreCase("N")) {
            System.out.print("Entrada inválida. Ingrese 'S' para confirmar o 'N' para reingresar los datos: ");
            confirmacion = scanner.nextLine();
        }
        return confirmacion.equalsIgnoreCase("S");
    }

}