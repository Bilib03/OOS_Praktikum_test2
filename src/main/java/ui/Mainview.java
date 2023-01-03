package ui;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionAttributeException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
        MenuItem menuItem1 = new MenuItem("Löschen");
        MenuItem menuItem2 = new MenuItem("Auswählen");
        contextMenu.getItems().addAll(menuItem2, menuItem1);
        listView.setContextMenu(contextMenu);
        menuItem1.setOnAction(this::confirmationDialog);

        menuItem2.setOnAction(event ->{
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("Accountview.fxml"));
                root = loader.load();
                stage = (Stage) menuItem1.getParentPopup().getOwnerWindow();
                Accountview accountview = loader.getController();
                accountview.setAccount(listView.getSelectionModel().getSelectedItem());
                stage.setScene(new Scene(root));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        /*menuItem2.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Accountview.fxml"));
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
        });*/
    }
   public void textInputDialog(javafx.event.ActionEvent event)
           throws AccountAlreadyExistsException, IOException {
       TextInputDialog newAccDialog = new TextInputDialog("Name");
       newAccDialog.setTitle("Account hinzufügen");
       newAccDialog.setHeaderText("Fügen Sie einen neuen Account hinzu");
       newAccDialog.setContentText("Accountname: ");
       Optional<String> result = newAccDialog.showAndWait();
       if (result.isPresent()) {
           String accName = result.get();
           pb1.createAccount(accName);
           listView.getItems().add(accName);
       }
   }

    public void confirmationDialog(javafx.event.ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sind Sie sicher?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                pb1.deleteAccount(listView.getSelectionModel().getSelectedItem());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (AccountDoesNotExistException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
            }
        }
    }

    public String getSelectedAccount() {
        return listView.getSelectionModel().getSelectedItem();
    }
}
