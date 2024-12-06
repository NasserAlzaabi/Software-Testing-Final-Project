import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.*;

class ViewOnePathTest {

    private ViewOne viewOne;
    private JButton btnFirst, btnBack, btnNext, btnLast;

    @BeforeEach
    void setUp() throws Exception {
        viewOne = new ViewOne();
        Field btnFirstField = ViewOne.class.getDeclaredField("btnFirst");
        btnFirstField.setAccessible(true);
        btnFirst = (JButton) btnFirstField.get(viewOne);

        Field btnBackField = ViewOne.class.getDeclaredField("btnBack");
        btnBackField.setAccessible(true);
        btnBack = (JButton) btnBackField.get(viewOne);
        Field btnNextField = ViewOne.class.getDeclaredField("btnNext");
        btnNextField.setAccessible(true);
        btnNext = (JButton) btnNextField.get(viewOne);
        Field btnLastField = ViewOne.class.getDeclaredField("btnLast");
        btnLastField.setAccessible(true);
        btnLast = (JButton) btnLastField.get(viewOne);
    }

    @Test
    void testFirstButtonNavigation() throws Exception {

        Field recCountField = ViewOne.class.getDeclaredField("recCount");
        recCountField.setAccessible(true);
        recCountField.set(viewOne, 5);
        btnFirst.doClick();
        assertEquals(0, recCountField.get(viewOne), "Clicking the First button should set recCount to 0.");
    }

    @Test
    void testBackButtonNavigation() throws Exception {

        Field recCountField = ViewOne.class.getDeclaredField("recCount");
        recCountField.setAccessible(true);
        recCountField.set(viewOne, 2);
        btnBack.doClick();
        // Verify that recCount decreases by 1
        assertEquals(1, recCountField.get(viewOne), "Clicking the Back button should decrease recCount by 1.");
    }

    @Test
    void testBackButtonOnFirstRecord() throws Exception {
        Field recCountField = ViewOne.class.getDeclaredField("recCount");
        recCountField.setAccessible(true);
        recCountField.set(viewOne, 0);
        btnBack.doClick();
        assertEquals(0, recCountField.get(viewOne), "Clicking the Back button on the first record should keep recCount at 0.");
    }

    @Test
    void testNextButtonNavigation() throws Exception {
        Field recCountField = ViewOne.class.getDeclaredField("recCount");
        recCountField.setAccessible(true);
        recCountField.set(viewOne, 2);
        btnNext.doClick();
        assertEquals(3, recCountField.get(viewOne), "Clicking the Next button should increase recCount by 1.");
    }

    @Test
    void testNextButtonOnLastRecord() throws Exception {
        Field recCountField = ViewOne.class.getDeclaredField("recCount");
        recCountField.setAccessible(true);
        recCountField.set(viewOne, 4);
        Field totalField = ViewOne.class.getDeclaredField("total");
        totalField.setAccessible(true);
        totalField.set(viewOne, 5);
        btnNext.doClick();
        // check if recCount stays at the last record when the Next button is clicked on the last record
        assertEquals(4, recCountField.get(viewOne), "Clicking the Next button on the last record should keep recCount at the last record.");
    }

    @Test
    void testLastButtonNavigation() throws Exception {
        Field totalField = ViewOne.class.getDeclaredField("total");
        totalField.setAccessible(true);
        totalField.set(viewOne, 5);
        btnLast.doClick();
        Field recCountField = ViewOne.class.getDeclaredField("recCount");
        recCountField.setAccessible(true);
        assertEquals(4, recCountField.get(viewOne), "Clicking the Last button should set recCount to the last record.");
    }

    @Test
    void testPopulateArrayWhenFileIsEmpty() throws Exception {
        Field rowsField = ViewOne.class.getDeclaredField("rows");
        rowsField.setAccessible(true);
        rowsField.set(viewOne, 0);
        Field totalField = ViewOne.class.getDeclaredField("total");
        totalField.setAccessible(true);
        totalField.set(viewOne, 0);
        // Simulate an empty file scenario
        viewOne.populateArray();
        // Check that total is still 0 using an empty file
        assertEquals(0, totalField.get(viewOne), "Total should be 0 when the records file is empty.");
    }

    @Test
    void testShowRecDisplaysCorrectData() throws Exception {

        Field recordsField = ViewOne.class.getDeclaredField("records");
        recordsField.setAccessible(true);
        recordsField.set(viewOne, new String[][]{{"12345", "John Doe", "March", "1", "2000", "5000"}});

        Field recCountField = ViewOne.class.getDeclaredField("recCount");
        recCountField.setAccessible(true);
        recCountField.set(viewOne, 0);
        viewOne.showRec(0);
        Field txtNoField = ViewOne.class.getDeclaredField("txtNo");
        txtNoField.setAccessible(true);
        JTextField txtNo = (JTextField) txtNoField.get(viewOne);
        assertEquals("12345", txtNo.getText(), "Account number should be displayed correctly.");
    }
}
