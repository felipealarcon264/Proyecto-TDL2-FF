package Control;

import java.util.Scanner;

import DAO.UsuarioDAOImpl;

import DataBase.InicializadorDB;

public class Main {
    public static void main(String[] args) {
        // SIEMPRE SE SUPONE QUE LA DB ESTA CREADA!
        Scanner in = new Scanner(System.in);

        
        //inicializador de tablas
         InicializadorDB inicializadorDB = new InicializadorDB();
         inicializadorDB.crearTablas();
        // fin inicializador de tablas

        //Plataforma plataforma = new Plataforma();

        //plataforma.cargarYguardarAdministrador(in);

        //plataforma.cargarYguardarCuenta(in);

        UsuarioDAOImpl usrDAO = new UsuarioDAOImpl();
        usrDAO.ListarTodosLosUsuariosEnPantalla();


        in.close();

    }

}