package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class LayoutTest {

    private Layout qwerty;
    private Layout colemakDH;

    @BeforeEach
    public void initialize() {
        qwerty = new Layout(
                "QWERTY",
                "The quintessential QWERTY layout",
                "qwertyuiopasdfghjkl;zxcvbnm,./ "
        );

        colemakDH = new Layout(
                "Colemak-DH",
                "Colemak variant that moves D and H",
                "qwfpbjluy;arstgmneioxcdvzkh,./ "
        );
    }

    @Test
    public void testGetName() {
        assertEquals("QWERTY", qwerty.getName());
        assertEquals("Colemak-DH", colemakDH.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("The quintessential QWERTY layout", qwerty.getDescription());
        assertEquals("Colemak variant that moves D and H", colemakDH.getDescription());
    }

    @Test
    public void testGetLayoutString() {
        assertEquals("qwertyuiopasdfghjkl;zxcvbnm,./ ", qwerty.getLayoutString());
        assertEquals("qwfpbjluy;arstgmneioxcdvzkh,./ ", colemakDH.getLayoutString());
    }

    @Test
    public void testGetIndexOfKey() {
        assertEquals(0, qwerty.getIndexOfKey('q'));
        assertEquals(6, qwerty.getIndexOfKey('u'));
        assertEquals(-1, qwerty.getIndexOfKey('0'));

        assertEquals(0, colemakDH.getIndexOfKey('q'));
        assertEquals(7, colemakDH.getIndexOfKey('u'));
        assertEquals(-1, colemakDH.getIndexOfKey('1'));
    }

    @Test
    public void testToJson() {
        JSONObject expected = new JSONObject();

        expected.put("name", "QWERTY");
        expected.put("description", "The quintessential QWERTY layout");
        expected.put("layout", "qwertyuiopasdfghjkl;zxcvbnm,./ ");

        assertTrue(expected.similar(qwerty.toJson()));
    }
}
