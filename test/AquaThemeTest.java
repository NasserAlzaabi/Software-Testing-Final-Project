import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.plaf.ColorUIResource;

class AquaThemeTest {

    @Test
    void testGetName() {
        AquaTheme theme = new AquaTheme();
        assertEquals("Pure Aqua", theme.getName());
    }

    @Test
    void testGetPrimaryColors() {
        AquaTheme theme = new AquaTheme();
        assertEquals(new ColorUIResource(102, 153, 153), theme.getPrimary1());
        assertEquals(new ColorUIResource(128, 192, 192), theme.getPrimary2());
        assertEquals(new ColorUIResource(159, 235, 235), theme.getPrimary3());
    }

    @Test
    void testGetSecondaryColors() {
        AquaTheme theme = new AquaTheme();
        assertEquals(new ColorUIResource(204, 204, 204), theme.getSecondary2());
        assertEquals(new ColorUIResource(160, 225, 225), theme.getSecondary3());
    }

    @Test
    void testColorConsistency() {
        AquaTheme theme1 = new AquaTheme();
        AquaTheme theme2 = new AquaTheme();
        assertEquals(theme1.getPrimary1(), theme2.getPrimary1());
        assertEquals(theme1.getPrimary2(), theme2.getPrimary2());
        assertEquals(theme1.getPrimary3(), theme2.getPrimary3());
        assertEquals(theme1.getSecondary2(), theme2.getSecondary2());
        assertEquals(theme1.getSecondary3(), theme2.getSecondary3());
    }
}
