package gui;

import core.Receive;
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
import javafx.scene.control.ProgressBar;

import java.io.File;

import core.Send;


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

    private File fileToSend;
    private String ip;
    private Runnable send;
    private Thread sendThread;


    public void initialize() {

        networkComboBox.setItems(FXCollections.observableArrayList("Internet","LAN"));
        networkComboBox.getSelectionModel().selectFirst();

        sendFileTextField.setEditable(false);

    }

    public void netComboAction(ActionEvent e) {
        if(networkComboBox.getValue().equals("LAN")){
            //initialize hbox with a listview of localHosts
        }
    }

    public void chooseFile(ActionEvent e) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Επιλογή Αρχείου προς Αποστολή");
        fileToSend = fileChooser.showOpenDialog(null);
        if (fileToSend!=null){
            sendFileTextField.setText(fileToSend.getAbsolutePath());
        }
    }

    public void send(ActionEvent e) {
        ip = ipTextField.getText();
        System.out.println(ip);
        send = new Send(fileToSend,ip);
        sendThread = new Thread(send);
        sendThread.start();
    }

}
