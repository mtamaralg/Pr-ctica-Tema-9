package com.marina;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Categoria {
    private IntegerProperty id;
    private StringProperty nombre;
    private StringProperty descripcion;

    public Categoria(int id, String nombre, String descripcion) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public void setId(int id) {
        this.id.set(id);
    }
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    public void setDescription(String descripcion) {
        this.descripcion.set(descripcion);
    }
    public int getId() {
        return id.get();
    }
    public String getNombre() {
        return nombre.get();
    }
    public String getDescription() {
        return descripcion.get();
    }

    

    public static Categoria get(int id){
        Connection con = conectarBD();
        Categoria categoria = null;
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CATEGORIA WHERE id = " + id);
            if(rs.next()){
                categoria = new Categoria(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"));
            }else{
                categoria = new Categoria(0, "", "");
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        } 
        return categoria;
    }

    public static void get(String txt, ObservableList<Categoria> listaCategorias){

        Connection con = conectarBD();
        listaCategorias.clear();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CATEGORIA WHERE nombre LIKE '%" + txt + "%' OR descripcion LIKE '%" + txt + "%'" );
            while (rs.next()) {
                Categoria c = new Categoria(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"));
                listaCategorias.add(c);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
    }

    public static void getAll(ObservableList<Categoria> listaCategorias){
        Connection con = conectarBD();
        listaCategorias.clear();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CATEGORIA");
            while (rs.next()) {
                Categoria c = new Categoria(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"));
                listaCategorias.add(c);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static int getLastId(){
        Connection con = conectarBD();
        int LastId = 0;
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) as id FROM CATEGORIA");
            if(rs.next()){
                LastId = rs.getInt(1);
            }else{
                LastId = 0;
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return LastId;
    }

    public int save(){
        Connection con = conectarBD();
        int result = 0;
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CATEGORIA WHERE id = " + id.get());
            if(rs.next()){
                st.executeUpdate("UPDATE CATEGORIA SET nombre = '" + this.getNombre() + "', descripcion = '" + this.getDescription() + "' WHERE id = " + this.getId());
                result = 1;
            }else{
                st.executeUpdate("INSERT INTO CATEGORIA (id, nombre, descripcion) VALUES (" + this.getId()+ ", '" + this.getNombre() + "', '" + this.getDescription() + "')");
                result = 1;
            }
            con.close();
        }catch(Exception e){
            System.out.println("Error de SQL: " + e.getMessage());
            result = 0;
        }
        return result;
    }

    public int delete(){
        Connection con = conectarBD();
        int result = 0;
        try{
            Statement st = con.createStatement();
            st.executeUpdate("DELETE FROM CATEGORIA WHERE id = " + this.getId());
            result = 1;
            con.close();
        }catch(Exception e){
            System.out.println("Error de SQL: " + e.getMessage());
            result = 0;
        }
        return result;
    }

    public static Connection conectarBD(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practica9","root","root");
        }catch(Exception e){
            System.out.println(e);
        }
        return con;
    }
}
