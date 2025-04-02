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

public class Evento {
    private IntegerProperty id;
    private StringProperty nombre;
    private StringProperty description;
    private StringProperty lugar;
    private StringProperty fecha_inicio;
    private StringProperty fecha_fin;
    private IntegerProperty id_categoria;

    public Evento(int id, String nombre, String description, String lugar,String fecha_inicio, String fecha_fin, int id_categoria) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre); 
        this.description = new SimpleStringProperty(description);
        this.lugar = new SimpleStringProperty(lugar);
        this.fecha_inicio = new SimpleStringProperty(fecha_inicio);
        this.fecha_fin = new SimpleStringProperty(fecha_fin);
        this.id_categoria = new SimpleIntegerProperty(id_categoria);

    }
    public ObservableList<Evento> listaEventos = FXCollections.observableArrayList();
    
    public void setId(int id) {
        this.id.set(id);
    }
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    public void setDescription(String description) {
        this.description.set(description);
    }
    public void setLugar(String lugar) {
        this.lugar.set(lugar);
    }
    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio.set(fecha_inicio);
    }
    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin.set(fecha_fin);
    }
    public void setId_categoria(int id_categoria) {
        this.id_categoria.set(id_categoria);
    }
    public int getId() {
        return id.get();
    }
    public String getNombre() {
        return nombre.get();
    }
    public String getDescription() {
        return description.get();
    }
    public String getLugar() {
        return lugar.get();
    }
    public String getFecha_inicio() {
        return fecha_inicio.get();
    }
    public String getFecha_fin() {
        return fecha_fin.get();
    }
    public int getId_categoria() {
        return id_categoria.get();
    }
    
    public static void getAll(ObservableList<Evento> listaEventos ){
        Connection con = conectarBD();
        listaEventos.clear();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM EVENTO");
            while (rs.next()) {
                Evento e = new Evento(rs.getInt("id"), rs.getString("nombre"), rs.getString("description"), rs.getString("lugar"), rs.getString("fecha_inicio"), rs.getString("fecha_fin"), rs.getInt("id_categoria"));
                listaEventos.add(e);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
    }

    public static Evento get(int id){
        Connection con = conectarBD();
        Evento e = null;
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT *" + 
                                            "FROM EVENTO" + 
                                            "WHERE id = " + id);
            if (rs.next()) {
               e = new Evento(rs.getInt("id"), rs.getString("nombre"), rs.getString("description"), rs.getString("lugar"), rs.getString("fecha_inicio"), rs.getString("fecha_fin"), rs.getInt("id_categoria"));  

            }else{
                e = null;
            }
            con.close();
        } catch (Exception exception) {
            System.out.println("Error de SQL: " + exception.getMessage());
        }
        return e;
    }

    public void get(String txt){
        Connection con = conectarBD();
        listaEventos.clear();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM EVENTO WHERE nombre LIKE '%" + txt + "%' OR descripcion LIKE '%" + txt + "%'" );
            while (rs.next()) {
                Evento e = new Evento(rs.getInt("id"), rs.getString("nombre"), rs.getString("description"), rs.getString("lugar"), rs.getString("fecha_inicio"), rs.getString("fecha_fin"), rs.getInt("id_categoria"));
                listaEventos.add(e);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
    }

    public static int getLastId(){
        Connection con = conectarBD();
        int lastId = 0;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id) FROM EVENTOS");
            if (rs.next()) {
                lastId = rs.getInt(1);
            }else{
                lastId = 0;
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
        return lastId;
    }

    public int save(){
        Connection con = conectarBD();
        int resultado = 0;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM EVENTO WHERE id = " + id.get());
            if(rs.next()){
                st.executeUpdate("UPDATE EVENTO SET nombre = '" + this.getNombre() + "', description = '" + this.getDescription() + "', lugar = '" + this.getLugar() + "', fecha_inicio = '" + this.getFecha_inicio() + "', fecha_fin = '" + this.getFecha_fin() + "', id_categoria = " + this.getId_categoria() + " WHERE id = " + this.getId());
                resultado = 1;
            }else{
                st.executeUpdate("INSERT INTO EVENTO VALUES(" + this.getId() + ", '" + this.getNombre() + "', '" + this.getDescription() + "', '" + this.getLugar() + "', '" + this.getFecha_inicio() + "', '" + this.getFecha_fin() + "', " + this.getId_categoria() + ")");
                resultado = 1;
            }
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
            resultado = 0;
        }
        return resultado;
    }

    public int delete(){
        Connection con = conectarBD();
        int resultado = 0;
        try {
            Statement st = con.createStatement();
            st.executeUpdate("DELETE FROM EVENTO WHERE id = " + this.getId());
            resultado = 1;
            con.close();
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
            resultado = 0;
        }
        return resultado;
    }

    public Categoria getCategoria(){
        Categoria c = Categoria.get(this.getId_categoria());
        return c;
       /*
        Connection con = conectarBD();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CATEGORIA WHERE id = " + this.getId_categoria());
            if (rs.next()) {
                Categoria c = new Categoria()
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
            */
    }

    public static Connection conectarBD(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practica","root","root");
        }catch(Exception e){
            System.out.println(e);
        }
        return con;
    }

}
