package sample;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelpWindows {

    @FXML
    private ImageView logoImage;

    @FXML
    private TextArea programTextArea;

    @FXML
    private TextArea programersTextArea;

    @FXML
    private TextArea functionalTextArea;

    public void initialize(){

        logoImage.setImage(new Image(Controller.class.getResource("logoImage.jpg").toString()));

        programTextArea.setText("Το Transferra είναι ένα πρόγραμμα μεταφοράς αρχείων μέσω τοπικού δικτύου \n" +
                "(LAN) ή μέσω Internet. Αναπτύχθηκε με σκοπό την απλή, ασφαλή και εύκολη \n" +
                "μεταφορά δεδομένων μεταξύ υπολογιστών. Η παρούσα έκδοση είναι δοκιμαστική \n" +
                "και μπορεί να περιέχει σφάλματα. Οι προγραμματιστές δεν φέρουν καμία ευθύνη \n" +
                "για τυχών δυσλειτουργία προγράμματος.");

        programersTextArea.setText("");

        functionalTextArea.setText("");
    }

}
