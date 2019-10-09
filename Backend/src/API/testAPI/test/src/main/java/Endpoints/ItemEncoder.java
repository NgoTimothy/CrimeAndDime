package Endpoints;

import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ItemEncoder implements Encoder.Text<Item> {
    private static Gson gson = new Gson();

    @Override
    public String encode(Item item) throws EncodeException {
        return gson.toJson(item);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
