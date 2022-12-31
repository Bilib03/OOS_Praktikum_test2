package ui;

import bank.*;
import bank.exceptions.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class SuperController {
    String directoryName = "C:\\Users\\Philipp\\IdeaProjects\\praktikum\\src\\main\\java\\bank\\Accounts";
    PrivateBank pb1 = new PrivateBank("pb1",0.1,0.1,directoryName);

    public SuperController() throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
    }

    public void confirmationDialog(javafx.event.ActionEvent event) throws AccountDoesNotExistException, IOException, TransactionDoesNotExistException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sind Sie sicher?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("OK");
        } else {
            System.out.println("Cancel");
        }
    }

    public void textInputDialog(javafx.event.ActionEvent event) throws AccountAlreadyExistsException {
        TextInputDialog dialog = new TextInputDialog("Name");
        dialog.setTitle("Account hinzufügen");
        dialog.setHeaderText("Account hinzufügen");
        dialog.setContentText("Accountname:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println(result.get());
        }
    }
}