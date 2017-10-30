package sample;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;

public class Controller {

    @FXML
    private MenuBar menuBar;

    @FXML
    private ImageView image;

    public void initialize() {
        menuBar.getMenus().addAll(createSendMenu(), createReceiveMenu(), createHelpMenu());
        image.setImage(new Image(Controller.class.getResource("tranferraImage.jpg").toString()));
    }

    private Menu createSendMenu(){
        Label sendLabel = new Label("Αποστολή");
        sendLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sendFile(event);
            }
        });
        Menu sendMenu = new Menu();
        sendMenu.setGraphic(sendLabel);
        return sendMenu;
    }

    private Menu createReceiveMenu(){
        Label receiveLabel = new Label("Λήψη");
        receiveLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                receiveFile(event);
            }
        });
        Menu receiveMenu = new Menu();
        receiveMenu.setGraphic(receiveLabel);
        return receiveMenu;
    }

    private Menu createHelpMenu(){
        Label helpLabel = new Label("Βοήθεια");
        helpLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showHelp(event);
            }
        });
        Menu helpMenu = new Menu();
        helpMenu.setGraphic(helpLabel);
        return helpMenu;
    }

    // sendFile display send windows.
    private void sendFile(MouseEvent e) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("sendWindows.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Αποστολή Αρχείων");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // receiveFile display receive windows.
    private void receiveFile(MouseEvent e){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("receiveWindows.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Λήψη Αρχείων");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // showHelp display help windows.
    private void showHelp(MouseEvent e){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("helpWindows.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Βοήθεια");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

