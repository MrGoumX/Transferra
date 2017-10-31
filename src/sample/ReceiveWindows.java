package sample;

import core.Receive;
import core.UtilClass;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
    private ComboBox<String> localIpComboBox;

    @FXML
    private ProgressBar progressBar;


    private static int currentFiles; // currentFiles represent number of files that have received.
    private Thread receiveThread;
    private boolean hasBeforeReceived = false;


    // Public Methods.


    public void initialize(){

        networkComboBox.setItems(FXCollections.observableArrayList("Internet","LAN"));
        networkComboBox.getSelectionModel().selectFirst();


        IDTextField.setEditable(false);
        IDTextField.setText(getIdFromIp(getPublicIp()));

        folderTextField.setText(System.getProperty("user.home") + File.separator + "Downloads");

    }

    // Choose between id for Local IP(LAN) or for public IP(Internet).
    public void netComboAction(ActionEvent e) {

        if(networkComboBox.getValue().equals("LAN")){

            localIpComboBox.setItems(FXCollections.observableArrayList(getLocalIp()));
            if(getLocalIp().get(0).equals("192.168.56.1") && (getLocalIp().size()>1)){
                localIpComboBox.getSelectionModel().select(1);
            }else{
                localIpComboBox.getSelectionModel().selectFirst();
            }
            IDTextField.setText(getIdFromIp(localIpComboBox.getValue()));

        }else{
            IDTextField.setText(getIdFromIp(getPublicIp()));
            localIpComboBox.setItems(FXCollections.observableArrayList());
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

        if(hasBeforeReceived)return;
        if(!UtilClass.isValidIdAuth(authendicationTextField.getText())){
            UtilClass.showErrorAlert("Σφάλμα Λήψης", "Το ID πιστοποίησης μπορεί να περιέχει μέχρι 10 ακέραιους αριθμούς.");
            return;
        }


        Runnable receive = new Receive(folderTextField.getText(), authendicationTextField.getText(), 49900);
        receiveThread= new Thread(receive);
        receiveThread.start();
        bindProgressBar((Receive) receive);
        hasBeforeReceived = true;
    }

    // localIpComboAction
    public void localIpComboAction(ActionEvent e){

    }

    // cancelAction cancels the receive of files.
    public void cancelAction(ActionEvent e){
        if(UtilClass.showConfirmWindows("Ακύρωση Λήψης", "Θέλετε να ακυρώσετε την λήψη αρχείων;")){
            ((Node)(e.getSource())).getScene().getWindow().hide();
            if(receiveThread!=null) receiveThread.interrupt();
        }
    }

    // increaseNoFiles increase the level of progress bar.
    public static void increaseNoFiles(){
        currentFiles += 1;
    }

    // Private Methods.

    // Return public ip.
    private String getPublicIp(){
        try{
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            return in.readLine(); //you get the IP as a String
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
        return null;
    }

    // Return Local IP.
    private List<String> getLocalIp(){
        List<String> addressesList = new ArrayList<>();
        try{
            // for each network interface.
            Enumeration Interfaces = NetworkInterface.getNetworkInterfaces();
            while(Interfaces.hasMoreElements()) {
                NetworkInterface Interface = (NetworkInterface) Interfaces.nextElement();

                //for each address of this interface.
                Enumeration Addresses = Interface.getInetAddresses();
                while (Addresses.hasMoreElements()) {
                    InetAddress Address = (InetAddress) Addresses.nextElement();
                    // if address is IPv4, local and not loopback then add it to list.
                    if (Address.getHostAddress().contains(".") && Address.isSiteLocalAddress() && !Address.isLoopbackAddress()) addressesList.add(Address.getHostAddress());
                }
            }
        }catch (SocketException socketException) {
            socketException.printStackTrace();
        }

        return addressesList;
    }

    // Translate ip to id.
    private String getIdFromIp(String ip){


        /*
        *  remove ".",
        *  add zeros to make address with exactly 12 digits.
        *  reverse address,
        *  add spaces.
        */

        // replace all "." with "" and create pointer to "." as bounds.
        int pointers[] = new int[3];
        for(int i=0; i<pointers.length; i++){
            pointers[i] = ip.indexOf(".");
            ip = ip.replaceFirst("\\p{Punct}", "");
        }

        // create 4 substrings with 4 fields of an IP.
        String ipFields[] = new String[4];
        int bound = 0;
        for(int i=0; i<ipFields.length; i++){
            if(i >= pointers.length){
                ipFields[i] = ip.substring(bound, ip.length());
                break;
            }
            ipFields[i] = ip.substring(bound, pointers[i]);
            bound = pointers[i];
        }

        // fill each substring with 0 to have 3 digits and concat all substrings to one.
        ip = "";
        for(int i=0; i<ipFields.length; i++){
            switch (ipFields[i].length()){
                case 1:
                    ipFields[i] = "00" + ipFields[i];
                    break;
                case 2:
                    ipFields[i] = "0" + ipFields[i];
                    break;
            }
            ip += ipFields[i];
        }

        // reverse id.
        ip = new StringBuilder().append(ip).reverse().toString();

        // add spaces and return id.
        return ip.substring(0,3) + " " + ip.substring(3,6) + " " + ip.substring(6,9) + " " + ip.substring(9,12);

    }

    // bindProgressBar implements progressbar for receive.
    private void bindProgressBar(Receive receive){
        progressBar.setProgress(0);
        final Service ser = new Service<Object>(){
            @Override
            public Task<Object> createTask(){
                return new Task<Object>(){
                    @Override
                    public Object call() throws InterruptedException{
                        while(receive.getNumberOfFiles()==0){
                            Thread.sleep(10);
                        }
                        while(true) {
                            updateProgress( currentFiles, receive.getNumberOfFiles());
                            if(currentFiles == receive.getNumberOfFiles()){
                                updateProgress(currentFiles, receive.getNumberOfFiles());// if remove this sentence, then the progress bar never fill
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

}
