package group;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * CartesianTest : Test the Cartesian class for both calculation of cartesian product
 * and utility functions.
 * 
 * @author richard_sayad
 */
public class CartesianTest {
   
    /**
     * Test of getValue method, of class Cartesian.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        Cartesian instance = new Cartesian("a{b,c}d{e,f,g}hi");
        String expResult = "abdehi abdfhi abdghi acdehi acdfhi acdghi";
        String result = instance.getValue();
        assertTrue(expResult.equals(result));
        
        instance = new Cartesian("d{a,b{a,c}}jk");
        expResult = "dajk dbajk dbcjk";
        result = instance.getValue();
        assertTrue(expResult.equals(result));        
        
        instance = new Cartesian(null);
        expResult = null;
        result = instance.getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of pop static method, of class Cartesian.
     */
    @Test
    public void testPop() {
        System.out.println("pop");
        StringBuilder current = new StringBuilder("a{b,c}d{e,f,g}hi");
        String expResult = "a";
        String result = Cartesian.pop(current);
        assertTrue(expResult.equals(result));

        expResult = "{b,c}";
        result = Cartesian.pop(current);
        assertTrue(expResult.equals(result));
        
        expResult = "d";
        result = Cartesian.pop(current);
        assertTrue(expResult.equals(result));

        expResult = "{e,f,g}";
        result = Cartesian.pop(current);
        assertTrue(expResult.equals(result));

        expResult = "hi";
        result = Cartesian.pop(current);
        assertTrue(expResult.equals(result));

        expResult = "";
        result = Cartesian.pop(current);
        assertTrue(expResult.equals(result));
    }
}
