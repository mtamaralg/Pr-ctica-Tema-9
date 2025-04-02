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

public class categoriaController {
    @FXML
    TableView<Categoria> tablaCategoria;

    @FXML
    private TableColumn<Categoria, Integer> idColumna;

    @FXML
    private TableColumn<Categoria, String> nombreColumna;

    @FXML
    private TableColumn<Categoria, String> descriptionColumna;

    private ObservableList<Categoria> listaCategorias = FXCollections.observableArrayList();

    public void initialize(){
        idColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descriptionColumna.setCellValueFactory(new PropertyValueFactory<>("description"));

        nombreColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumna.setCellFactory(TextFieldTableCell.forTableColumn());

        nombreColumna.setOnEditCommit(event -> {
            Categoria categoria = event.getRowValue();
            categoria.setNombre(event.getNewValue());
            saveRow(categoria);
        });
        descriptionColumna.setOnEditCommit(event -> {
            Categoria categoria = event.getRowValue();
            categoria.setDescription(event.getNewValue());
            saveRow(categoria);
        });
        tablaCategoria.setItems(listaCategorias);
        loadData();
    }

    private void loadData() {
        Categoria.getAll(listaCategorias);
    }
    @FXML
    public void addRow() {
        Categoria categoria = new Categoria(Categoria.getLastId() + 1, "", "");
        listaCategorias.add(categoria);
        tablaCategoria.getSelectionModel().select(categoria);
        tablaCategoria.edit(tablaCategoria.getSelectionModel().getSelectedIndex(), nombreColumna);
        tablaCategoria.edit(tablaCategoria.getSelectionModel().getSelectedIndex(), descriptionColumna);
        categoria.save();
    }
    public void saveRow(Categoria categoria) {
        categoria.save();
    }

    @FXML
    public void deleteRow() {
        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setTitle("Confirmación");
        a.setHeaderText("¿Estás seguro de que quieres borrar este usuario?");
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            Categoria categoria = (Categoria) tablaCategoria.getSelectionModel().getSelectedItem();
            categoria.delete();  
            listaCategorias.remove(categoria);  
        }
    }
    @FXML
    public void primary() throws IOException {
        App.setRoot("primary");}
    
    @FXML
    public void evento() throws IOException {
        App.setRoot("evento");}
}
