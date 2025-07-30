package Bank;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * A custom serializer and deserializer for the {@link Transaction} class and its subclasses.
 * This class handles the conversion of objects to JSON (serialization) and JSON back to objects (deserialization),
 * ensuring that the correct subclass of {@link Transaction} is maintained during the process.
 */
public class Serializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    /**
     * Serializes a {@link Transaction} object into its JSON representation.
     * <p>
     * This method adds a special "CLASSNAME" property to the JSON to store the class type
     * so that it can be properly deserialized back into its original class later.
     *
     * @param src        The {@link Transaction} object to serialize.
     * @param typeOfSrc  The actual type of the source object.
     * @param context    The serialization context used to serialize fields of the object.
     * @return A {@link JsonElement} representing the serialized JSON object.
     */
    @Override
    public JsonElement serialize(Transaction src, Type typeOfSrc, JsonSerializationContext context) {
        // JsonSerializationContext is a utility to serialize fields, including nested and complex objects, during custom serialization.

        // Create new JSON object
        JsonObject json = new JsonObject();

        // Adds CLASSNAME property to know which class it belongs to
        json.addProperty("CLASSNAME", src.getClass().getName());

        // Serialize the instance and add it as a property
        json.add("INSTANCE", context.serialize(src));
        return json;
    }

    /**
     * Deserializes a JSON representation back into a {@link Transaction} object or its subclasses.
     * <p>
     * This method inspects the "CLASSNAME" property in the JSON to determine the appropriate class
     * to deserialize into, ensuring polymorphic behavior is preserved.
     *
     * @param json     The JSON data being deserialized.
     * @param typeOfT  The type of the object to deserialize to.
     * @param context  The deserialization context used to deserialize fields of the object.
     * @return A {@link Transaction} object corresponding to the JSON data.
     * @throws JsonParseException If the JSON structure is invalid or the class name is unrecognized.
     */
    @Override
    public Transaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        // JsonDeserializationContext is a utility to deserialize fields, including nested and complex objects, during custom deserialization.

        // Convert the input JsonElement into a JsonObject for easier access to its properties
        JsonObject jsonObject = json.getAsJsonObject();

        // Check if it has the required properties
        if (!jsonObject.has("CLASSNAME") || !jsonObject.has("INSTANCE")) {
            throw new JsonParseException("Invalid JSON structure: Missing required keys 'CLASSNAME' or 'INSTANCE'.");
        }

        // Extract CLASSNAME and INSTANCE
        String className = jsonObject.get("CLASSNAME").getAsString();
        JsonElement instanceData = jsonObject.get("INSTANCE");

        // Use CLASSNAME to determine the appropriate class for deserialization
        switch (className) {
            case "Bank.Payment":
                return context.deserialize(instanceData, Payment.class);
            case "Bank.IncomingTransfer":
                return context.deserialize(instanceData, IncomingTransfer.class);
            case "Bank.OutgoingTransfer":
                return context.deserialize(instanceData, OutgoingTransfer.class);
            default:
                throw new JsonParseException("Unexpected CLASSNAME: " + className);
        }
    }
}
