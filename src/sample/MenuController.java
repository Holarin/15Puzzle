package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MenuController {

    @FXML
    private Button play;

    @FXML
    private Button record;

    @FXML
    void initialize() {
        play.setOnAction(event -> {
            openNewScene("sample.fxml");
        });

        record.setOnAction(event -> {
            try {
                String rec = getRecords("records.txt");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Рекорды");
                alert.setHeaderText("Рекорды за прошлые прохождения:");
                alert.setContentText(rec);
                alert.show();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Результаты не смогли загрузиться:");
                alert.setContentText(null);
                alert.show();
            }
        });
    }

    public static String getRecords(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("sec");
            stringBuilder.append(ls);
        }

        return stringBuilder.toString();
    }

    public void openNewScene(String window) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setResizable(false);
        stage.showAndWait();
    }
}
