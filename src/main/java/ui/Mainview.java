package ui;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionAttributeException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Mainview extends SuperController implements Initializable {

    private Stage stage;
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    private ListView<String> listView;

    public Mainview() throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            System.out.println(pb1.getAllAccounts());
            List<String> test = pb1.getAllAccounts();
            listView.getItems().addAll(test);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Edit");
        MenuItem menuItem2 = new MenuItem("Delete");
        contextMenu.getItems().addAll(menuItem1, menuItem2);
        listView.setContextMenu(contextMenu);
        menuItem1.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/Accountview.fxml"));
                root = loader.load();
                Accountview accountview = loader.getController();
                accountview.setAccount(listView.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage = (Stage) menuItem1.getParentPopup().getOwnerWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
        menuItem2.setOnAction(event -> {
            try {
                pb1.deleteAccount(listView.getSelectionModel().getSelectedItem());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AccountDoesNotExistException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void textInputDialog(javafx.event.ActionEvent event) throws AccountAlreadyExistsException {
        TextInputDialog dialog = new TextInputDialog("Name");
        dialog.setTitle("Account hinzufügen");
        dialog.setHeaderText("Account hinzufügen");
        dialog.setContentText("Accountname:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                pb1.createAccount(result.get());
                listView.getItems().add(result.get());
            } catch (AccountAlreadyExistsException e) {
                System.out.println("Account already exists");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void confirmationDialog(javafx.event.ActionEvent event) throws AccountDoesNotExistException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sind Sie sicher?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            pb1.deleteAccount(listView.getSelectionModel().getSelectedItem());
            listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
        }
    }

    public String getSelectedAccount() {
        return listView.getSelectionModel().getSelectedItem();
    }
}
