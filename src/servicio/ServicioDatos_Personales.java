package servicio;

import dao.FactoryDAO;
import modelo.ente.Datos_Personales;
import dao.interfaces.Datos_PersonalesDAO;
import java.util.Scanner;


public class ServicioDatos_Personales {
    Datos_PersonalesDAO datosPersonalesDAO;

    public ServicioDatos_Personales() {
        this.datosPersonalesDAO = FactoryDAO.getDatosPersonalesDAO();
    }
    /**
     * Carga los datos personales desde la entrada estándar (consola).
     * Verifica que los nombres y apellidos contengan solo letras y que el DNI sea
     * un número entero positivo.
     * En caso de datos inválidos, solicita al usuario que reingrese los datos.
     * Verifica que el dni no haya sigo ingresado previamente a la base de datos.
     * Finalmente, solicíta confirmación de los datos ingresados caso contrario
     * reinicia el proceso.
     * Se puede cancelar.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return Un objeto {@link Datos_Personales} con los datos ingresados, o null
     *         si se cancela la carga.
     * No se usa para el entregable 3
     */
    /*public Datos_Personales cargaDatosPersonales(Scanner scanner) {
        System.out.println("Carga de datos personales: ");
        boolean datosValidos = false;
        String nombre = "";
        String apellido = "";
        int dni = -1;

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
                } catch (Exception err) {
                    System.out.print("DNI inválido. Ingrese un número entero: ");
                }
                if (dni < 0 && dniValido) {
                    System.out.print("DNI inválido. Ingrese un número entero positivo: ");
                    dniValido = false;
                }
                if (datosPersonalesDAO.buscarPorDNI(dni) != null && dniValido) {
                    System.out.print("DNI ya ingresado previamente. Ingrese un DNI diferente: ");
                    dniValido = false;
                }
                scanner.nextLine(); // Limpiar el buffer

            }
            System.out.println("Datos ingresados:" +
                    "\nNombre: " + nombre +
                    "\nApellido: " + apellido +
                    "\nDNI: " + dni);
            System.out.println("CONFIRMACIÓN DE CARGA -> DATOS PERSONALES.");
            datosValidos = confirmacion(scanner);
            if (!datosValidos) {
                System.out.println("Quieres cancelar la carga? ");
                datosValidos = confirmacion(scanner);
                if (datosValidos) {
                    System.out.println("Carga cancelada.💡");
                    return null;
                }
            }
        }
        System.out.println("Datos confirmados...");
        return new Datos_Personales(-1, nombre, apellido, dni); // Retorna un id no valido eso se asegura de brindárselo a la base de datos
    }*/

    /**
     * Solicita al usuario la confirmación de los datos ingresados.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     * 
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return true si el usuario confirma, false en caso contrario.
     */
    public boolean confirmacion(Scanner scanner) {
        System.out.print(" (S/N): ");
        String confirmacion = scanner.nextLine();
        while (!confirmacion.equalsIgnoreCase("S") && !confirmacion.equalsIgnoreCase("N")) {
            System.out.print("Entrada inválida. Ingrese 'S' para confirmar o 'N' para denegar: ");
            confirmacion = scanner.nextLine();
        }
        return confirmacion.equalsIgnoreCase("S");
    }

    /**
     * Verifica si una cadena de texto contiene únicamente letras (mayúsculas y/o
     * minúsculas).
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     * 
     * @param texto La cadena de texto a verificar.
     * @return true si la cadena contiene solo letras, false en caso contrario.
     */
    public boolean contieneSoloLetras(String texto) {
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


}
