package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;
import java.util.Observable;


public class SendWindows {

    @FXML
    private ComboBox fileComboBox;

    @FXML
    private TextField sendFileTextField;

    @FXML
    private Button fileChooseButton;

    @FXML
    private ComboBox networkComboBox;

    @FXML
    private TextField ipTextField;

    @FXML
    private HBox hbox;

    @FXML
    private ProgressBar progressBar;

    public void initialize() {

        fileComboBox.setItems(FXCollections.observableArrayList("Αρχείου","Φακέλου"));
        fileComboBox.getSelectionModel().selectFirst();

        networkComboBox.setItems(FXCollections.observableArrayList("Internet","LAN"));
        networkComboBox.getSelectionModel().selectFirst();

        sendFileTextField.setEditable(false);

    }

    public void netComboAction(ActionEvent e) {
        if(networkComboBox.getValue().equals("LAN")){
            //initialize hbox with a listview of localHosts
        }
    }

    public void fileComboAction(ActionEvent e) {
        if(fileComboBox.getValue().equals("Αρχείου")){
            fileChooseButton.setText("Επιλογή αρχείου για αποστολή:");
        }else{
            fileChooseButton.setText("Επιλογή φακέλου για αποστολή:");
        }
    }

    public void chooseFile(ActionEvent e) {
        File file = null;
        if(fileComboBox.getValue().equals("Αρχείου")){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Επιλογή Αρχείου προς Αποστολή");
            file = fileChooser.showOpenDialog(null);
        }else{
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Επιλογή Φακέλου προς Αποστολή");
            file = directoryChooser.showDialog(null);
        }
        if (file!=null){
            sendFileTextField.setText(file.getAbsolutePath());
        }

    }

    public void send(ActionEvent e) {

    }
}
