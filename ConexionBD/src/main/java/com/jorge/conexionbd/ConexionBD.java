/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jorge.conexionbd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 *
 * @author jorge
 */
public class ConexionBD {
    static final String DB_URL = "jdbc:mysql://localhost:3306/jcvd";
   static final String USER = "dam";
   static final String PASS = "dam";
   static final String QUERY = "SELECT * FROM videojuegos";

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        
            //buscaNombre
        /*System.out.println("Introduce el nombre de un juego: ");
        String n=sc.nextLine();
        boolean result= buscaNombre(n);
        System.out.println(result);*/
        
            //lanzaConsulta
        /*String consulta="SELECT FechaLanzamiento FROM videojuegos";
        lanzaConsulta(consulta);*/
        
            //nuevoRegistro
        /*Date fecha = Date.valueOf("2017-03-03");
        nuevoRegistro("The Legend of Zelda: Breath of the Wild", "Aventura", fecha, "Nintendo", 70);
        */
            
            //nuevoRegistro
        //nuevoRegistro();
        
            //eliminarRegistro
        String juegoAEliminar = "The Legend of Zelda: Breath of the Wild";
        boolean eliminar = eliminarRegistro(juegoAEliminar);

        if (eliminar) {
            System.out.println("Videojuego eliminado exitosamente.");
        } else {
            System.out.println("No se pudo eliminar el videojuego o no existe en la base de datos.");
        }
    }
    
    public static boolean buscaNombre(String nombre){
        boolean nom=false;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY);) {
         // Extract data from result set
         while (rs.next()) {
            // Retrieve by column name
             String nombreJuego = rs.getString("nombre");
             if (nombreJuego.equals(nombre)) {
                    nom = true;
                }
         }
         
         stmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      } 
        System.out.println(nom);
        return nom;
    }
    
    public static void lanzaConsulta(String consulta){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(consulta);) {

            while (rs.next()) {
                System.out.println("Resultado: " + rs.getString("FechaLanzamiento"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void nuevoRegistro(String nombre, String genero, Date fechaLanzamiento, String compañia, int precio) {
        String consulta = "INSERT INTO videojuegos (NOmbre, Genero, FechaLanzamiento, Compañia, Precio) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = conn.prepareStatement(consulta)) {

            // Establecer los valores de los parámetros
            statement.setString(1, nombre);
            statement.setString(2, genero);
            statement.setDate(3, fechaLanzamiento);
            statement.setString(4, compañia);
            statement.setInt(5, precio);

            // Ejecutar la consulta
            int filasAfectadas = statement.executeUpdate();

            // Verificar si se ha insertado correctamente
            if (filasAfectadas > 0) {
                System.out.println("Nuevo videojuego creado exitosamente.");
            } else {
                System.out.println("No se pudo insertar el nuevo videojuego.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void nuevoRegistro() {
        Scanner sc = new Scanner(System.in);
        String nombre, genero, compañia, fechaLanzamiento;
        int  precio;


        System.out.println("Ingrese el nombre del videojuego:");
        nombre = sc.nextLine();

        System.out.println("Ingrese el género del videojuego:");
        genero = sc.nextLine();

        System.out.println("Ingrese la fecha de lanzamiento del videojuego:");
        fechaLanzamiento = sc.nextLine(); 
        Date fecha = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fecha = (Date) dateFormat.parse(fechaLanzamiento);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Ingrese la compañia del videojuego:");
        compañia = sc.nextLine();
        
        System.out.println("Ingrese el precio del videojuego:");
        precio = sc.nextInt();
        sc.nextLine();

        String consulta = "INSERT INTO videojuegos (titulo, plataforma, ano_lanzamiento, desarrolladora) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = conn.prepareStatement(consulta)) {

            // Establecer los valores de los parámetros
            statement.setString(1, nombre);
            statement.setString(2, genero);
            statement.setDate(3, new java.sql.Date(fecha.getTime()));
            statement.setString(4, compañia);
            statement.setInt(5, precio);

            // Ejecutar la consulta
            int filasAfectadas = statement.executeUpdate();

            // Verificar si se ha insertado correctamente
            if (filasAfectadas > 0) {
                System.out.println("Nuevo videojuego creado exitosamente.");
            } else {
                System.out.println("No se pudo insertar el nuevo videojuego.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
    
    public static boolean eliminarRegistro(String nombreVideojuego) {
        String consulta = "DELETE FROM videojuegos WHERE Nombre = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = conn.prepareStatement(consulta)) {

            // Establecer el valor del parámetro
            statement.setString(1, nombreVideojuego);

            // Ejecutar la consulta
            int filasAfectadas = statement.executeUpdate();

            // Verificar si se ha eliminado correctamente
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si ocurre una excepción, asumimos que la eliminación no fue exitosa
        }
    }
}
