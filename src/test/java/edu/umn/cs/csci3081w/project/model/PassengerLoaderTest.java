package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassengerLoaderTest {

  PassengerLoader testPassengerLoader;
  Passenger testPassenger1;
  Passenger testPassenger2;
  Passenger testPassenger3;
  ArrayList<Passenger> testPassengerList;

  /**
   * Setups test Passengers, test PassengerLoader, and a test PassengerList.
   */
  @BeforeEach
  public void setup() {
    testPassenger1 = new Passenger(420, "Joe");
    testPassenger2 = new Passenger(1100, "Bob");
    testPassenger3 = new Passenger(4, "Tom");
    testPassengerLoader = new PassengerLoader();
    testPassengerList = new ArrayList<Passenger>();
  }

  /**
   * Tests PassengerLoader's LoadPassenger method to ensure it loads Passengers into a PassengerList
   * correctly, and not when the list is full.
   */
  @Test
  public void testLoadPassenger() {
    int r1;
    int r2;
    int r3;
    r1 = testPassengerLoader.loadPassenger(testPassenger1, 2, testPassengerList);
    r2 = testPassengerLoader.loadPassenger(testPassenger2, 2, testPassengerList);
    r3 = testPassengerLoader.loadPassenger(testPassenger3, 2, testPassengerList);
    assertEquals(1, r1);
    assertEquals(1, r2);
    assertEquals(0, r3);
    assertEquals(testPassenger1, testPassengerList.get(0));
    assertEquals(testPassenger2, testPassengerList.get(1));
    assertThrows(IndexOutOfBoundsException.class, () -> testPassengerList.get(3));
    assertEquals(2, testPassengerList.size());
  }
}
