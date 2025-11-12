package Ejemplo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraControlador {
    private CalculadoraModelo modelo;
    private CalculadoraVista vista;

    public CalculadoraControlador(CalculadoraModelo modelo, CalculadoraVista vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.getBotonMultiplicar().addActionListener(new MultiplicarListener());
    }

    class MultiplicarListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int numero1 = vista.getNumero1();
                int numero2 = vista.getNumero2();
                int resultado = modelo.multiplicar(numero1, numero2);
                vista.setResultado(resultado);
            } catch (NumberFormatException ex) {
                vista.mostrarMensajeError("Por favor ingrese numeros validos");

            }
        }
    }

}
