package core;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class UtilClass {

    // showConfirmWindows shows an Confirmation(yes-no button) Alert windows.
    public static boolean showConfirmWindows(String title, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType yesButton = new ButtonType("Ναί");
        ButtonType noButton = new ButtonType("Όχι");
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton){
            return true;
        }
        return false;
    }
}
