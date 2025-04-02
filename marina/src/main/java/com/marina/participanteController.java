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
    private TableColumn<Participante, String> id_PersonaEvento;

    private ObservableList<Participante> listaParticipantes = FXCollections.observableArrayList();

    public void initialize(){
        idParticipante.setCellValueFactory(new PropertyValueFactory<>("id_Participante"));
        emailParticipante.setCellValueFactory(new PropertyValueFactory<>("email"));
        id_PersonaEvento.setCellValueFactory(new PropertyValueFactory<>("id_PersonaEvento"));

        emailParticipante.setCellFactory(TextFieldTableCell.forTableColumn());
        id_PersonaEvento.setCellFactory(TextFieldTableCell.forTableColumn());

        emailParticipante.setOnEditCommit(event -> {
            Participante participante = event.getRowValue();
            participante.setFecha(event.getNewValue());
            saveRow(participante);
        });
        id_PersonaEvento.setOnEditCommit(event -> {
            Participante participante = event.getRowValue();
            participante.setId_Evento(Integer.parseInt(event.getNewValue()));
            saveRow(participante);
        });
        tablaParticipante.setItems(Participante.listaParticipantes);
        loadData();
    }

    private void loadData() {
        Participante.getAll(Participante.listaParticipantes);
    }

    @FXML
    public void addRow() {
        Participante participante = new Participante(Participante.getId() + 1, "", "", "", 0, "");
        listaParticipantes.add(participante);
        tablaParticipante.scrollTo(participante);
        tablaParticipante.edit(tablaParticipante.getItems().size() - 1, emailParticipante);
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
    
