package sample;

import core.Send;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;
import java.util.Random;


public class SendWindows {

    @FXML
    private ComboBox<File> fileComboBox;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField authendicationTextField;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button removeButton;

    public void initialize() {

        // files remove button initialization.
        Image image = new Image(getClass().getResourceAsStream("remove.png"));
        removeButton.setGraphic(new ImageView(image));

        // Authentication Id initialization.
        authendicationTextField.setText((new Random().nextInt(899999) + 100000) + "" );

        bindProgressBar();

    }

    // chooseFile provides a FileChooser for multiple files.
    public void chooseFile(ActionEvent e) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Επιλογή Αρχείου προς Αποστολή");
        List<File> filesList = fileChooser.showOpenMultipleDialog(null);
        fileComboBox.getItems().addAll(filesList);
    }

    // send sends files to server.
    public void send(ActionEvent e) {

        // translate id to ip.
        String ip = getIpFromId(idTextField.getText());

        // start client in new thread.
        Runnable send = new Send(fileComboBox.getItems(), ip, authendicationTextField.getText(),49900);
        Thread sendThread = new Thread(send);
        sendThread.start();

    }

    // remove files from combobox.
    public void removeFileAction(ActionEvent e) {
        fileComboBox.getItems().remove(fileComboBox.getSelectionModel().getSelectedItem());
    }

    // translate ip from id.
    private String getIpFromId(String id){
        String ip = id.replace(" ",""); // remove spaces " ".
        int pointers[] = {Integer.parseInt(""+ip.charAt(0)), Integer.parseInt(""+ip.charAt(1)), Integer.parseInt(ip.substring(ip.length()-2,ip.length())) }; // get pointers.
        ip = ip.substring(2,ip.length()-2); // remove pointers.
        ip =  ip.substring(0,pointers[0]) + "." + ip.substring(pointers[0]+1,pointers[1])+"."+ ip.substring(pointers[1]+1, pointers[2]) + "." + ip.substring(pointers[2]+1, ip.length());
        return ip;
    }

    private void bindProgressBar(){
        progressBar.setProgress(0);
        final Service ser = new Service<Integer>(){
            @Override
            public Task createTask(){
                return new Task<Object>(){
                    @Override
                    public Object call() throws InterruptedException{
                        for(int i=0; i<1000; i++){
                            updateProgress(i, 1000);// max = value that fills bar, i= current value at bar.
                            Thread.sleep(10);// dealey progress in bar
                        }
                        return null;
                    }
                };
            }
        };
        progressBar.progressProperty().bind(ser.progressProperty());// bind and start move.
        ser.restart();// start count and fill bar.
    }
}
