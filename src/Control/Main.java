package Control;

import Entes.Datos_Personales;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello word!");
        Datos_Personales dp = new Main().cargaDatosPersonales();

    }

    /**
     * Verifica si una cadena de texto contiene únicamente letras (mayúsculas y/o minúsculas).
     * @param texto La cadena de texto a verificar.
     * @return {@code true} si la cadena contiene solo letras , {@code false} en caso contrario.
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
     *
     * @return Un objeto {@link Datos_Personales} con los datos ingresados.
     */
    public Datos_Personales cargaDatosPersonales() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        while (!contieneSoloLetras(nombre)) {
            System.out.print("Nombre inválido. Ingrese solo letras: ");
            nombre = scanner.nextLine();
        }
        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.nextLine();
        while (!contieneSoloLetras(apellido)) {
            System.out.print("Apellido inválido. Ingrese solo letras: ");
            apellido = scanner.nextLine();
        }
        System.out.print("Ingrese el DNI: ");
        int dni = 0;
        boolean dniValido = false;
        while (!dniValido) {
            try {
                dni = scanner.nextInt();
                dniValido = true;
            } catch (Exception e) {
                System.out.print("DNI inválido. Ingrese un número entero: ");
            }
            scanner.nextLine(); // Limpiar el buffer
        }

        Datos_Personales aux = new Datos_Personales(nombre, apellido, dni);
        System.out.println(aux);
        scanner.close();
        return aux;
    }
}