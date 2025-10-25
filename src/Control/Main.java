package Control;

import java.util.Scanner;

import DAO.UsuarioDAOImpl;

import DataBase.InicializadorDB;
import Entes.Usuario;

public class Main {
    public static void main(String[] args) {
        // SIEMPRE SE SUPONE QUE LA DB ESTA CREADA!
        Scanner in = new Scanner(System.in);

        // inicializador de tablas
       // InicializadorDB inicializadorDB = new InicializadorDB();
       // inicializadorDB.crearTablas();
        // fin inicializador de tablas

        Plataforma plataforma = new Plataforma();

        System.out.println("Longitud lista antes de borrar: " + plataforma.listaUSuario.size());

        Usuario usr = plataforma.listaUSuario.get(2); // elijo un usuario a la fuerza para borrar solo es prueba.

        plataforma.eliminarUsuario(usr);

        System.out.println("Longitud lista despues de borrar: " + plataforma.listaUSuario.size());

        UsuarioDAOImpl usrDAO = new UsuarioDAOImpl();
        usrDAO.ListarTodosLosUsuariosEnPantalla();

        in.close();

    }

}