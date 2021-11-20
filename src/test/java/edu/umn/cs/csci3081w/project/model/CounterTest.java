package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CounterTest {

  Counter testCounter;

  /**
   * Setup a Counter for testing its methods.
   */
  @BeforeEach
  public void setup() {
    testCounter = new Counter();
    // routeIdCounter = 10; starting values
    // stopIdCounter = 100;
    // busIdCounter = 1000;
    // trainIdCounter = 2000;
    // lineIdCounter = 10000;
  }

  /**
   * Test the methods that return the value and increment the values AFTER returning
   * to see if they return the value then increment their values like expected.
   */
  @Test
  public void testGetAndIncrement() {
    assertEquals(10, testCounter.getRouteIdCounterAndIncrement());
    assertEquals(11, testCounter.getRouteIdCounterAndIncrement());
    assertEquals(100, testCounter.getStopIdCounterAndIncrement());
    assertEquals(101, testCounter.getStopIdCounterAndIncrement());
    assertEquals(1000, testCounter.getBusIdCounterAndIncrement());
    assertEquals(1001, testCounter.getBusIdCounterAndIncrement());
    assertEquals(2000, testCounter.getTrainIdCounterAndIncrement());
    assertEquals(2001, testCounter.getTrainIdCounterAndIncrement());
    assertEquals(10000, testCounter.getLineIdCounterAndIncrement());
    assertEquals(10001, testCounter.getLineIdCounterAndIncrement());
  }

}
