package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppStateTest {

    private AppState appState;
    private KeyboardGeometry testKg;

    @BeforeEach
    public void beforeEach() {
        appState = new AppState();
        testKg = new KeyboardGeometry("TestKg", "");
    }

    @Test
    public void testAdd() {
        appState.getKeyboardGeometries().add(testKg);
        assertEquals(1, appState.getKeyboardGeometries().size());
        assertEquals(0, appState.getCorpora().size());
        assertEquals(0, appState.getLayouts().size());
        assertEquals(0, appState.getEffortModels().size());
        assertEquals(0, appState.getTournaments().size());

        assertNull(appState.getEffortModels().remove("ShouldFindNothing"));
        assertEquals(1, appState.getKeyboardGeometries().size());
        assertEquals(0, appState.getCorpora().size());
        assertEquals(0, appState.getLayouts().size());
        assertEquals(0, appState.getEffortModels().size());
        assertEquals(0, appState.getTournaments().size());

        assertEquals(testKg, appState.getKeyboardGeometries().get("TestKg"));
        assertNull(appState.getKeyboardGeometries().get("ShouldFindNothing"));

        assertEquals(testKg, appState.getKeyboardGeometries().remove("TestKg"));
        assertEquals(0, appState.getKeyboardGeometries().size());
        assertEquals(0, appState.getLayouts().size());
        assertEquals(0, appState.getCorpora().size());
        assertEquals(0, appState.getEffortModels().size());
        assertEquals(0, appState.getTournaments().size());
    }


}
