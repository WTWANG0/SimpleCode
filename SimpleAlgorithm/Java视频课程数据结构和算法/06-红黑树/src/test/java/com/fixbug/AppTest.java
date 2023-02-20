package com.fixbug;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.PriorityQueue;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        RBTree<Integer> rb = new RBTree<>();
        for (int i = 0; i < 10; i++) {
            rb.insert(i+1);
        }

        rb.remove(9);
        rb.remove(5);
        System.out.println();
    }
}