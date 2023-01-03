package ui;

import bank.*;
import bank.exceptions.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class Accountview extends SuperController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // use any account to prevent null pointer exception
    private String account = "account1";

    @FXML
    private Label kontostand;

    @FXML
    private Label accountName;

    @FXML
    private ListView<Transaction> transactionList;

    public Accountview() throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(pb1.getAllAccounts());
        if(pb1.getTransactions(account)!=null) {
            transactionList.getItems().addAll(pb1.getTransactions(account));
        }
        ContextMenu contextMenu=new ContextMenu();
        MenuItem MenuItemLöschen=new MenuItem("Löschen");
        contextMenu.getItems().addAll(MenuItemLöschen);
        transactionList.setContextMenu(contextMenu);
        MenuItemLöschen.setOnAction(this::confirmationDialog);
    }

    public void switchToMainview(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Mainview.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setAccount(String temp_account){
        account=temp_account;
        accountName.setText(account);
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactions(account));
        kontostand.setText("Kontostand: " + pb1.getAccountBalance(account));
    }

    public void sortAsc(javafx.event.ActionEvent event) {
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactionsSorted(account,true));
    }
    public void sortDesc(javafx.event.ActionEvent event) {
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactionsSorted(account,false));
    }
    public void sortTypePos(javafx.event.ActionEvent event) {
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactionsByType(account,true));
    }
    public void sortTypeNeg(javafx.event.ActionEvent event) {
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactionsByType(account,false));
    }
    public void update(){
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactions(account));
        kontostand.setText("Kontostand: " + pb1.getAccountBalance(account));
    }

    public void confirmationDialog(javafx.event.ActionEvent event){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sind Sie sicher?");
        Optional <ButtonType> result=alert.showAndWait();
        if(result.get() == ButtonType.OK){
            try {
                pb1.removeTransaction(account, transactionList.getSelectionModel().getSelectedItem());
                transactionList.getItems().remove(transactionList.getSelectionModel().getSelectedItem());
            }catch(AccountDoesNotExistException e){
                throw new RuntimeException(e);
            }catch(IOException e) {
            }catch(TransactionDoesNotExistException e) {
             throw new RuntimeException(e);
            }
        }
        update();
    }

    public void transactionAnlegen(javafx.event.ActionEvent event) {
        ChoiceDialog dialog = new ChoiceDialog("Payment", "Payment", "Transfer");
        dialog.setTitle("Transaktion Hinzufügen");
        dialog.setHeaderText("Transaktion Hinzufügen");
        dialog.setContentText("Transactionstyp: ");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().equals("Payment")) {
                Dialog<Transaction> dialogInfos = new Dialog<>();
                GridPane gridPane = new GridPane();

                Label label_datum = new Label("Datum: ");
                gridPane.add(label_datum, 1, 1);
                TextField feld_datum = new TextField();
                gridPane.add(feld_datum, 2, 1);

                Label label_betrag = new Label("Betrag: ");
                gridPane.add(label_betrag, 1, 2);
                TextField feld_betrag = new TextField();
                gridPane.add(feld_betrag, 2, 2);

                Label label_beschreibung = new Label("Beschreibung: ");
                gridPane.add(label_beschreibung, 1, 3);
                TextField feld_beschreibung = new TextField();
                gridPane.add(feld_beschreibung, 2, 3);

                Label label_incInt = new Label("IncomingInterest: ");
                gridPane.add(label_incInt, 1, 4);
                TextField feld_incInt = new TextField();
                gridPane.add(feld_incInt, 2, 4);

                Label label_outInt = new Label("OutgoingInterest: ");
                gridPane.add(label_outInt, 1, 5);
                TextField feld_outInt = new TextField();
                gridPane.add(feld_outInt, 2, 5);


                ButtonType Ja = new ButtonType("Erstellen", ButtonBar.ButtonData.OK_DONE);
                ButtonType Nein = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialogInfos.getDialogPane().setContent(gridPane);
                dialogInfos.getDialogPane().getButtonTypes().add(Ja);
                dialogInfos.getDialogPane().getButtonTypes().add(Nein);
                dialogInfos.setTitle("Paymentdaten eingeben");
                dialogInfos.show();
                dialogInfos.setResultConverter(dialogButton ->
                {
                    if (dialogButton == Ja) {
                        if (!(feld_incInt.getText().equals("") || feld_outInt.getText().equals("") || feld_beschreibung.getText().equals("") || feld_betrag.getText().equals("") || feld_datum.getText().equals(""))) {
                            try {
                                Double.parseDouble(feld_betrag.getText());
                            } catch (NumberFormatException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe beim Betrag. Betrag muss double sein.");
                                alert.show();
                            }
                            try {
                                Double.parseDouble(feld_incInt.getText());
                            } catch (NumberFormatException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe bei IncomingInterest. IncomingInterest muss double sein.");
                                alert.show();
                            }
                            try {
                                Double.parseDouble(feld_outInt.getText());
                            } catch (NumberFormatException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe bei OutgoingInterest. OutgoingInterest muss double sein.");
                                alert.show();
                            }
                            if(Double.parseDouble(feld_betrag.getText())<=0){
                                Alert alert=new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe beim Betrag. Betrag muss positiv sein.");
                            }
                            if(Double.parseDouble(feld_incInt.getText())<=0||Double.parseDouble(feld_incInt.getText())>1){
                                Alert alert=new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe bei IncomingInterest. INcomingInterest muss zwischen 0 und 1 liegen.");
                            }
                            if(Double.parseDouble(feld_outInt.getText())<=0||Double.parseDouble(feld_outInt.getText())>1){
                                Alert alert=new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe bei OutgoingInterest. OutgoingInterest muss zwischen 0 und 1 liegen.");
                            }
                            if (Double.parseDouble(feld_betrag.getText()) > 0 &&
                                    Double.parseDouble(feld_incInt.getText()) > 0 &&
                                    Double.parseDouble(feld_incInt.getText()) <= 1 &&
                                    Double.parseDouble(feld_outInt.getText()) > 0 &&
                                    Double.parseDouble(feld_outInt.getText()) <= 1) {
                                try {
                                    Payment pay = new Payment(feld_datum.getText(),
                                            Double.parseDouble(feld_betrag.getText()),
                                            feld_beschreibung.getText(),
                                            Double.parseDouble(feld_incInt.getText()),
                                            Double.parseDouble(feld_outInt.getText()));
                                    pb1.addTransaction(account, pay);
                                } catch (TransactionAttributeException | AccountDoesNotExistException |
                                         TransactionAlreadyExistException | IOException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Fehler");
                                    alert.setHeaderText("Fehler");
                                    alert.setContentText(e.toString());
                                    alert.show();
                                }
                            }
                        }
                    }
                    update();
                    return null;
                });
            } else {
                Dialog<Transaction> dialogInfos = new Dialog<>();
                GridPane gridPane = new GridPane();

                Label label_datum = new Label("Datum: ");
                gridPane.add(label_datum, 1, 1);
                TextField feld_datum = new TextField();
                gridPane.add(feld_datum, 2, 1);

                Label label_betrag = new Label("Betrag: ");
                gridPane.add(label_betrag, 1, 2);
                TextField feld_betrag = new TextField();
                gridPane.add(feld_betrag, 2, 2);

                Label label_beschreibung = new Label("Beschreibung: ");
                gridPane.add(label_beschreibung, 1, 3);
                TextField feld_beschreibung = new TextField();
                gridPane.add(feld_beschreibung, 2, 3);

                Label label_send = new Label("Sender: ");
                gridPane.add(label_send, 1, 4);
                TextField feld_send = new TextField();
                gridPane.add(feld_send, 2, 4);

                Label label_empf = new Label("Empfänger: ");
                gridPane.add(label_empf, 1, 5);
                TextField feld_empf = new TextField();
                gridPane.add(feld_empf, 2, 5);


                ButtonType Ja = new ButtonType("Erstellen", ButtonBar.ButtonData.OK_DONE);
                ButtonType Nein = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialogInfos.getDialogPane().setContent(gridPane);
                dialogInfos.getDialogPane().getButtonTypes().add(Ja);
                dialogInfos.getDialogPane().getButtonTypes().add(Nein);
                dialogInfos.setTitle("Transferdaten eingeben");
                dialogInfos.show();
                dialogInfos.setResultConverter(dialogButton ->
                {
                    if (dialogButton == Ja) {
                        if (!(feld_send.getText().equals("") || feld_empf.getText().equals("") || feld_beschreibung.getText().equals("") || feld_betrag.getText().equals("") || feld_datum.getText().equals(""))) {
                            try {
                                Double.parseDouble(feld_betrag.getText());
                            } catch (NumberFormatException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe beim Betrag. Betrag muss double sein.");
                                alert.show();
                            }
                            if(Double.parseDouble(feld_betrag.getText())<=0){
                                Alert alert=new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe beim Betrag. Betrag muss positiv sein.");
                            }
                            if(feld_empf.getText().equals(feld_send.getText())){
                                Alert alert=new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe beim Sender und Empfänger. Sender kann nicht Empfänger sein.");
                            }

                            if (Double.parseDouble(feld_betrag.getText()) > 0&& !(feld_empf.getText().equals(feld_send.getText()))) {
                                try {
                                    Transfer transfer = new Transfer(feld_datum.getText(),
                                            Double.parseDouble(feld_betrag.getText()),
                                            feld_beschreibung.getText(),
                                            feld_send.getText(),
                                            feld_empf.getText()) {
                                    };
                                    pb1.addTransaction(account, transfer);
                                } catch (TransactionAttributeException | AccountDoesNotExistException |
                                         TransactionAlreadyExistException | IOException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Fehler");
                                    alert.setHeaderText("Fehler");
                                    alert.setContentText(e.toString());
                                    alert.show();
                                }
                            }
                        }
                    }
                    update();
                    return null;
                });
            }
        }
    }
}