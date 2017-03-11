package iann91.uw.tacoma.edu.myfridge;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Null;

import iann91.uw.tacoma.edu.myfridge.item.Item;

import static org.junit.Assert.*;

/**
 * Created by imnic on 3/10/2017.
 */

public class ItemTest {

//    private Item mCorrectItem
//
//    @Before
//    public void setup() {
//        mCorrectItem = new Item("Spinach", "2 lb", "2", "Vegetables");
//
//    }

    @Test
    public void testItemConstructor() {
        assertNotNull(new Item("Spinach", "2 lb", "2", "Vegetables"));
    }

    @Test
    public void testItemConstructorOnNullItemName() {
        try {
            new Item(null, "2 lb", "2", "Vegetables");
        } catch(NullPointerException e) {
            e.toString();
        }
    }

    @Test
    public void testItemConstructorOnNullQuantity() {
        try {
            new Item("Spinach", null, "2", "Vegetables");
        } catch(NullPointerException e) {
            e.toString();
        }
    }

    @Test
    public void testItemConstructorOnNullId() {
        try {
            new Item("Spinach", null, "2", "Vegetables");
        } catch(NullPointerException e) {
            e.toString();
        }
    }

    @Test
    public void testItemConstructorOnNullItemType() {
        try {
            new Item("Spinach", "2 lb", "2", null);
        } catch(NullPointerException e) {
            e.toString();
        }
    }

    @Test
    public void testItemConstructorOnItemNameLengthLessThanThree() {
        try {
            new Item("Sp", "2 lb", "2", "Vegetables");
        } catch(IllegalArgumentException e) {
            e.toString();
        }
    }

    @Test
    public void testItemConstructorOnQuantityLengthLessThanOne() {
        try {
            new Item("Spinach", "", "2", "Vegetables");
        } catch(IllegalArgumentException e) {
            e.toString();
        }
    }


}
