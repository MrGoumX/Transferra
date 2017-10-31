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

        // program tab.
        programTextArea.setText("Το Transferra είναι ένα πρόγραμμα μεταφοράς αρχείων μέσω τοπικού δικτύου\n" +
                "(LAN) ή μέσω Internet. Αναπτύχθηκε με σκοπό την απλή, ασφαλή και εύκολη\n" +
                "μεταφορά δεδομένων μεταξύ υπολογιστών. Η παρούσα έκδοση είναι δοκιμαστική\n" +
                "και μπορεί να περιέχει σφάλματα. Οι προγραμματιστές δεν φέρουν καμία ευθύνη\n" +
                "για τυχών δυσλειτουργία προγράμματος.");
        programTextArea.setEditable(false);

        // programmers tab.
        programersTextArea.setText("Η ανάπτυξη της εφαρμογής ήταν έργο των φοιτητών πληροφορικής ΟΠΑ \nΧρήστου Γκούμπα και Κωνσταντίνου Λαμπράκη. Για να αναφέρετε σχόλια,\nκριτικές, bugs ή οτιδήποτε άλλο θεωρείται σκόπιμο, μπορείται να επικοινω-\nνήσετε μαζί μας στα e-mail:\n" +
                "\nΧρήστος Γκούμας: " +
                "\nΚωνσταντίνος Λαμπράκης: koslamprakis@gmail.com");
        programersTextArea.setEditable(false);

        // functionality tab.
        functionalTextArea.setText("Αποστολή: \n\nΟ αποστολέας θα πρέπει να γνωρίζει και να συμπληρώσει το ID αποστολής\nτου λήπτη." +
                "\nΟ λήπτης θα πρέπει να γνωρίζει και να συμπληρώσει το ID πιστοποίησης του\nαποστολέα." +
                "\nΟ λήπτης πρέπει να πατήσει πρώτος λήψη ώστε περιμένει τα αρχεία." +
                "\nΕφόσον έχει πατήσει λήψη ο λήπτης, ο αποστολέας πρέπει να επιλέξει τα αρχεία" +
                "\nπρος αποστολή και να πατήσει το κουμπί αποστολής.");
        functionalTextArea.setEditable(false);
    }

}
