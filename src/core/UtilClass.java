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

    //
    public static boolean isValidIdAuth(String id){
        return id.matches("\\d{1,10}");
    }

    //
    public static boolean isValidIpId(String ip){
        return ip.matches("(\\d{3}\\s){3}\\d{3}");
    }

    // showErrorAlert shows an Error Alert windows.
    public static void showErrorAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
