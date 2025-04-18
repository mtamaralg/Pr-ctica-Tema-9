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
    private StringProperty correo;


    public Participante(int id, String nombre, String apellido1, String apellido2, String email) {
        super(id, nombre, apellido1, apellido2);
        this.id_Participante = new SimpleIntegerProperty(id);
        this.correo = new SimpleStringProperty(email);
    }


    public void setId_Participante(int id_Participante) {
        this.id_Participante.set(id_Participante);
    }
    
    public void setCorreo(String email) {
        this.correo.set(email);
    }

    public int getId_Participante() {
        return id_Participante.get();
    }

    public String getCorreo() {
        return correo.get();
    }
    @Override
    public Persona get(int id) {
        Connection con = conectarBD();
        Participante participante = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PARTICIPANTE INNER JOIN PERSONA ON PARTICIPANTE.id = PERSONA.ID WHERE id = " + id);
            if (rs.next()) {
                participante = new Participante(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("email"));
            } else {
                participante = new Participante(0, "", "", "", "");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return participante;
    }

    public static void get(String txt, ObservableList<Participante> listaParticipantes){

        Connection con = conectarBD();
        listaParticipantes.clear();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PARTICIPANTE INNER JOIN PERSONA ON PARTICIPANTE.id = PERSONA.id WHERE nombre LIKE '%" + txt + "%' OR apellido1 LIKE '%" + txt + "%' OR apellido2 LIKE '%" + txt + "%' OR email LIKE '%" + txt + "%'");
            while (rs.next()) {
                Participante p = new Participante(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("email"));
                listaParticipantes.add(p);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
    }

    public static void getAll(ObservableList<Participante> listaParticipantes) {
        Connection con = conectarBD();
        Participante participante = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PARTICIPANTE INNER JOIN PERSONA ON PARTICIPANTE.id = PERSONA.id");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("nombre") + " " + rs.getString("apellido1") + " " + rs.getString("apellido2") + " " + rs.getString("email"));
                participante = new Participante(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("email"));
                listaParticipantes.add(participante);
                System.out.println("Participante: " + participante.getNombre() + " " + participante.getApellido1() + " " + participante.getApellido2() + " " + participante.getCorreo());
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
                st.executeUpdate("UPDATE PERSONA SET nombre = '" + this.getNombre() + "' , apellido1 = '" + this.getApellido1() + "', apellido2 = '" + this.getApellido2() + "' WHERE id = " + this.getId()); 
                st.executeUpdate("UPDATE PARTICIPANTE SET email = '" + this.getCorreo() + "' WHERE id = " + this.getId());

                result = 1;
            } else {
                st.execute("INSERT INTO PERSONA (id, nombre, apellido1, apellido2) VALUES (" + this.getId() + ", '" + this.getNombre() + "', '" + this.getApellido1() + "', '" + this.getApellido2() + "')");
                st.executeUpdate("INSERT INTO PARTICIPANTE ( id, email) VALUES (" + this.getId_Participante() + ", '" + this.getCorreo() + "')");

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
            st.executeUpdate("DELETE FROM PARTICIPANTE WHERE id = " + this.getId_Participante());
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practica9","root","root");
        }catch(Exception e){
            System.out.println(e);
        }
        return con;
    }

    public void participa(int id_evento, int id_persona, String fecha) {
        Connection con = conectarBD();
        try {
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO PARTICIPA (id_evento, id_persona, fecha) VALUES (" + this.getId_Participante() + ", " + this.getId() + ", '" + "20/05/2025"+ "')");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public void getAll() {
        // TODO Auto-generated method stub
        
    }


   
    
}
