package sample;

import core.Send;
import core.UtilClass;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    private static long currentBytes; // currentBytes represent bytes, witch have sending.
    private Thread sendThread;

    // Public Methods.

    public void initialize() {

        // files remove button initialization.
        Image image = new Image(getClass().getResourceAsStream("remove.png"));
        removeButton.setGraphic(new ImageView(image));

        // Authentication Id initialization.
        authendicationTextField.setText((new Random().nextInt(899999) + 100000) + "" );

    }

    // chooseFile provides a FileChooser for multiple files.
    public void chooseFile(ActionEvent e) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Επιλογή Αρχείου προς Αποστολή");
        List<File> filesList = fileChooser.showOpenMultipleDialog(null);
        if(filesList!=null)fileComboBox.getItems().addAll(filesList);
    }

    // send sends files to server.
    public void send(ActionEvent e) {

        if(progressBar.getProgress()>0)return;
        if(!UtilClass.isValidIdAuth(authendicationTextField.getText())){
            UtilClass.showErrorAlert("Σφάλμα Αποστολής", "Το ID πιστοποίησης μπορεί να περιέχει μέχρι 10 ακέραιους αριθμούς.");
            return;
        }

        if(!UtilClass.isValidIpId(idTextField.getText())){
            UtilClass.showErrorAlert("Σφάλμα Αποστολής", "Το ID αποστολής βρίσκεται σε λάθος μορφή.");
            return;
        }

        if(fileComboBox.getItems().isEmpty()){
            UtilClass.showErrorAlert("Σφάλμα Αποστολής", "Δεν βρέθηκαν αρχεία προς αποστολή. Παρακαλώ επιλέξτε αρχεία.");
            return;
        }

        // translate id to ip.
        String ip = getIpFromId(idTextField.getText());

        // start client in new thread.
        Runnable send = new Send(fileComboBox.getItems(), ip, authendicationTextField.getText(),49900);
        sendThread = new Thread(send);
        sendThread.start();
        bindProgressBar();
    }

    // remove files from combobox.
    public void removeFileAction(ActionEvent e) {
        fileComboBox.getItems().remove(fileComboBox.getSelectionModel().getSelectedItem());
    }

    // cancelAction cancels the sending of files.
    public void cancelAction(ActionEvent e){
        if(UtilClass.showConfirmWindows("Ακύρωση Αποστολής", "Θέλετε να ακυρώσετε την αποστολή αρχείων;")){
            ((Node)(e.getSource())).getScene().getWindow().hide();
            if(sendThread!=null) sendThread.interrupt();
        }
    }

    // increaseBytes increase the level of progressbar.
    public static  void increaseBytes(){
        currentBytes += 1024*1024;
    }

    // Private Methods.

    // translate ip from id.
    private String getIpFromId(String id){
        id = id.replace(" ",""); // remove spaces " ".
        id = new StringBuilder().append(id).reverse().toString();
        String ip = "";
        for(int i=0; i<id.length(); i+=3){
            ip += id.substring(i, i+3).replaceFirst("^0+(?!$)", "") + ".";
        }

        return ip.substring(0,ip.length()-1);
    }

    // bindProgressBar implements progressbar for send.
    private void bindProgressBar(){

        progressBar.setProgress(0);
        final Service ser = new Service<Object>(){
            @Override
            public Task<Object> createTask(){
                return new Task<Object>(){
                    @Override
                    public Object call() throws InterruptedException{
                        while(true){
                            updateProgress(currentBytes, getTotalSize(fileComboBox.getItems()));
                            if(currentBytes >= getTotalSize(fileComboBox.getItems()))break;
                        }
                        return null;
                    }
                };
            }
        };

        progressBar.progressProperty().bind(ser.progressProperty());// bind and start move.
        ser.restart();// start count and fill bar.
    }

    // getTotalSize returns total size of bytes for all files.
    private long getTotalSize(List<File> files){
        long size = 0;
        for(File file: files){
            size += file.length();
        }
        return size;
    }
}
