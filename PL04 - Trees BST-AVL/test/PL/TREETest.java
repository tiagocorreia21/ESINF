package PL;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TREETest {
    Integer[] arr = {20,15,10,13,8,17,40,50,30,7};
    int[] nnodes= {1,2,4,2,1};
    Integer[] inorderT= {7,8,10,13,15,17,20,30,40,50};
    Integer[] posorderT= {7,8,13,10,17,15,30,50,40,20};
    Integer[] bstAscDes = {7, 8, 10, 13, 15, 17, 20, 50, 40, 30};
    TREE<Integer> instance;
        
    public TREETest() {
    }
    
    @Before
    public void setUp(){
        instance = new TREE();
        for(int i :arr)
            instance.insert(i);        
    }
    
    
    /**
     * Test of path method, of class TREE.
     */
    @Test
    public void testpath() {
        System.out.println("path");
        Integer[] expectedResult = {20, 15, 10, 8, 7};
        assertArrayEquals(expectedResult, instance.path(7).toArray());
        Integer[] expectedResult2 = {20, 15, 10, 13};
        assertArrayEquals(expectedResult2, instance.path(13).toArray());
        assertNull(instance.path(80));
        assertNull(instance.path(45));
    }

    /**
     * Test of leafs method, of class TREE.
     */
    @Test
    public void testleafs() {
        System.out.println("leafs");
        Set<Integer> expectedResult = new HashSet<>(Arrays.asList(7, 13, 17, 30, 50));
        assertEquals(expectedResult, instance.leafs());
    }

    /**
     * Test of range method, of class TREE.
     */
    @Test
    public void testrange() {
        System.out.println("range");
        Integer[] expectedResult = {7, 50};
        assertArrayEquals(expectedResult, instance.range());
    }
    
}
