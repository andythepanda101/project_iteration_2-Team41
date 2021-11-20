package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PositionTest {

  double testLongitude1;
  double testLatitude1;
  double testLongitude2;
  double testLatitude2;
  Position testPosition1;
  Position testPosition2;

  /**
   * Setup values for creating Positions in testing.
   */
  @BeforeEach
  public void setup() {
    testLongitude1 = 11.111111;
    testLatitude1 = 22.222222;
    testLongitude2 = 33.333333;
    testLatitude2 = 44.444444;
  }

  /**
   * Testing state after using constructor.
   */
  @Test
  public void testConstructorNormal() {
    testPosition1 = new Position(testLongitude1, testLatitude1);
    testPosition2 = new Position(testLongitude2, testLatitude2);
    assertEquals(testLongitude1, testPosition1.getLongitude());
    assertEquals(testLatitude1, testPosition1.getLatitude());
    assertEquals(testLongitude2, testPosition2.getLongitude());
    assertEquals(testLatitude2, testPosition2.getLatitude());
  }
}
