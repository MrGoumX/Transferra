package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.Observable;

public class SendWindows {

    @FXML
    private TextField sendFileTextField;

    @FXML
    private ComboBox networkComboBox;

    @FXML
    private TextField ipTextField;

    @FXML
    private HBox hbox;

    @FXML
    private ProgressBar progressBar;

    public void initialize() {
        networkComboBox.setItems(FXCollections.observableArrayList("Internet","LAN"));
        networkComboBox.getSelectionModel().selectFirst();
        /*
        networkComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue value, String t, String t1) {
            }
        });*/

        sendFileTextField.setEditable(false);
    }

    public void chooseFile(ActionEvent e) {

    }

    public void send(ActionEvent e) {}
}
