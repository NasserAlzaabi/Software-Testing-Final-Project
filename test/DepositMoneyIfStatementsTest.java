import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class DepositMoneyIfStatementsTest {

     @Test
    void testPopulateArrayWithEmptyFile() throws Exception {
        BankSystem bankSystem = new BankSystem();

        File emptyFile = new File("emptyBank.dat");
        try {
            if (emptyFile.createNewFile()) {
                Method populateArrayMethod = BankSystem.class.getDeclaredMethod("populateArray");
                populateArrayMethod.setAccessible(true);
                boolean result = (boolean) populateArrayMethod.invoke(bankSystem);

                assertFalse(result, "populateArray should return false for an empty file.");

                Field totalField = BankSystem.class.getDeclaredField("total");
                totalField.setAccessible(true);
                int total = (int) totalField.get(bankSystem);
                assertEquals(0, total, "Total records should be 0 for an empty file.");
            }
        } catch (IOException e) {
            fail("Failed to create or access the test file: " + e.getMessage());
        } finally {
            emptyFile.delete();
        }
    }

    @Test
    void testFindRecordExists() throws Exception {
        BankSystem bankSystem = new BankSystem();

        Field recordsField = BankSystem.class.getDeclaredField("records");
        recordsField.setAccessible(true);
        recordsField.set(bankSystem, new String[][]{
            {"101", "John Doe", "10 Jan 2024", "Credit", "5000", "15000"},
            {"102", "Jane Doe", "15 Jan 2024", "Debit", "3000", "12000"}
        });

        Field totalField = BankSystem.class.getDeclaredField("total");
        totalField.setAccessible(true);
        totalField.set(bankSystem, 2);

        Method findRecMethod = BankSystem.class.getDeclaredMethod("findRec", String.class);
        findRecMethod.setAccessible(true);

        assertDoesNotThrow(() -> findRecMethod.invoke(bankSystem, "101"), 
            "Finding an existing record should not throw exceptions.");
    }

    @Test
    void testFindRecordDoesNotExist() throws Exception {
        BankSystem bankSystem = new BankSystem();

        Field recordsField = BankSystem.class.getDeclaredField("records");
        recordsField.setAccessible(true);
        recordsField.set(bankSystem, new String[][]{
            {"101", "John Doe", "10 Jan 2024", "Credit", "5000", "15000"},
            {"102", "Jane Doe", "15 Jan 2024", "Debit", "3000", "12000"}
        });

        Field totalField = BankSystem.class.getDeclaredField("total");
        totalField.setAccessible(true);
        totalField.set(bankSystem, 2);

        Method findRecMethod = BankSystem.class.getDeclaredMethod("findRec", String.class);
        findRecMethod.setAccessible(true);

        assertDoesNotThrow(() -> findRecMethod.invoke(bankSystem, "999"), 
            "Finding a non-existent record should handle gracefully without throwing exceptions.");
    }

    @Test
    void testOpenChildWindow() throws Exception {
        BankSystem bankSystem = new BankSystem();

        Method openChildWindowMethod = BankSystem.class.getDeclaredMethod("openChildWindow", String.class);
        openChildWindowMethod.setAccessible(true);

        assertDoesNotThrow(() -> openChildWindowMethod.invoke(bankSystem, "Test Window"),
            "Opening a new child window should not throw exceptions.");

        boolean result = (boolean) openChildWindowMethod.invoke(bankSystem, "Nonexistent Window");
        assertFalse(result, "openChildWindow should return false for nonexistent windows.");
    }

    @Test
    void testMakeRecordPrint() throws Exception {
        BankSystem bankSystem = new BankSystem();

        Field recordsField = BankSystem.class.getDeclaredField("records");
        recordsField.setAccessible(true);
        recordsField.set(bankSystem, new String[][]{
            {"101", "John Doe", "10 Jan 2024", "Credit", "5000", "15000"}
        });

        Method makeRecordPrintMethod = BankSystem.class.getDeclaredMethod("makeRecordPrint", int.class);
        makeRecordPrintMethod.setAccessible(true);

        String formattedRecord = (String) makeRecordPrintMethod.invoke(bankSystem, 0);
        assertTrue(formattedRecord.contains("John Doe"), "Formatted record should contain customer name.");
        assertTrue(formattedRecord.contains("15000"), "Formatted record should contain account balance.");
    }
}
