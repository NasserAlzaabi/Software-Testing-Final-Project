import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BankHelpTest {
    @Test
    void testBankHelpInitialization() {
        BankHelp bankHelp = new BankHelp("Help Window", "test.html");
        assertEquals("Help Window", bankHelp.getTitle());
        assertNotNull(bankHelp.getContentPane());
        assertTrue(bankHelp.isVisible());
        assertEquals(500, bankHelp.getWidth());
        assertEquals(350, bankHelp.getHeight());
    }
}
