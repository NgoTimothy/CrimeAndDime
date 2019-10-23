import com.mygdx.Screen.LobbyScreen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utility.Lobby;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StoreScreenTest {
    private tileMapScreen mockScreen;
    private tileMapScreen realScreen;

    @Before
    public void setup() {
        realScreen = new tileMapScreen();
        mockScreen = spy(realScreen);

        assertEquals(0,mockScreen);
        
        List mockedList = Mockito.mock(ArrayList.class);

        mockedList.add("one");
        Mockito.verify(mockedList).add("one");

        assertEquals(0, mockedList.size());
    }

}
