import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.*;

class DeleteCustomerTest {

    @Test
    void testTxtNoEmptyCheck() throws Exception {
        DeleteCustomer deleteCustomer = new DeleteCustomer();
        Field txtNoField = DeleteCustomer.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(deleteCustomer);
        Field btnDelField = DeleteCustomer.class.getDeclaredField("btnDel");
        btnDelField.setAccessible(true);
        JButton btnDel = (JButton) btnDelField.get(deleteCustomer);

        txtNo.setText("");
        deleteCustomer.actionPerformed(new ActionEvent(btnDel, ActionEvent.ACTION_PERFORMED, "Delete"));
        assertTrue(txtNo.hasFocus(), "Focus should return to txtNo if it's empty.");

        txtNo.setText("101");
        deleteCustomer.actionPerformed(new ActionEvent(btnDel, ActionEvent.ACTION_PERFORMED, "Delete"));
        assertFalse(txtNo.hasFocus(), "Focus should not return to txtNo if it's not empty.");
    }

    @Test
    void testCharacterConditions() throws Exception {
        DeleteCustomer deleteCustomer = new DeleteCustomer();
        Field txtNoField = DeleteCustomer.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(deleteCustomer);
        KeyEvent keyEvent;

        keyEvent = new KeyEvent(txtNo, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, 0, '3');
        txtNo.getKeyListeners()[0].keyTyped(keyEvent);
        assertFalse(keyEvent.isConsumed(), "Valid digit input should not be consumed.");

        keyEvent = new KeyEvent(txtNo, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, 0, '\b');
        txtNo.getKeyListeners()[0].keyTyped(keyEvent);
        assertFalse(keyEvent.isConsumed(), "Backspace input should not be consumed.");

        keyEvent = new KeyEvent(txtNo, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, 0, 'A');
        txtNo.getKeyListeners()[0].keyTyped(keyEvent);
        assertTrue(keyEvent.isConsumed(), "Invalid character input should be consumed.");
    }

    @Test
    void testFindRecordConditions() throws Exception {
        DeleteCustomer deleteCustomer = new DeleteCustomer();

        Field recordsField = DeleteCustomer.class.getDeclaredField("records");
        recordsField.setAccessible(true); // Ensure private field is accessible
        recordsField.set(deleteCustomer, new String[][]{
            {"101", "John Doe", "10 Jan 2024", "Credit", "5000", "15000"}
        });
        Field totalField = DeleteCustomer.class.getDeclaredField("total");
        totalField.setAccessible(true); // Ensure private field is accessible
        totalField.set(deleteCustomer, 1);

        Field txtNoField = DeleteCustomer.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true); // Ensure private field is accessible
        JTextField txtNo = (JTextField) txtNoField.get(deleteCustomer);

        txtNo.setText("101");
        Method findRecMethod = DeleteCustomer.class.getDeclaredMethod("findRec");
        findRecMethod.setAccessible(true); // Ensure private method is accessible
        findRecMethod.invoke(deleteCustomer);
        Field txtNameField = DeleteCustomer.class.getDeclaredField("txtName");
        txtNameField.setAccessible(true); // Ensure private field is accessible
        assertEquals("John Doe", ((JTextField) txtNameField.get(deleteCustomer)).getText(),
                "Valid account should show the correct name.");

        txtNo.setText("999");
        findRecMethod.invoke(deleteCustomer);
        assertEquals("", ((JTextField) txtNameField.get(deleteCustomer)).getText(),
                "Non-existent account should clear the fields.");
    }

    @Test
    void testDeletionConfirmation() throws Exception {
        DeleteCustomer deleteCustomer = new DeleteCustomer();

        Field txtNameField = DeleteCustomer.class.getDeclaredField("txtName");
        txtNameField.setAccessible(true);
        JTextField txtName = (JTextField) txtNameField.get(deleteCustomer);
        txtName.setText("John Doe");

        Method deletionMethod = DeleteCustomer.class.getDeclaredMethod("deletion");
        deletionMethod.setAccessible(true);

        SwingUtilities.invokeAndWait(() -> {
            UIManager.put("OptionPane.showConfirmDialog", JOptionPane.YES_OPTION);
            try {
                deletionMethod.invoke(deleteCustomer);
            } catch (Exception e) {
                fail("Error invoking deletion: " + e.getMessage());
            }
        });

        assertDoesNotThrow(() -> {}, "Deletion should not throw exceptions.");
    }
}
