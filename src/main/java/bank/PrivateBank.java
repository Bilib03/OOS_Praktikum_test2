package bank;

// vorgefertigte

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


// selbst geschrieben:
import bank.exceptions.*;
import com.google.gson.reflect.TypeToken;


/**
 * class to implement methods of the interface {@link Bank}
 */
public class PrivateBank implements Bank {
    /**
     * private attribute: Name of the {@link PrivateBank}
     */
    private String name;

    /**
     * private attribute: specifies the interest rate that accrues when a deposit is made,
     * it has to be positive value in percent (0 to 1)
     * and is identical to the attribute of the same name in the {@link Payment} class.
     */
    private double incomingInterest;

    /**
     * private attribute: specifies the interest rate that is incurred when a withdrawal is made,
     * it has to be positive value in percent (0 to 1)
     * and is identical to the attribute of the same name in the {@link Payment} class.
     */
    private double outgoingInterest;

    /**
     * private attribute: connects accounts to {@link Transaction},
     * so that 0 to n transactions can be assigned to each stored account.
     * The key represents the name of the account.
     * for example: „Account Hans“ -> [Transaction 1, Transaction2]
     */
    public HashMap<String, ArrayList<Transaction>> accountsToTransactions = new HashMap<>();
    // In contrast to Map, HashMap can hold duplicate values.

    /**
     * private attribute: specifies the location of the Json file, where the {@link Transaction} object is stored
     * can be a specified folder, relative or absolute path
     */
    private String directoryName;

    // getter + setter:

    /**
     * returns private attribute "name" of {@link PrivateBank}-Object
     *
     * @return String
     */
    public String get_name() {
        return name;
    }

    /**
     * sets value of private attribute "name" of {@link PrivateBank}-Object
     *
     * @param temp_name Name of the {@link PrivateBank}
     */
    public void set_name(String temp_name) {
        name = temp_name;
    }

    /**
     * returns private attribute "incomingInterest" of a {@link Payment}-object
     *
     * @return double
     */
    public double get_incomingInterest() {
        return incomingInterest;
    }

    /**
     * sets the value of the private attribute "incomingInterest" of a {@link Payment}-object
     *
     * @param temp_inInterest must be percentage between 0 and 1
     */
    public void set_incomingInterest(double temp_inInterest) throws TransactionAttributeException {
        if (temp_inInterest >= 0 && temp_inInterest <= 1)
            incomingInterest = temp_inInterest;
        else
            throw new TransactionAttributeException("Eine der Transaktion ist so nicht möglich.");
    }

    /**
     * returns private attribute "outgoingInterest" of a {@link Payment}-object
     *
     * @return double
     */
    public double get_outgoingInterest() {
        return outgoingInterest;
    }

    /**
     * sets the value of the private attribute "outgoingInterest" of a {@link Payment}-object
     *
     * @param temp_outInterest must be percentage between 0 and 1
     */
    public void set_outgoingInterest(double temp_outInterest) throws TransactionAttributeException {
        if (temp_outInterest >= 0 && temp_outInterest <= 1)
            outgoingInterest = temp_outInterest;
        else
            throw new TransactionAttributeException("Eine der Transaktion ist so nicht möglich.");
    }

    /**
     * returns the file path of the json file where the {@link PrivateBank}-Object is stored
     *
     * @return String
     */
    public String getDirectoryName() {
        return directoryName;
    }

