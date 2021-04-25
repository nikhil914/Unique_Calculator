package com.nik.uniquecalculator.model;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class CalculateTests extends TestCase {

    Calculate calculate;

    @Before
    public void setup(){
        calculate = new Calculate();
    }
    @Test
    public void test1() {
        calculate = new Calculate();
        int total = calculate.calculateExpression("50+20/10");
        assertEquals("Calculate is not adding correctly", 7, total);
    }
    @Test
    public void test2() {
        calculate = new Calculate();
        int total = calculate.calculateExpression("50/20+5");
        assertEquals("Calculate is not adding correctly", 2, total);
    }
    @Test
    public void test3() {
        calculate = new Calculate();
        int total = calculate.calculateExpression("10/2-20");
        assertEquals("Calculate is not adding correctly", -15, total);
    }
    @Test
    public void test4() {
        calculate = new Calculate();
        int total = calculate.calculateExpression("10-2-3");
        assertEquals("Calculate is not adding correctly", 5, total);
    }

   @Test
    public void test5() {
        calculate = new Calculate();
        int total = calculate.calculateExpression("10/2/5");
        assertEquals("Calculate is not adding correctly", 1, total);
    }

   @Test
    public void test6() {
        calculate = new Calculate();
        int total = calculate.calculateExpression("10/2/4+1");
        assertEquals("Calculate is not adding correctly", 1, total);
    }


}