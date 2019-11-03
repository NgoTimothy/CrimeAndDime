package Endpoints;

import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Item Encoder encodes items to JSON string format
 */
public class ItemEncoder implements Encoder.Text<Item> {
    private static Gson gson = new Gson();

    /**
     * Encodes an item into JSON String format
     * @param item
     * @return
     * @throws EncodeException
     */
    @Override
    public String encode(Item item) throws EncodeException {
        return gson.toJson(item);
    }

    /**
     * Empty override method
     */
    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    /**
     * Empty override method
     */
    @Override
    public void destroy() {

    }
}
