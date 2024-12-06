import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.plaf.ColorUIResource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.awt.event.*;

class BankSystemTest {
    @Test
    void testActionPerformedAddNew() throws Exception {
        BankSystem bankSystem = new BankSystem();
        Field addNewField = BankSystem.class.getDeclaredField("addNew");
        addNewField.setAccessible(true);
        JMenuItem addNew = (JMenuItem) addNewField.get(bankSystem);
        bankSystem.actionPerformed(new ActionEvent(addNew, ActionEvent.ACTION_PERFORMED, "Add New"));
        Method openChildWindowMethod = BankSystem.class.getDeclaredMethod("openChildWindow", String.class);
        openChildWindowMethod.setAccessible(true);
        boolean result = (boolean) openChildWindowMethod.invoke(bankSystem, "Create New Account");
        assertTrue(result, "New Account window should open.");
    }

    @Test
    void testActionPerformedInvalidObject() throws Exception {
        BankSystem bankSystem = new BankSystem();
        JButton randomButton = new JButton();
        bankSystem.actionPerformed(new ActionEvent(randomButton, ActionEvent.ACTION_PERFORMED, "Invalid Action"));
        Method openChildWindowMethod = BankSystem.class.getDeclaredMethod("openChildWindow", String.class);
        openChildWindowMethod.setAccessible(true);
        boolean result = (boolean) openChildWindowMethod.invoke(bankSystem, "Create New Account");
        assertFalse(result, "New Account window should not open.");
    }
}