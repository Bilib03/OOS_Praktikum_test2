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
    private Label accountBalance;

    @FXML
    private Label accountName;

    @FXML
    private ListView<Transaction> transactionList;

    public Accountview() throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
    }

    public void update() {
        accountBalance.setText("Kontostand: " + pb1.getAccountBalance(account));
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactions(account));
    }
    public void setAccount(String account) {
        this.account = account;
        accountName.setText(account);
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactions(account));
    }

    public void switchToMainview(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Mainview.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

            pb1.getAllAccounts();
            System.out.println(pb1.getAllAccounts());
            transactionList.getItems().addAll(pb1.getTransactions(account));
            update();

    }

    public void confirmationDialog(javafx.event.ActionEvent event) throws AccountDoesNotExistException, IOException, TransactionDoesNotExistException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sind Sie sicher?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            pb1.removeTransaction("account1", transactionList.getSelectionModel().getSelectedItem());
            transactionList.getItems().remove(transactionList.getSelectionModel().getSelectedItem());
        }
    }

    public void showTransactionsSortedAsc(javafx.event.ActionEvent event) {
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactionsSorted(account, true));
    }

    public void showTransactionsSortedDesc(javafx.event.ActionEvent event) {
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactionsSorted(account, false));
    }

    public void showTransactionsByTypePos(javafx.event.ActionEvent event) {
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactionsByType(account, true));
    }

    public void showTransactionsByTypeNeg(javafx.event.ActionEvent event) {
        transactionList.getItems().clear();
        transactionList.getItems().addAll(pb1.getTransactionsByType(account, false));
    }

    public void addTransactionDialog(javafx.event.ActionEvent event) throws AccountAlreadyExistsException {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", "Transfer", "Payment");
        dialog.setTitle("Transaktion hinzufügen");
        dialog.setHeaderText("Transaktion hinzufügen");
        dialog.setContentText("Wählen Sie den Transaktionstyp:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().equals("Payment")) {
                Dialog<Transaction> dialog1 = new Dialog<>();
                GridPane gridPane = new GridPane();

                Label datuml = new Label("Datum: ");
                gridPane.add(datuml, 1, 1);
                TextField datumField = new TextField();
                gridPane.add(datumField, 2, 1);

                Label betragl = new Label("Betrag: ");
                gridPane.add(betragl, 1, 2);
                TextField betragField = new TextField();
                gridPane.add(betragField, 2, 2);
                Label beschreibungl = new Label("Beschreibung: ");
                gridPane.add(beschreibungl, 1, 3);
                TextField beschreibung = new TextField();
                gridPane.add(beschreibung, 2, 3);
                Label incl = new Label("IncomingInterest: ");
                gridPane.add(incl, 1, 4);
                TextField inc = new TextField();
                gridPane.add(inc, 2, 4);
                Label outl = new Label("OutgoingInterest: ");
                gridPane.add(outl, 1, 5);
                TextField outField = new TextField();
                gridPane.add(outField, 2, 5);

                ButtonType Ja = new ButtonType("Erstellen", ButtonBar.ButtonData.OK_DONE);
                ButtonType Nein = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog1.getDialogPane().setContent(gridPane);
                dialog1.getDialogPane().getButtonTypes().add(Ja);
                dialog1.getDialogPane().getButtonTypes().add(Nein);
                dialog1.setTitle("Payment");
                dialog1.setResultConverter(dialogButton ->
                {
                    if (dialogButton == Ja) {
                        if (!(datumField.getText().equals("") || betragField.getText().equals("") || beschreibung.getText().equals("") || inc.getText().equals("") || outField.getText().equals(""))) {
                            try {
                                Double.parseDouble(betragField.getText());
                            } catch (NumberFormatException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe! Bitte nur double beim Betrag!");
                                alert.show();
                            }
                            try {
                                Double.parseDouble(inc.getText());
                            } catch (NumberFormatException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe! Bitte nur double beim Incoming!");
                                alert.show();
                            }
                            try {
                                Double.parseDouble(outField.getText());
                            } catch (NumberFormatException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe! Bitte nur double beim Outgoing!");
                                alert.show();
                            }

                            if (Double.parseDouble(betragField.getText()) > 0 && (Double.parseDouble(inc.getText()) > 0 && Double.parseDouble(inc.getText()) <= 1) && (Double.parseDouble(outField.getText()) > 0 && Double.parseDouble(outField.getText()) <= 1)) {
                                try {
                                    Payment tmp = new Payment(datumField.getText(), Double.parseDouble(betragField.getText()),beschreibung.getText(),  Double.parseDouble(inc.getText()), Double.parseDouble(outField.getText()));
                                    pb1.addTransaction(account, tmp);
                                } catch (TransactionAlreadyExistException | AccountDoesNotExistException |
                                         TransactionAttributeException |  IOException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Fehler");
                                    alert.setHeaderText("Fehler");
                                    alert.setContentText(e.toString());
                                    alert.show();
                                }
                                update();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe! Betrag darf nicht negativ sein!, Incoming und Outgoing müssen zwischen 0 und 1 liegen!");
                                alert.show();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Fehler");
                            alert.setHeaderText("Fehler");
                            alert.setContentText("Fehler!, bitte füllen sie alle Felder aus!");
                            alert.show();
                        }
                    }
                    return null;
                });
                dialog1.show();
            } else if (result.get().equals("Transfer")) {
                Dialog<Transaction> dialog2 = new Dialog<>();
                GridPane gridPane = new GridPane();

                Label datuml = new Label("Datum: ");
                gridPane.add(datuml, 1, 1);

                TextField datumField = new TextField();
                gridPane.add(datumField, 2, 1);

                Label betragl = new Label("Betrag: ");
                gridPane.add(betragl, 1, 2);

                TextField betragField = new TextField();
                gridPane.add(betragField, 2, 2);

                Label beschreibungl = new Label("Beschreibung: ");
                gridPane.add(beschreibungl, 1, 3);

                TextField beschreibung = new TextField();
                gridPane.add(beschreibung, 2, 3);

                Label senderl = new Label("sender: ");
                gridPane.add(senderl, 1, 4);

                TextField senderField = new TextField();
                gridPane.add(senderField, 2, 4);

                Label empaengerl = new Label("empfaenger: ");
                gridPane.add(empaengerl, 1, 5);

                TextField empaengerField = new TextField();
                gridPane.add(empaengerField, 2, 5);

                ButtonType Ja = new ButtonType("Erstellen", ButtonBar.ButtonData.OK_DONE);
                ButtonType Nein = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog2.getDialogPane().setContent(gridPane);
                dialog2.getDialogPane().getButtonTypes().add(Ja);
                dialog2.getDialogPane().getButtonTypes().add(Nein);
                dialog2.setTitle("Transfer");
                dialog2.setResultConverter(dialogButton ->
                {
                    if (dialogButton == Ja) {
                        if (!(datumField.getText().equals("") || betragField.getText().equals("") || beschreibung.getText().equals("") || senderField.getText().equals("") || empaengerField.getText().equals(""))) {
                            try {
                                Double.parseDouble(betragField.getText());
                            } catch (NumberFormatException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe! Bitte nur double beim Betrag!");
                                alert.show();
                            }
                            if ((senderField.getText().equals(account) && !(empaengerField.getText().equals(account))) || (empaengerField.getText().equals(account) && !(senderField.getText().equals(account)))) {
                                if (senderField.getText().equals(account)) {
                                    try {
                                        OutgoingTransfer tmp = new OutgoingTransfer(datumField.getText(), Double.parseDouble(betragField.getText()),beschreibung.getText(),  senderField.getText(), empaengerField.getText());
                                        pb1.addTransaction(account, tmp);
                                    } catch (TransactionAlreadyExistException | AccountDoesNotExistException |
                                             TransactionAttributeException  e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Fehler");
                                        alert.setHeaderText("Fehler");
                                        alert.setContentText(e.toString());
                                        alert.show();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    try {
                                        IncomingTransfer tmp = new IncomingTransfer(datumField.getText(), Double.parseDouble(betragField.getText()), beschreibung.getText(), senderField.getText(), empaengerField.getText());
                                        pb1.addTransaction(account, tmp);
                                    } catch (TransactionAlreadyExistException | AccountDoesNotExistException |
                                             TransactionAttributeException  e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Fehler");
                                        alert.setHeaderText("Fehler");
                                        alert.setContentText(e.toString());
                                        alert.show();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                update();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Fehler");
                                alert.setContentText("Fehlerhafte Eingabe! Sender und Empfänger müssen unterschiedlich sein!");
                                alert.show();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Fehler");
                            alert.setHeaderText("Fehler");
                            alert.setContentText("Fehler!, bitte füllen sie alle Felder aus!");
                            alert.show();
                        }
                    }
                    return null;
                });
                dialog2.show();
            } else {
                System.out.println("Fehler");
            }
        }
    }
}