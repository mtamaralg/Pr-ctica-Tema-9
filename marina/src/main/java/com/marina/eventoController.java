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

public class eventoController {
    @FXML
    TableView<Evento> tablaEvento;

    @FXML
    private TableColumn<Evento, Integer> idEventoColumna;

    @FXML
    private TableColumn<Evento, String> nombreEvento;

    @FXML
    private TableColumn<Evento, String> descriptionEvento;

    @FXML
    private TableColumn<Evento, String> lugarEvento;

    @FXML
    private TableColumn<Evento, String> fecha_inicioEvento;

    @FXML
    private TableColumn<Evento, String> fecha_finEvento;

    @FXML
    private TableColumn<Evento, Integer> id_categoriaEventoColumna;

    private ObservableList<Evento> listaEventos = FXCollections.observableArrayList();

    public void initialize(){
        idEventoColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreEvento.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descriptionEvento.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        lugarEvento.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        fecha_inicioEvento.setCellValueFactory(new PropertyValueFactory<>("fecha_inicio"));
        fecha_finEvento.setCellValueFactory(new PropertyValueFactory<>("fecha_fin"));
        id_categoriaEventoColumna.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));

        nombreEvento.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionEvento.setCellFactory(TextFieldTableCell.forTableColumn());
        lugarEvento.setCellFactory(TextFieldTableCell.forTableColumn());
        fecha_inicioEvento.setCellFactory(TextFieldTableCell.forTableColumn());
        fecha_finEvento.setCellFactory(TextFieldTableCell.forTableColumn());
        id_categoriaEventoColumna.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));

        nombreEvento.setOnEditCommit(event -> {
            Evento evento = event.getRowValue();
            evento.setNombre(event.getNewValue());
            saveRow(evento);
        });
        descriptionEvento.setOnEditCommit(event -> {
            Evento evento = event.getRowValue();
            evento.setDescription(event.getNewValue());
            saveRow(evento);
        });
        lugarEvento.setOnEditCommit(event -> {
            Evento evento = event.getRowValue();
            evento.setLugar(event.getNewValue());
            saveRow(evento);
        });
        fecha_inicioEvento.setOnEditCommit(event -> {
            Evento evento = event.getRowValue();
            evento.setFecha_inicio(event.getNewValue());
            saveRow(evento);
        });
        fecha_finEvento.setOnEditCommit(event -> {
            Evento evento = event.getRowValue();
            evento.setFecha_fin(event.getNewValue());
            saveRow(evento);
        });
        id_categoriaEventoColumna.setOnEditCommit(event -> {
            Evento evento = event.getRowValue();
            evento.setId_categoria(event.getNewValue());
            saveRow(evento);
        });
        tablaEvento.setItems(listaEventos);
        loadData();}

        public void loadData() {
            Evento.getAll(listaEventos);
        }

        @FXML
        public void addRow() {
            Evento evento = new Evento(Evento.getLastId() + 1, "", "", "", "", "", 1);
            listaEventos.add(evento);
            tablaEvento.getSelectionModel().select(evento);
            tablaEvento.edit(tablaEvento.getSelectionModel().getSelectedIndex(), nombreEvento);
            tablaEvento.edit(tablaEvento.getSelectionModel().getSelectedIndex(), descriptionEvento);
            tablaEvento.edit(tablaEvento.getSelectionModel().getSelectedIndex(), lugarEvento);
            tablaEvento.edit(tablaEvento.getSelectionModel().getSelectedIndex(), fecha_inicioEvento);
            tablaEvento.edit(tablaEvento.getSelectionModel().getSelectedIndex(), fecha_finEvento);
            tablaEvento.edit(tablaEvento.getSelectionModel().getSelectedIndex(), id_categoriaEventoColumna);
            evento.save();
        }
        public void saveRow(Evento evento) {
            evento.save();
        }
        @FXML
        public void deleteRow() {
            Evento evento = tablaEvento.getSelectionModel().getSelectedItem();
            if (evento != null) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmación de eliminación");
                alert.setHeaderText("¿Está seguro de que desea eliminar este evento?");
                alert.setContentText("Esta acción no se puede deshacer.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    listaEventos.remove(evento);
                    evento.delete();
                }
            }
        }
        @FXML
        public void Principal() throws IOException {
            App.setRoot("primary");
        }
        @FXML
        public void Categoria() throws IOException {
            App.setRoot("categoria");}
        @FXML
        public void Participante() throws IOException {
            App.setRoot("participante");}
        @FXML
        public void Evento() throws IOException {
            App.setRoot("evento");}
}
