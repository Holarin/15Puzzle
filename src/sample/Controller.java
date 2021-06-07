package sample;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Controller {

    @FXML
    private GridPane battleField;

    static public double time = 0;
    static public boolean inGame = true;
    static private boolean canMove = true;
    @FXML
    private ImageView b1;

    @FXML
    private ImageView b2;

    @FXML
    private ImageView b3;

    @FXML
    private ImageView b4;

    @FXML
    private ImageView b5;

    @FXML
    private ImageView b6;

    @FXML
    private ImageView b7;

    @FXML
    private ImageView b8;

    @FXML
    private ImageView b9;

    @FXML
    private ImageView b10;

    @FXML
    private ImageView b11;

    @FXML
    private ImageView b12;

    @FXML
    private ImageView b13;

    @FXML
    private ImageView b14;

    @FXML
    private ImageView b15;

    @FXML
    private ImageView emptyView;

    List<ImageView> list;

    @FXML
    void initialize() {
        inGame = true;
        time = 0;
        canMove = true;
        setupGoodList();
        setupGrid();
        Timer timer = new Timer();
        timer.start();
    }

    @FXML
    void mouseClickGame(MouseEvent event) {
        if (canMove) {
            Node source = (Node) event.getSource();
            int colEmpty = battleField.getColumnIndex(emptyView);
            int rowEmpty = battleField.getRowIndex(emptyView);
            int colIndex = battleField.getColumnIndex(source);
            int rowIndex = battleField.getRowIndex(source);
            if (colIndex == colEmpty) {
                if ((rowIndex + 1) == rowEmpty) {
                    playAnim(source, emptyView, colEmpty, rowEmpty, colIndex, rowIndex, 2);
                    canMove = false;
                }

                if ((rowIndex - 1) == rowEmpty) {
                    canMove = false;
                    playAnim(source, emptyView, colEmpty, rowEmpty, colIndex, rowIndex, 2);
                }
            }

            if (rowIndex == rowEmpty) {
                if ((colIndex + 1) == colEmpty) {
                    canMove = false;
                    playAnim(source, emptyView, colEmpty, rowEmpty, colIndex, rowIndex, 2);
                }

                if ((colIndex - 1) == colEmpty) {
                    canMove = false;
                    playAnim(source, emptyView, colEmpty, rowEmpty, colIndex, rowIndex, 2);
                }
            }
        }
    }

    void tryWin() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Node node = list.get(i*4+j);
                if (battleField.getRowIndex(node) != i || (battleField.getColumnIndex(node) != j)) {
                    canMove = true;
                    return;
                }
            }
        }
        inGame = false;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Победа!");
        alert.setHeaderText("Результат:");
        alert.setContentText("Time: " + String.format("%.2f", time));
        alert.show();
        Stage stage = (Stage)b1.getScene().getWindow();
        stage.close();
        try {
            List<Double> doubles = getRecords("records.txt");
            doubles.add(time);
            save(doubles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(List<Double> doubles) {
        Set<Double> doubleSet = new HashSet<>(doubles);
        doubles = new ArrayList<>(doubleSet);
        Collections.sort(doubles);
        try(FileWriter writer = new FileWriter("records.txt", false))
        {
            for (Double d : doubles) {
                writer.append(String.format("%.2f", d) + "\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }



    public static List<Double> getRecords(String fileName) throws IOException {
        List<Double> records = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = reader.readLine()) != null) {
            records.add(Double.parseDouble(line.replace(',', '.')));
        }

        return records;
    }


    void playAnim(Node node, Node empty, int colEmpty, int rowEmpty, int colIndex, int rowIndex, int time) {
        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(500), node);
        translateTransition.setFromX(0);
        translateTransition.setToX(empty.getLayoutX() - node.getLayoutX());
        translateTransition.setFromY(0);
        translateTransition.setToY(empty.getLayoutY() - node.getLayoutY());
        translateTransition.setCycleCount(0);
        translateTransition.play();

        TranslateTransition translateTransition2 =
                new TranslateTransition(Duration.millis(500), emptyView);
        translateTransition2.setFromX(0);
        translateTransition2.setToX(node.getLayoutX() - empty.getLayoutX());
        translateTransition2.setFromY(0);
        translateTransition2.setToY(node.getLayoutY() - empty.getLayoutY());
        translateTransition2.setCycleCount(0);
        translateTransition2.play();
        translateTransition2.setOnFinished(event -> {
            emptyView.setTranslateX(0);
            emptyView.setTranslateY(0);
        });
        translateTransition.setOnFinished(event -> {

            battleField.getChildren().remove(emptyView);
            battleField.getChildren().remove(node);
            battleField.add(node, colEmpty, rowEmpty);
            battleField.add(emptyView, colIndex, rowIndex);
            node.setTranslateX(0);
            node.setTranslateY(0);
            tryWin();
        });
    }



    void setupGoodList() {
        list = new ArrayList<>();
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);
        list.add(b7);
        list.add(b8);
        list.add(b9);
        list.add(b10);
        list.add(b11);
        list.add(b12);
        list.add(b13);
        list.add(b14);
        list.add(b15);
        list.add(emptyView);
    }
    void setupGrid() {
        List<ImageView> imageViews = new ArrayList<>(list);
        Collections.shuffle(imageViews);
        battleField.add(imageViews.get(0), 0, 0);
        battleField.add(imageViews.get(1), 1, 0);
        battleField.add(imageViews.get(2), 2, 0);
        battleField.add(imageViews.get(3), 3, 0);
        battleField.add(imageViews.get(4), 0, 1);
        battleField.add(imageViews.get(5), 1, 1);
        battleField.add(imageViews.get(6), 2, 1);
        battleField.add(imageViews.get(7), 3, 1);
        battleField.add(imageViews.get(8), 0, 2);
        battleField.add(imageViews.get(9), 1, 2);
        battleField.add(imageViews.get(10), 2, 2);
        battleField.add(imageViews.get(11), 3, 2);
        battleField.add(imageViews.get(12), 0, 3);
        battleField.add(imageViews.get(13), 1, 3);
        battleField.add(imageViews.get(14), 2, 3);
        battleField.add(imageViews.get(15), 3, 3);
    }
}
