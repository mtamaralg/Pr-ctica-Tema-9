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


public class Artista extends Persona {
    private IntegerProperty id_Artista;
    private StringProperty fotografia;
    private StringProperty obraDestacada;

    public Artista(int id, String nombre, String apellido1, String apellido2, String fotografia, String obraDestacada) {
        super(id, nombre, apellido1, apellido2);
        this.id_Artista = new SimpleIntegerProperty(id);
        this.fotografia = new SimpleStringProperty(fotografia);
        this.obraDestacada = new SimpleStringProperty(obraDestacada);
    }

    public void setId_Artista(int id_Artista) {
        this.id_Artista.set(id_Artista);
    }
    public void setFotografia(String fotografia) {
        this.fotografia.set(fotografia);
    }
    public void setObraDestacada(String obraDestacada) {
        this.obraDestacada.set(obraDestacada);
    }
    public int getId_Artista() {
        return id_Artista.get();
    }
    public String getFotografia() {
        return fotografia.get();
    }
    public String getObraDestacada() {
        return obraDestacada.get();
    }
    @Override
    public Persona get(int id) {
        Connection con = conectarBD();
        Artista artista = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ARTISTA INNER JOIN PERSONA ON ARTISTA.id = PERSONA.ID WHERE id = " + id);
            if (rs.next()) {
                artista = new Artista(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("fotografia"), rs.getString("obraDestacada"));
            } else {
                artista = new Artista(0, "", "", "", "", "");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return artista;
    }
    public static void getAll(ObservableList<Artista> listaArtistas) {
        Connection con = conectarBD();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ARTISTA INNER JOIN PERSONA ON ARTISTA.id = PERSONA.ID");
            while (rs.next()) {
                Artista artista = new Artista(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("fotografia"), rs.getString("obraDestacada"));
                listaArtistas.add(artista);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void get(String txt, ObservableList<Artista> listaArtistas){

        Connection con = conectarBD();
        listaArtistas.clear();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ARTISTA INNER JOIN PERSONA ON ARTISTA.id = PERSONA.id WHERE nombre LIKE '%" + txt + "%' OR apellido1 LIKE '%" + txt + "%' OR apellido2 LIKE '%" + txt + "%' OR fotografia LIKE '%" + txt + "%' OR obraDestacada LIKE '%" + txt + "%'");
            while (rs.next()) {
                Artista a = new Artista(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("fotografia"), rs.getString("obraDestacada"));
                listaArtistas.add(a);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
    }

    @Override
    public int save() {
        Connection con = conectarBD();
        int result = 0;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ARTISTA WHERE id = " + this.getId());
            if (rs.next()) {
                st.executeUpdate("UPDATE PERSONA SET nombre = '" + this.getNombre() + "' , apellido1 = '" + this.getApellido1() + "', apellido2 = '" + this.getApellido2() + "' WHERE id = " + this.getId()); 
                st.executeUpdate("UPDATE ARTISTA SET fotografia = '" + this.getFotografia() + "' , obraDestacada = '" + this.getObraDestacada() + "' WHERE id = " + this.getId_Artista());
                result = 1;
            } else {
                st.execute("INSERT INTO PERSONA (id, nombre, apellido1, apellido2) VALUES (" + this.getId() + ", '" + this.getNombre() + "', '" + this.getApellido1() + "', '" + this.getApellido2() + "')");
                st.execute("INSERT INTO ARTISTA (id, fotografia, obraDestacada) VALUES (" + this.getId_Artista() + ", '" + this.getFotografia() + "', '" + this.getObraDestacada() + "')");
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
        try {
            Statement st = con.createStatement();
            String sql = "DELETE FROM ARTISTA WHERE id = " + getId_Artista();
            st.executeUpdate(sql);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
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
            st.executeUpdate("INSERT INTO PARTICIPA (id_evento, id_persona, fecha) VALUES (" + this.getId_Artista() + ", " + this.getId() + ", '" + "20/05/2025"+ "')");
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
