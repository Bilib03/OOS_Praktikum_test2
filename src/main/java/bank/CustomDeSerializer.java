package bank;

import bank.exceptions.TransactionAttributeException;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * implements interfaces JsonSerializer and JsonDeserializer
 */
public class CustomDeSerializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    /**
     * converts a {@link Transaction}-object into an object and brings it into the correct display structure
     *
     * @param transaction {@link Transaction}-object that will be converted
     * @return JsonObject which can be stored
     */
    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        JsonObject instance = new JsonObject();

        if (transaction instanceof Payment) {
            obj.addProperty("CLASSNAME", transaction.getClass().getSimpleName());

            instance.addProperty("incomingInterest", ((Payment) transaction).get_incomingInterest());
            instance.addProperty("outgoingInterest", ((Payment) transaction).get_outgoingInterest());
            instance.addProperty("date", (transaction).get_date());
            instance.addProperty("amount", (transaction).get_amount());
            instance.addProperty("description", (transaction).get_description());

        } else if (transaction instanceof IncomingTransfer) {
            obj.addProperty("CLASSNAME", transaction.getClass().getSimpleName());

            instance.addProperty("sender", ((IncomingTransfer) transaction).get_sender());
            instance.addProperty("recipient", ((IncomingTransfer) transaction).get_recipient());
            instance.addProperty("date", (transaction).get_date());
            instance.addProperty("amount", (transaction).get_amount());
            instance.addProperty("description", (transaction).get_description());

        } else if (transaction instanceof OutgoingTransfer) {
            obj.addProperty("CLASSNAME", transaction.getClass().getSimpleName());

            instance.addProperty("sender", ((OutgoingTransfer) transaction).get_sender());
            instance.addProperty("recipient", ((OutgoingTransfer) transaction).get_recipient());
            instance.addProperty("date", (transaction).get_date());
            instance.addProperty("amount", (transaction).get_amount());
            instance.addProperty("description", (transaction).get_description());
        }

        obj.add("INSTANCE", instance);

        return obj;
    }

    /**
     * converts a Json file to a {@link Transaction}-object
     *
     * @param jsonElement the file which will be read
     * @return Transaction object
     */
    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jObj = jsonElement.getAsJsonObject();
        JsonObject instance = jObj.getAsJsonObject("INSTANCE");

        if (jObj.get("CLASSNAME").getAsString().equals("Payment")) {
            try {
                return new Payment(
                        instance.get("date").getAsString(),
                        instance.get("amount").getAsInt(),
                        instance.get("description").getAsString(),
                        instance.get("incomingInterest").getAsDouble(),
                        instance.get("outgoingInterest").getAsDouble()
                        );
            } catch (TransactionAttributeException e) {
                throw new RuntimeException(e);
            }
        } else if (jObj.get("CLASSNAME").getAsString().equals("IncomingTransfer")) {
            try {
                return new IncomingTransfer(
                        instance.get("date").getAsString(),
                        instance.get("amount").getAsInt(),
                        instance.get("description").getAsString(),
                        instance.get("sender").getAsString(),
                        instance.get("recipient").getAsString()
                        );
            } catch (TransactionAttributeException e) {
                throw new RuntimeException(e);
            }
        } else if (jObj.get("CLASSNAME").getAsString().equals("OutgoingTransfer")) {
            try {
                return new OutgoingTransfer(
                        instance.get("date").getAsString(),
                        instance.get("amount").getAsInt(),
                        instance.get("description").getAsString(),
                        instance.get("sender").getAsString(),
                        instance.get("recipient").getAsString()
                );
            } catch (TransactionAttributeException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
