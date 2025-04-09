package com.marina;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.fxml.FXML;

public class artistaController {
    
    @FXML
    TableView<Artista> tablaArtista;

    @FXML
    TextField buscar;

    @FXML
    private TableColumn<Artista, Integer> idArtista;

    @FXML
    private TableColumn<Artista, String> fotografiaArtista;

    @FXML
    private TableColumn<Artista,String> nombreArtista;

    @FXML
    private TableColumn<Artista,String> apellido1Artista;

    @FXML
    private TableColumn<Artista,String> apellido2Artista;

    @FXML
    private TableColumn<Artista,String> obraDestacadaArtista;

    private ObservableList<Artista> listaArtistas = FXCollections.observableArrayList();

    public void initialize(){
        idArtista.setCellValueFactory(new PropertyValueFactory<>("id_Artista"));
        nombreArtista.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellido1Artista.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        apellido2Artista.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        fotografiaArtista.setCellValueFactory(new PropertyValueFactory<>("fotografia"));
        obraDestacadaArtista.setCellValueFactory(new PropertyValueFactory<>("obraDestacada"));

        fotografiaArtista.setCellFactory(TextFieldTableCell.forTableColumn());
        nombreArtista.setCellFactory(TextFieldTableCell.forTableColumn());
        apellido1Artista.setCellFactory(TextFieldTableCell.forTableColumn());
        apellido2Artista.setCellFactory(TextFieldTableCell.forTableColumn());
        obraDestacadaArtista.setCellFactory(TextFieldTableCell.forTableColumn());

        fotografiaArtista.setOnEditCommit(event -> {
            Artista artista = event.getRowValue();
            artista.setFotografia(event.getNewValue());
            saveRow(artista);
        });
        nombreArtista.setOnEditCommit(event -> {
            Artista artista = event.getRowValue();
            artista.setNombre(event.getNewValue());
            saveRow(artista);
        });
        apellido1Artista.setOnEditCommit(event -> {
            Artista artista = event.getRowValue();
            artista.setApellido1(event.getNewValue());
            saveRow(artista);
        });
        apellido2Artista.setOnEditCommit(event -> {
            Artista artista = event.getRowValue();
            artista.setApellido2(event.getNewValue());
            saveRow(artista);
        });
        obraDestacadaArtista.setOnEditCommit(event -> {
            Artista artista = event.getRowValue();
            artista.setObraDestacada(event.getNewValue());
            saveRow(artista);
        });
        loadData();
    }

    private void loadData() {
        Artista.getAll(listaArtistas);
        tablaArtista.setItems(listaArtistas);
    }

    public void addRow() {
        Artista artista = new Artista(Persona.getLastId() + 1, "", "", "", "", "");
        listaArtistas.add(artista);
    }

    public void saveRow(Artista artista) {
        artista.save();
    }

    public void deleteRow() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar este artista?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Artista artista = tablaArtista.getSelectionModel().getSelectedItem();
            listaArtistas.remove(artista);
            artista.delete();
        }
    }

    @FXML
        public void Buscar() throws IOException {
            String busca = buscar.getText();
            listaArtistas.clear();

            if(busca.isEmpty()){
                loadData();
            }else{
                Artista.get(busca,listaArtistas);
            }
        }

    @FXML
    public void primary() throws IOException {
        App.setRoot("primary");}
    
    @FXML
    public void evento() throws IOException {
        App.setRoot("evento");}

    @FXML
    public void participante() throws IOException {
        App.setRoot("participante");}

    @FXML
    public void artista() throws IOException {
        App.setRoot("artista");}

        @FXML
        public void categoria() throws IOException {
            App.setRoot("categoria");}
}
