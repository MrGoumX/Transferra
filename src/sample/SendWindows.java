package sample;

import core.Send;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Observable;
import java.util.Random;


public class SendWindows {

    @FXML
    private ComboBox fileComboBox;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField authendicationTextField;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button removeButton;

    private Runnable send;
    private Thread sendThread;
    private List<File> filesList;
    private String ip;

    public void initialize() {

        Image image = new Image(getClass().getResourceAsStream("remove.png"));
        removeButton.setGraphic(new ImageView(image));

        authendicationTextField.setText((new Random().nextInt(899999) + 100000) + "" );

    }

    public void chooseFile(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Επιλογή Αρχείου προς Αποστολή");
        filesList= fileChooser.showOpenMultipleDialog(null);
        fileComboBox.getItems().addAll(filesList);
    }

    public void send(ActionEvent e) {
        send = new Send(filesList,getIpFromId(idTextField.getText()),authendicationTextField.getText());
        sendThread = new Thread(send);
        sendThread.start();
    }

    public void removeFileAction(ActionEvent e) {
        fileComboBox.getItems().remove(fileComboBox.getSelectionModel().getSelectedItem());
    }

    private String getIpFromId(String id){
        String ip = id.replace(" ",""); // remove spaces " ".
        int pointers[] = {Integer.parseInt(""+ip.charAt(0)), Integer.parseInt(""+ip.charAt(1)), Integer.parseInt(ip.substring(ip.length()-2,ip.length())) }; // get pointers.
        ip = ip.substring(2,ip.length()-2); // remove pointers.
        ip =  ip.substring(0,pointers[0]) + "." + ip.substring(pointers[0]+1,pointers[1])+"."+ ip.substring(pointers[1]+1, pointers[2]) + "." + ip.substring(pointers[2]+1, ip.length());
        return ip;
    }
}
