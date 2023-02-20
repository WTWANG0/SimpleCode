package com.fixbug;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testAVL()
    {
        AVL<Integer> avl = new AVL<>();
        for (int i = 0; i < 10; i++) {
            avl.insert(i+1);
        }

        System.out.println();
        avl.remove(9);
        System.out.println();
        avl.remove(10);
        System.out.println();
        avl.remove(3);
        System.out.println();
    }
}
