package Control;

//import Servicio.ComunicacionDB;
import java.util.Scanner;

//import DAO.UsuarioDAO;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        
        Plataforma plataforma = new Plataforma();

        plataforma.cargarYguardarAdministrador(in);


        in.close();

    }

}