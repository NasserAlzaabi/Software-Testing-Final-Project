import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.*;


// Testing the new account class using path testing
class NewAccountTest {

    private NewAccount newAccount;
    private JButton btnSave, btnCancel;

    @BeforeEach
    void setUp() throws Exception {
        newAccount = new NewAccount();

        Field btnSaveField = NewAccount.class.getDeclaredField("btnSave");
        btnSaveField.setAccessible(true);
        btnSave = (JButton) btnSaveField.get(newAccount);

        Field btnCancelField = NewAccount.class.getDeclaredField("btnCancel");
        btnCancelField.setAccessible(true);
        btnCancel = (JButton) btnCancelField.get(newAccount);
    }

    @Test
    void testSaveWithEmptyAccountNumber() throws Exception {
        Field txtNoField = NewAccount.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(newAccount);

        txtNo.setText("");
        btnSave.doClick();
        assertTrue(txtNo.hasFocus(), "Focus should return to txtNo if it's empty.");
    }

    @Test
    void testSaveWithEmptyName() throws Exception {
        Field txtNoField = NewAccount.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(newAccount);

        Field txtNameField = NewAccount.class.getDeclaredField("txtName");
        txtNameField.setAccessible(true);
        JTextField txtName = (JTextField) txtNameField.get(newAccount);

        txtNo.setText("12345");
        txtName.setText("");

        btnSave.doClick();
        assertTrue(txtName.hasFocus(), "Focus should return to txtName if it's empty.");
    }

    @Test
    void testSaveWithEmptyDepositAmount() throws Exception {
        Field txtNoField = NewAccount.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(newAccount);
        Field txtNameField = NewAccount.class.getDeclaredField("txtName");
        txtNameField.setAccessible(true);
        JTextField txtName = (JTextField) txtNameField.get(newAccount);
        Field txtDepositField = NewAccount.class.getDeclaredField("txtDeposit");
        txtDepositField.setAccessible(true);
        JTextField txtDeposit = (JTextField) txtDepositField.get(newAccount);


        txtNo.setText("12345");
        txtName.setText("John Doe");
        txtDeposit.setText("");

        btnSave.doClick();
        assertTrue(txtDeposit.hasFocus(), "Focus should return to txtDeposit if it's empty.");
    }

    @Test
    void testSaveWithExistingAccountNumber() throws Exception {
        Field txtNoField = NewAccount.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(newAccount);
        Field recordsField = NewAccount.class.getDeclaredField("records");
        recordsField.setAccessible(true);
        recordsField.set(newAccount, new String[][]{{"12345", "John Doe", "March", "1", "2000", "5000"}});
        Field totalField = NewAccount.class.getDeclaredField("total");
        totalField.setAccessible(true);
        totalField.set(newAccount, 1);
        txtNo.setText("12345");
        btnSave.doClick();
        assertEquals("", txtNo.getText(), "txtNo should be cleared if account already exists.");
    }

    @Test
    void testSaveWithValidData() throws Exception {

        Field txtNoField = NewAccount.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(newAccount);
        Field txtNameField = NewAccount.class.getDeclaredField("txtName");
        txtNameField.setAccessible(true);
        JTextField txtName = (JTextField) txtNameField.get(newAccount);
        Field txtDepositField = NewAccount.class.getDeclaredField("txtDeposit");
        txtDepositField.setAccessible(true);
        JTextField txtDeposit = (JTextField) txtDepositField.get(newAccount);


        txtNo.setText("67890");
        txtName.setText("Jane Doe");
        txtDeposit.setText("2000");

        btnSave.doClick();
        assertEquals("", txtNo.getText(), "txtNo should be cleared after saving.");
        assertEquals("", txtName.getText(), "txtName should be cleared after saving.");
        assertEquals("", txtDeposit.getText(), "txtDeposit should be cleared after saving.");
    }

    @Test
    void testCancelButtonClearsFields() throws Exception {
        Field txtNoField = NewAccount.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(newAccount);
        Field txtNameField = NewAccount.class.getDeclaredField("txtName");
        txtNameField.setAccessible(true);
        JTextField txtName = (JTextField) txtNameField.get(newAccount);
        Field txtDepositField = NewAccount.class.getDeclaredField("txtDeposit");
        txtDepositField.setAccessible(true);
        JTextField txtDeposit = (JTextField) txtDepositField.get(newAccount);


        txtNo.setText("12345");
        txtName.setText("John Doe");
        txtDeposit.setText("1000");

        btnCancel.doClick();
        assertEquals("", txtNo.getText(), "txtNo should be cleared after cancel.");
        assertEquals("", txtName.getText(), "txtName should be cleared after cancel.");
        assertEquals("", txtDeposit.getText(), "txtDeposit should be cleared after cancel.");
    }
}