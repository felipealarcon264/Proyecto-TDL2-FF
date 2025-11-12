package Ejemplo;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;

public class CalculadoraVista extends JFrame {
    private JTextField campoNumero1 = new JTextField(10);
    private JTextField campoNumero2 = new JTextField(10);
    private JButton botonMultiplicar = new JButton("Multiplicar");
    private JTextField campoResultado = new JTextField(10);

    public CalculadoraVista() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 5));

        add(new JLabel("Numero 1:"));
        add(campoNumero1);
        add(new JLabel("Numero 2:"));
        add(campoNumero2);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(botonMultiplicar);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel("Resultado:"));
        add(campoResultado);
        campoResultado.setEditable(false);
        setVisible(true);
    }

    public int getNumero1() {
        return Integer.parseInt(campoNumero1.getText());
    }

    public int getNumero2() {
        return Integer.parseInt(campoNumero2.getText());
    }

    public void setResultado(int resultado) {
        campoResultado.setText(String.valueOf(resultado));
    }

    public JButton getBotonMultiplicar() {
        return botonMultiplicar;
    }

    public void mostrarMensajeError(String mensaje) {
        System.out.println(mensaje);
    }
}
