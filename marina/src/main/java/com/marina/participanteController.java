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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.fxml.FXML;

public class participanteController {

    @FXML
    TableView<Participante> tablaParticipante;

    @FXML
    private TableColumn<Participante, Integer> idParticipante;

    @FXML
    private TableColumn<Participante, String> emailParticipante;

    @FXML
    private TableColumn<Participante,String> nombreParticipante;

    @FXML
    private TableColumn<Participante,String> apellido1Participante;

    @FXML
    private TableColumn<Participante,String> apellido2Participante;

   

    private ObservableList<Participante> listaParticipantes = FXCollections.observableArrayList();

    public void initialize(){
        idParticipante.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreParticipante.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellido1Participante.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        apellido2Participante.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        emailParticipante.setCellValueFactory(new PropertyValueFactory<>("correo"));

       // emailParticipante.setCellFactory(TextFieldTableCell.forTableColumn());
        nombreParticipante.setCellFactory(TextFieldTableCell.forTableColumn());
        apellido1Participante.setCellFactory(TextFieldTableCell.forTableColumn());
        apellido2Participante.setCellFactory(TextFieldTableCell.forTableColumn());


        // emailParticipante.setOnEditCommit(event -> {
        //     Participante participante = event.getRowValue();
        //     participante.setemail(event.getNewValue());
        //     saveRow(participante);
        // });
        nombreParticipante.setOnEditCommit(event -> {
            Participante participante = event.getRowValue();
            participante.setNombre(event.getNewValue());
            saveRow(participante);
        });
        apellido1Participante.setOnEditCommit(event -> {
            Participante participante = event.getRowValue();
            participante.setApellido1(event.getNewValue());
            saveRow(participante);
        });
        apellido2Participante.setOnEditCommit(event -> {
            Participante participante = event.getRowValue();
            participante.setApellido2(event.getNewValue());
            saveRow(participante);
        });
       
        tablaParticipante.setItems(listaParticipantes);
        loadData();
        
    }

    private void loadData() {
        Participante.getAll(listaParticipantes);
    }

    @FXML
    public void addRow() {
        Participante participante = new Participante(Participante.getId() + 1, "", "", "", "");
        listaParticipantes.add(participante);
        
    }

    public void saveRow(Participante participante) {
        participante.save();
    }

    @FXML
    public void deleteRow() {
        Participante participante = tablaParticipante.getSelectionModel().getSelectedItem();
        if (participante != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "¿Está seguro de que desea eliminar este participante?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                listaParticipantes.remove(participante);
                participante.delete();
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Seleccione un participante para eliminar.");
            alert.showAndWait();
        }
    }

    @FXML
    public void evento() throws IOException {
        App.setRoot("evento");
    }
    @FXML
    public void categoria() throws IOException {
        App.setRoot("categoria");
    }
    @FXML
    public void primary() throws IOException {
        App.setRoot("primary");
    }

}   
    
