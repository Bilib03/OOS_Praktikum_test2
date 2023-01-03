package ui;

import bank.*;
import bank.exceptions.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class SuperController {
    String directoryName = "C:\\Users\\phili\\IdeaProjects\\OOS_Praktikum_test2\\src\\main\\java\\bank\\Accounts\\";
    PrivateBank pb1 = new PrivateBank("pb1",0.1,0.1,directoryName);

    public SuperController() throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
    }
}