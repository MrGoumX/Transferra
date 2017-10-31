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

        programTextArea.setText("Το Transferra είναι ένα πρόγραμμα μεταφοράς αρχείων μέσω τοπικού δικτύου\n" +
                "(LAN) ή μέσω Internet. Αναπτύχθηκε με σκοπό την απλή, ασφαλή και εύκολη\n" +
                "μεταφορά δεδομένων μεταξύ υπολογιστών. Η παρούσα έκδοση είναι δοκιμαστική\n" +
                "και μπορεί να περιέχει σφάλματα. Οι προγραμματιστές δεν φέρουν καμία ευθύνη\n" +
                "για τυχών δυσλειτουργία προγράμματος.");
        programTextArea.setEditable(false);

        programersTextArea.setText("Η ανάπτυξη της εφαρμογής ήταν έργο των φοιτητών Χρήστου Γκούμπα και\nΚωνσταντίνου Λαμπράκη. Για να αναφέρετε σχόλια, κριτικές, bugs ή οτιδήποτε\nάλλο θεωρείται σκόπιμο, μπορείται να επικοινωνήσετε μαζί μας στα e-mail:" +
                "\nΧρήστος Γκούμας: " +
                "\nΚωνσταντίνος Λαμπράκης: koslamprakis@gmail.com");
        programersTextArea.setEditable(false);

        functionalTextArea.setText("Αποστολή: \n\nΟ αποστολέας θα πρέπει να γνωρίζει και να συμπληρώσει το ID αποστολής\nτου λήπτη." +
                "\n\nΟ λήπτης θα πρέπει να γνωρίζει και να συμπληρώσει το ID πιστοποίησης του\nαποστολέα." +
                "\n\nΟ λήπτης πρέπει να πατήσει πρώτος λήψη ώστε περιμένει τα αρχεία." +
                "\n\nΕφόσον έχει πατήσει λήψη ο λήπτης, ο αποστολέας πρέπει να επιλέξει τα αρχεία" +
                "\nπρος αποστολή και να πατήσει το κουμπί αποστολής.");
        functionalTextArea.setEditable(false);
    }

}
