import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class DepositMoneyTest {
    @Test
    void testBtnSaveWithEmptyTxtNo() throws Exception {
        DepositMoney depositMoney = new DepositMoney();
        Field btnSaveField = DepositMoney.class.getDeclaredField("btnSave");
        btnSaveField.setAccessible(true);
        JButton btnSave = (JButton) btnSaveField.get(depositMoney);
        Field txtNoField = DepositMoney.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(depositMoney);

        txtNo.setText("");

        depositMoney.actionPerformed(new ActionEvent(btnSave, ActionEvent.ACTION_PERFORMED, "Save"));

        assertTrue(txtNo.hasFocus(), "Focus should return to txtNo if it's empty.");
    }

    @Test
    void testBtnSaveWithEmptyTxtDeposit() throws Exception {
        DepositMoney depositMoney = new DepositMoney();
        Field btnSaveField = DepositMoney.class.getDeclaredField("btnSave");
        btnSaveField.setAccessible(true);
        JButton btnSave = (JButton) btnSaveField.get(depositMoney);
        Field txtNoField = DepositMoney.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(depositMoney);
        Field txtDepositField = DepositMoney.class.getDeclaredField("txtDeposit");
        txtDepositField.setAccessible(true);
        JTextField txtDeposit = (JTextField) txtDepositField.get(depositMoney);
        txtNo.setText("12345");
        txtDeposit.setText("");
        depositMoney.actionPerformed(new ActionEvent(btnSave, ActionEvent.ACTION_PERFORMED, "Save"));

        assertTrue(txtDeposit.hasFocus(), "Focus should return to txtDeposit if it's empty.");
    }

    @Test
    void testBtnSaveWithValidData() throws Exception {
        DepositMoney depositMoney = new DepositMoney();
        Field btnSaveField = DepositMoney.class.getDeclaredField("btnSave");
        btnSaveField.setAccessible(true);
        JButton btnSave = (JButton) btnSaveField.get(depositMoney);
        Field txtNoField = DepositMoney.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(depositMoney);
        Field txtDepositField = DepositMoney.class.getDeclaredField("txtDeposit");
        txtDepositField.setAccessible(true);
        JTextField txtDeposit = (JTextField) txtDepositField.get(depositMoney);
        txtNo.setText("12345");
        txtDeposit.setText("5000");
    
        Method editRecMethod = DepositMoney.class.getDeclaredMethod("editRec");
        editRecMethod.setAccessible(true);
    
        depositMoney.actionPerformed(new ActionEvent(btnSave, ActionEvent.ACTION_PERFORMED, "Save"));
    
        // Assert that editRec is called (editRec modifies records, can verify changes)
        // For simplicity, we assume editRec updates the records array.
        Field recordsField = DepositMoney.class.getDeclaredField("records");
        recordsField.setAccessible(true);
        String[][] records = (String[][]) recordsField.get(depositMoney);
        assertNotNull(records[0][5], "Record balance should be updated after a valid deposit.");
    }

    @Test
    void testBtnCancel() throws Exception {
        DepositMoney depositMoney = new DepositMoney();
        Field btnCancelField = DepositMoney.class.getDeclaredField("btnCancel");
        btnCancelField.setAccessible(true);
        JButton btnCancel = (JButton) btnCancelField.get(depositMoney);
        depositMoney.actionPerformed(new ActionEvent(btnCancel, ActionEvent.ACTION_PERFORMED, "Cancel"));

        Field txtNoField = DepositMoney.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(depositMoney);

        assertEquals("", txtNo.getText(), "txtNo should be cleared after cancel.");
        assertFalse(depositMoney.isVisible(), "Form should not be visible after cancel.");
    }
}