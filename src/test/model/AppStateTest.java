package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppStateTest {

    private AppState appState;
    private KeyboardGeometry testKg;
    private KeyboardGeometry testKg2;

    @BeforeEach
    public void beforeEach() {
        appState = new AppState();
        testKg = new KeyboardGeometry("TestKg", "");
        testKg2 = new KeyboardGeometry("TestKg2", "");
    }

    @Test
    public void testAdd() {
        appState.getKeyboardGeometries().add(testKg);
        appState.getKeyboardGeometries().add(testKg2);
        assertEquals(2, appState.getKeyboardGeometries().size());
        assertEquals(0, appState.getCorpora().size());
        assertEquals(0, appState.getLayouts().size());
        assertEquals(0, appState.getEffortModels().size());
        assertEquals(0, appState.getTournaments().size());

        assertNull(appState.getEffortModels().remove("ShouldFindNothing"));
        assertEquals(2, appState.getKeyboardGeometries().size());
        assertEquals(0, appState.getCorpora().size());
        assertEquals(0, appState.getLayouts().size());
        assertEquals(0, appState.getEffortModels().size());
        assertEquals(0, appState.getTournaments().size());

        assertNull(appState.getKeyboardGeometries().get("ShouldFindNothing"));
        assertEquals(2, appState.getKeyboardGeometries().size());
        assertEquals(0, appState.getCorpora().size());
        assertEquals(0, appState.getLayouts().size());
        assertEquals(0, appState.getEffortModels().size());
        assertEquals(0, appState.getTournaments().size());


        assertEquals(testKg2, appState.getKeyboardGeometries().get("TestKg2"));
        assertEquals(testKg2, appState.getKeyboardGeometries().remove("TestKg2"));
        assertNull(appState.getKeyboardGeometries().get("ShouldFindNothing"));

        assertEquals(testKg, appState.getKeyboardGeometries().remove("TestKg"));
        assertEquals(0, appState.getKeyboardGeometries().size());
        assertEquals(0, appState.getLayouts().size());
        assertEquals(0, appState.getCorpora().size());
        assertEquals(0, appState.getEffortModels().size());
        assertEquals(0, appState.getTournaments().size());
    }


}
