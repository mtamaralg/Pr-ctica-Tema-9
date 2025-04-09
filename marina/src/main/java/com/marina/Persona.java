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

public abstract class Persona {
    private static IntegerProperty id;
    private StringProperty nombre;
    private StringProperty apellido1;
    private StringProperty apellido2;


    public Persona(int id, String nombre, String apellido1, String apellido2) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido1 = new SimpleStringProperty(apellido1);
        this.apellido2 = new SimpleStringProperty(apellido2);

    }

    public void setId(int id) {
        this.id.set(id);
    }  
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    public void setApellido1(String apellido1) {
        this.apellido1.set(apellido1);
    }
    public void setApellido2(String apellido2) {
        this.apellido2.set(apellido2);
    }
    public static int getId() {
        return id.get();
    }
    public String getNombre() {
        return nombre.get();
    }
    public String getApellido1() {
        return apellido1.get();
    }
    public String getApellido2() {
        return apellido2.get();
    }

    public abstract Persona get(int id);
    public abstract void getAll();
    public abstract int save();
    public abstract int delete();
    public abstract void participa(int id_Evento, int id_persona, String fecha);

    public ObservableList<Evento> getEvento(){
        Connection con = conectarBD();
        Evento evento = null;
        ObservableList<Evento> listaEventos = FXCollections.observableArrayList();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PERSONA INNER JOIN PARTICIPA ON PERSONA.id = PARTICIPA.persona_id INNER JOIN EVENTO ON EVENTO.id = PARTICIPA.evento_id WHERE PERSONA.id = " + this.getId()
            );
            if(rs.next()){
                evento = new Evento(rs.getInt("id"), rs.getString("nombre"), rs.getString("description"), rs.getString("lugar"), rs.getString("fecha_inicio"), rs.getString("fecha_fin"), rs.getInt("id_categoria"));
                listaEventos.add(evento);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return listaEventos;
    }
    public static Connection conectarBD(){
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practica9","root","root");
        }catch(Exception e){
            System.out.println(e);
        }
        return con;
    }
    
    public static int getLastId() {
        Connection con = conectarBD();
        int id = 0;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id) FROM PERSONA");
            if (rs.next()) {
                id = rs.getInt(1);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }
}
