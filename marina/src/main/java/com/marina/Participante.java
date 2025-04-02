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

public class Participante extends Persona {
    private IntegerProperty id_Participante;
    private StringProperty email;


    public Participante(int id, String nombre, String apellido1, String apellido2, int id_Participante, String email) {
        super(id, nombre, apellido1, apellido2);
        this.id_Participante = new SimpleIntegerProperty(id_Participante);
        this.email = new SimpleStringProperty(email);
    }


    public void setId_Evento(int id_Participante) {
        this.id_Participante.set(id_Participante);
    }
    
    public void setFecha(String email) {
        this.email.set(email);
    }

    public int getId_Evento() {
        return id_Participante.get();
    }

    public String getFecha() {
        return email.get();
    }
    static ObservableList<Participante> listaParticipantes = FXCollections.observableArrayList();
    @Override
    public Persona get(int id) {
        Connection con = conectarBD();
        Participante participante = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PARTICIPANTE WHERE id = " + id);
            if (rs.next()) {
                participante = new Participante(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("id_Participante"), rs.getString("email"));
            } else {
                participante = new Participante(0, "", "", "", 0, "");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return participante;
    }

    public static void getAll(ObservableList<Participante> listaParticipantes) {
        Connection con = conectarBD();
        Participante participante = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PARTICIPANTE");
            while (rs.next()) {
                participante = new Participante(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("id_Participante"), rs.getString("email"));
                listaParticipantes.add(participante);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int save() {
        Connection con = conectarBD();
        int result = 0;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PARTICIPANTE WHERE id = " + this.getId());
            if (rs.next()) {
                st.executeUpdate("UPDATE PARTICIPANTE SET nombre = '" + this.getNombre() + "', apellido1 = '" + this.getApellido1() + "', apellido2 = '" + this.getApellido2() + "', id_Participante = " + this.getId_Evento() + ", email = '" + this.getFecha() + "' WHERE id = " + this.getId());
                result = 1;
            } else {
                st.executeUpdate("INSERT INTO PARTICIPANTE (nombre, apellido1, apellido2, id_Participante, email) VALUES ('" + this.getNombre() + "', '" + this.getApellido1() + "', '" + this.getApellido2() + "', " + this.getId_Evento() + ", '" + this.getFecha() + "')");
                result = 1;
            }
            con.close();            
        } catch (Exception e) {
            System.out.println(e);
            result = 0;
        }
        return result;
    }

    @Override
    public int delete() {
        Connection con = conectarBD();
        int result = 0;
        try {
            Statement st = con.createStatement();
            st.executeUpdate("DELETE FROM PARTICIPANTE WHERE id = " + this.getId());
            result = 1;
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            result = 0;
        }
        return result;
    }

    
     public static Connection conectarBD(){
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practica","root","root");
        }catch(Exception e){
            System.out.println(e);
        }
        return con;
    }

    public void participa(int id_Evento, int id_Participante, String fecha) {
        Connection con = conectarBD();
        try {
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO PARTICIPA (id_Evento, id_Participante, fecha) VALUES (" + this.getId_Evento() + ", " + this.getId() + ", '" + this.getFecha() + "')");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public void getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }


   
    
}