    /**
     * sets the file path of the json file where the {@link PrivateBank}-Object will be stored
     *
     * @param directoryName file path can be a specified folder, relative or absolute path
     */
    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }


    // constructor:

    /**
     * creates a new {@link PrivateBank}-Object with the given parameters
     *
     * @param temp_name          name of {@link PrivateBank}
     * @param temp_inInterest    interest rate of deposit
     * @param temp_outInterest   interest rate of withdrawal
     * @param temp_directoryName file path of {@link PrivateBank}-Json file
     */
    public PrivateBank(String temp_name, double temp_inInterest, double temp_outInterest, String temp_directoryName)
            throws TransactionAttributeException, TransactionAlreadyExistException, AccountAlreadyExistsException, IOException {
        set_name(temp_name);
        set_incomingInterest(temp_inInterest);
        set_outgoingInterest(temp_outInterest);
        setDirectoryName(temp_directoryName);
        readAccounts();
    }

    // copy-constructor:

    /**
     * creates a new {@link PrivateBank}-Object with the same attributes of the transferring object
     *
     * @param x is the already existing object, which will be copied
     */
    public PrivateBank(PrivateBank x)
            throws TransactionAttributeException, TransactionAlreadyExistException, AccountAlreadyExistsException, IOException {
        set_name(x.get_name());
        set_incomingInterest(x.get_incomingInterest());
        set_outgoingInterest(x.get_outgoingInterest());
        setDirectoryName(x.getDirectoryName());
        readAccounts();
    }


    // object-methods :

    /**
     * turns {@link Transaction}-object in a described String
     *
     * @return String
     */
    @Override
    public String toString() {
        return ("name: " + get_name() + "\n"
                + "deposit: " + get_incomingInterest() + "\n"
                + "withdrawal: " + get_outgoingInterest() + "\n"
                + "map: " + accountsToTransactions + "\n"
                + "file path: " + getDirectoryName() + "\n");
    }

    /**
     * compares two objects of the same class on their attributes
     *
     * @param obj is the second {@link PrivateBank}-object which will be compared
     * @return boolean: true if both are equal
     */
    @Override
    public boolean equals(Object obj) {
        // tests if both objects have the same classes
        if(obj==this)return true;
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        PrivateBank other = (PrivateBank) obj;
        return Objects.equals(get_name(), other.get_name())
                && Objects.equals(get_incomingInterest(), other.get_incomingInterest())
                && Objects.equals(get_outgoingInterest(), other.get_outgoingInterest())
                && Objects.equals(accountsToTransactions, other.accountsToTransactions)
                && Objects.equals(getDirectoryName(), other.getDirectoryName());
    }


    // implements Interface-methods:

    /**
     * Adds an account to the bank.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account already exists
     */
    @Override
    public void createAccount(String account)
            throws AccountAlreadyExistsException, IOException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Der Account existiert bereits.");
        } else {
            accountsToTransactions.put(account, new ArrayList<>());
            writeAccounts(account);
        }
    }

    /**
     * Adds an account (with specified transactions) to the bank.
     * Important: duplicate transactions must not be added to the account!
     *
     * @param account      the account to be added
     * @param transactions a list of already existing transactions which should be added to the newly created account
     * @throws AccountAlreadyExistsException    if the account already exists
     * @throws TransactionAlreadyExistException if the transaction already exists
     */
    @Override
    public void createAccount(String account, ArrayList<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException {
        createAccount(account);
        for (Transaction temp : transactions) {
            if (!accountsToTransactions.get(account).contains(temp)) {
                if (temp instanceof Payment) {
                    ((Payment) temp).set_incomingInterest(this.incomingInterest);
                    ((Payment) temp).set_outgoingInterest(this.outgoingInterest);
                }
                accountsToTransactions.get(account).add(temp);
                writeAccounts(account);
            } else
                throw new TransactionAlreadyExistException("Die Transaktion existiert bereits.");
        }
    }

    /**
     * Adds a transaction to an already existing account.
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionAlreadyExistException if the transaction already exists
     */
    @Override
    public void addTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionAlreadyExistException, TransactionAttributeException, IOException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Der Account existiert nicht.");
        } else if (accountsToTransactions.get(account).contains(transaction)) {
            throw new TransactionAlreadyExistException("Der Account besitzt bereits diese Transaktion.");
        } else {
            if (transaction.getClass() == Payment.class) {
                ((Payment) transaction).set_incomingInterest(this.get_incomingInterest());
                ((Payment) transaction).set_outgoingInterest(this.get_outgoingInterest());
            }
            accountsToTransactions.get(account).add(transaction);
            writeAccounts(account);
        }
    }

    /**
     * C:\Users\Philipp\IdeaProjects\praktikum\src\main\resourcess a transaction from an account. If the transaction does not exist, an exception is thrown.
     *
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is removed from the specified account
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionDoesNotExistException if the transaction cannot be found
     */
    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Der Account existiert nicht.");
        } else if (!accountsToTransactions.get(account).contains(transaction)) {
            throw new TransactionDoesNotExistException("Der Account besitzt diese Transaktion nicht.");
        } else {
            accountsToTransactions.get(account).remove(transaction);
            writeAccounts(account);
        }
    }

    /**
     * Checks whether the specified transaction for a given account exists.
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     * @return true if specified transaction is contained in given account
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        return accountsToTransactions.get(account).contains(transaction);
    }

    /**
     * Calculates and returns the current account balance.
     *
     * @param account the selected account
     * @return the current account balance
     */
    @Override
    public double getAccountBalance(String account) {
        double result = 0;
        if (accountsToTransactions.containsKey(account)) {
            for (Transaction temp : accountsToTransactions.get(account)) {
                result += temp.calculate();
            }
        }
        return result;
    }

    /**
     * Returns a list of transactions for an account.
     *
     * @param account the selected account
     * @return the list of all transactions for the specified account
     */
    @Override
    public ArrayList<Transaction> getTransactions(String account) {
        return accountsToTransactions.get(account);
    }

    /**
     * Returns a sorted list (-> calculated amounts) of transactions for a specific account.
     * Sorts the list either in ascending or descending order (or empty).
     *
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted in ascending or descending order
     * @return the sorted list of all transactions for the specified account
     */
    @Override
    public ArrayList<Transaction> getTransactionsSorted(String account, boolean asc) {
        ArrayList<Transaction> result = new ArrayList<Transaction>(accountsToTransactions.get(account));
        result.sort(Comparator.comparingDouble(CalculateBill::calculate));
        if (!asc)
            Collections.reverse(result);
        return result;
    }

    /**
     * Returns a list of either positive or negative transactions (-> calculated amounts).
     *
     * @param account  the selected account
     * @param positive selects if positive or negative transactions are listed
     * @return the list of all transactions by type
     */
    @Override
    public ArrayList<Transaction> getTransactionsByType(String account, boolean positive) {
        ArrayList<Transaction> result = new ArrayList<Transaction>();
        for (Transaction temp : accountsToTransactions.get(account)) {
            if (temp.calculate() >= 0 && positive) {
                result.add(temp);
            } else if (temp.calculate() < 0 && !positive) {
                result.add(temp);
            }
        }
        return result;
    }

    /**
     * opens all Accounts-files and puts them into accountsToTransactions
     *
     * @throws IOException if the file cannot be created or cannot be opened
     * @throws TransactionAlreadyExistException if the specified transaction already exist
     * @throws AccountAlreadyExistsException if the specified account already exist
     */
    private void readAccounts()
            throws IOException, TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new CustomDeSerializer())
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        File folder = new File(directoryName);
        File[] listOfFiles = folder.listFiles(); // liste alle Files in dem Ordner auf

        if (listOfFiles != null) {
            for (File file : listOfFiles) {                                                         // gehe Files durch
                FileReader reader = new FileReader(file);
                String filename = file.getName();
                if (filename.endsWith(".json")) {                                                   // Lese nur .json files aus
                    String account = filename.substring(0, filename.length() - 5);                  // account = filename - ".json"
                    ArrayList<Transaction> list = gson.fromJson(reader, new TypeToken<ArrayList<Transaction>>() {
                    }.getType()); // jeweiliges Json-File wird in ArrayList<Transaction> umgewandelt, ruft dann indirekt Custom Deserializer auf
                    if (accountsToTransactions.containsKey(account)) {
                        this.accountsToTransactions.put(account, list);                             // Setze Account Transaktionen gleich list
                    } else {
                        this.createAccount(account, list);          // erstelle Account mit den Transaktionen in list
                    }
                }
                reader.close();
            }
        }
    }


    /**
     * saves the specified account to the file system
     *
     * @param account name of the account and file name
     * @throws IOException if the file cannot be created or cannot be opened
     */
    private void writeAccounts(String account)
            throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Transaction.class, new CustomDeSerializer())
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        FileWriter file = new FileWriter(directoryName + account + ".json");
        file.write(gson.toJson(getTransactions(account).toArray(), Transaction[].class));
        file.close();
    }

    @Override
    public void deleteAccount(String account)throws AccountDoesNotExistException, IOException{
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Der Account existiert nicht.");
        }else{
            accountsToTransactions.remove(account);
            File f=new File(directoryName + "/" + account + ".json");
            if(f.delete())System.out.println("Hat geklappt");
        }
    }

    @Override
    public List<String> getAllAccounts() {
        List<String> accounts=new ArrayList<String>() ;
        for(String name :accountsToTransactions.keySet())
        {
            accounts.add(name);
        }
        return accounts;
    }
}