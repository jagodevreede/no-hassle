package io.github.jagodevreede.demo.nohassle;

import io.github.jagodevreede.demo.nohassle.image.MemeImage;
import io.github.jagodevreede.demo.nohassle.image.MemeImageRepository;
import io.github.jagodevreede.demo.nohassle.text.MemeText;
import io.github.jagodevreede.demo.nohassle.text.MemeTextRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class MainController {

    private final MemeImageRepository memeImageRepository = new MemeImageRepository();
    private final MemeTextRepository memeTextRepository = new MemeTextRepository();

    @FXML
    TextField upperTextField;
    @FXML
    TextField lowerTextField;
    @FXML
    ComboBox<String> imageIdComboBox;
    @FXML
    ImageView memeImage;
    @FXML
    TableView<MemeText> memeTable;
    @FXML
    TableColumn<MemeText, String> idColumn;
    @FXML
    TableColumn<MemeText, String> upperTextColumn;
    @FXML
    TableColumn<MemeText, String> lowerTextColumn;
    @FXML
    TableColumn<MemeText, String> imageIdColumn;

    @FXML
    public void initialize() {
        imageIdComboBox.getItems().addAll(memeImageRepository.findAll().stream().map(MemeImage::id).toList());
        // Initialize the table columns
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().id().toString()));
        upperTextColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().upperText()));
        lowerTextColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().lowerText()));
        imageIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().imageId()));

        updateTable();
    }

    private void updateTable() {
        memeTable.setItems(FXCollections.observableArrayList(
                memeTextRepository.findAll()
        ));
    }

    public void handleCreateMeme(ActionEvent actionEvent) {
    }

    private static Image toFXImage(BufferedImage bufferedImage) {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public static MainController start(Stage primaryStage) {
        try {
            URL splashFxml = MainController.class.getClassLoader().getResource("main.fxml");
            FXMLLoader loader = new FXMLLoader(splashFxml);
            Parent loaded = loader.load();
            primaryStage.setScene(new Scene(loaded));
            primaryStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
