package sample;

import core.Receive;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;

public class ReceiveWindows {

    @FXML
    private TextField folderTextField;

    @FXML
    private TextField IDTextField;

    @FXML
    private TextField authendicationTextField;

    @FXML
    private ComboBox<String> networkComboBox;

    @FXML
    private ProgressBar progressBar;

    private static long receivedsize;

    public void initialize(){

        networkComboBox.setItems(FXCollections.observableArrayList("Internet","LAN"));
        networkComboBox.getSelectionModel().selectFirst();

        IDTextField.setEditable(false);
        IDTextField.setText(getIdFromIp(getPublicIp()));


    }

    // Choose between id for Local IP(LAN) or for public IP(Internet).
    public void netComboAction(ActionEvent e) {
        if(networkComboBox.getValue().equals("LAN")){
            IDTextField.setText(getIdFromIp(getLocalIp()));

        }else{
            IDTextField.setText(getIdFromIp(getPublicIp()));
        }
    }

    // Choose directory where the files will store.
    public void chooseDirAction(ActionEvent e){

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Επιλογή Θέση για Αποθήκευση Αρχείων");
        File file = directoryChooser.showDialog(null);
        if(file != null) folderTextField.setText(file.getAbsolutePath());
    }

    // Setup server and receive files at new thread.
    public void receive(ActionEvent e){
        Runnable receive = new Receive(folderTextField.getText(), authendicationTextField.getText(), 49900);
        Thread receiveThread = new Thread(receive);
        receiveThread.start();
        //bindProgressBar();
    }

    // Return public ip.
    private String getPublicIp(){
        try{
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine(); //you get the IP as a String
            return ip;
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
        return null;
    }

    // Return Local IP.
    private String getLocalIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        }catch(UnknownHostException unknownHostException){
            unknownHostException.printStackTrace();
        }
        return null;
    }

    // Translate ip to id.
    private String getIdFromIp(String ip){

        Random random = new Random();

        /*
        *  Replace all . with randoms digits.
        *  Keep at prefix pointer to first two random digits.
        *  Keep at suffix pointer to third random digit.
        */

        String prefix = "" + ip.indexOf(".");
        ip = ip.replaceFirst("\\p{Punct}", ""+random.nextInt(10));

        prefix += ip.indexOf(".");
        ip = ip.replaceFirst("\\p{Punct}", ""+random.nextInt(10));

        String suffix = "" + ((ip.indexOf(".") > 9)? ip.indexOf(".") : "0" + ip.indexOf("."));
        ip =  ip.replaceFirst("\\p{Punct}", ""+random.nextInt(10));

        // Create ID.
        String id = prefix + ip + suffix;

        // Add space " " between each two digits.
        String formatedID = "";
        int i;
        for(i=0; i <= id.length(); i+=3){
            if(i==id.length()){
                // remember pointers start at 0.
                break;
            }else if ((i+1) == id.length()){
                formatedID += id.charAt(i);
                break;
            }else if((i+2)==id.length()){
                formatedID += id.substring(i, i+2);
                break;
            }
            else{
                formatedID += id.substring(i, i+3) + " ";
            }
        }
        return formatedID;
    }

    public void bindProgressBar(){
        progressBar.setProgress(0);
        final Service ser = new Service<Long>(){
            @Override
            public Task createTask(){
                return new Task<Object>(){
                    @Override
                    public Object call() throws InterruptedException{
                        while(true){
                            updateProgress(receivedsize, core.Receive.getSize());
                            Thread.sleep(10);
                            System.out.println(receivedsize);
                            if(core.Receive.getSize()==receivedsize){
                                break;
                            }
                        }
                        return null;
                    }
                };
            }
        };
        progressBar.progressProperty().bind(ser.progressProperty());// bind and start move.
        ser.restart();// start count and fill bar.
    }

    public static void increase(){
        receivedsize+=1024*1024;
    }
}
