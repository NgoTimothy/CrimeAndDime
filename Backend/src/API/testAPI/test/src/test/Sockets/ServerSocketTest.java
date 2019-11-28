package Sockets;

import Endpoints.StoreInfo;
import Endpoints.WebSocketServer;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.Session;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServerSocketTest {
    WebSocketServer testSocket;
    String mockStr;
    Session mockSession;
    StoreInfo fakeStoreInfo;

    @Before
    public void setup() {
        testSocket = spy(new WebSocketServer());
        mockStr = "storeInfo\"{\"name\":\"tv\",\"quantity\":1,\"wholesaleCost\":100.0,\"retailCost\":25.0}\",\"{\"name\":\"phone\",\"quantity\":2,\"wholesaleCost\"" +
                ":80.0,\"retailCost\":88.0}\",\"{\"name\":\"chair\",\"quantity\":2,\"wholesaleCost\":35.5,\"retailCost\":39.05}\",\"{\"name\":\"tv\",\"quantity\":2,\"wholesaleCost\":100.0,\"retailCost\":110.0}\"]";
        mockSession = mock(Session.class);
        try {
            testSocket.onMessage(mockSession, mockStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenStoreInfoIsInMessageItWillCallParsingMethod() {
        verify(testSocket, times(1)).parseStoreInformation(mockSession, mockStr);
    }

    @Test
    public void afterStoreInfoIsParsedTheSocketSavesCorrectNumberOfElements() {
        fakeStoreInfo = testSocket.getStoreInfoBySession(mockSession);
        assertEquals(4, fakeStoreInfo.getInventory().getSize());
    }

    @Test
    public void afterStoreInfoIsParsedFirstElementIsCorrect() {
        fakeStoreInfo = testSocket.getStoreInfoBySession(mockSession);
        assertEquals(1, fakeStoreInfo.getList().get(0).getQuantity());
        assertEquals("Item price as not correct", 25, fakeStoreInfo.getList().get(0).getPrice(), 0);
        assertEquals("tv", fakeStoreInfo.getList().get(0).getName());
    }

    @Test
    public void afterStoreInfoIsParsedSecondElementIsCorrect() {
        fakeStoreInfo = testSocket.getStoreInfoBySession(mockSession);
        assertEquals(2, fakeStoreInfo.getList().get(1).getQuantity());
        assertEquals("Item price as not correct", 88, fakeStoreInfo.getList().get(1).getPrice(), 0);
        assertEquals("phone", fakeStoreInfo.getList().get(1).getName());
    }

    @Test
    public void afterStoreInfoIsParsedThirdElementIsCorrect() {
        fakeStoreInfo = testSocket.getStoreInfoBySession(mockSession);
        assertEquals(2, fakeStoreInfo.getList().get(2).getQuantity());
        assertEquals("Item price as not correct", 39.05, fakeStoreInfo.getList().get(2).getPrice(), 0);
        assertEquals("chair", fakeStoreInfo.getList().get(2).getName());
    }

    @Test
    public void afterStoreInfoIsParsedFourthElementIsCorrect() {
        fakeStoreInfo = testSocket.getStoreInfoBySession(mockSession);
        assertEquals(2, fakeStoreInfo.getList().get(3).getQuantity());
        assertEquals("Item price as not correct", 110, fakeStoreInfo.getList().get(3).getPrice(), 0);
        assertEquals("tv", fakeStoreInfo.getList().get(3).getName());
    }

}
