package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassengerUnloaderTest {

  // basic setup
  PassengerLoader testPassengerLoader;
  ArrayList<Passenger> testPassengerList;
  Passenger testPassenger1;
  Passenger testPassenger2;
  Passenger testPassenger3;

  // unloader stuff
  PassengerUnloader testPassengerUnloader;
  Stop testStop1;
  Stop testStop2;
  Stop testStop3;

  /**
   * Setups test Passengers, test PassengerUnloader and PassengerUnloader, a test PassengerList,
   * and creates test Stops and creates PassengerUnloader which is what this class is testing.
   */
  @BeforeEach
  public void setup() {
    testPassenger1 = new Passenger(420, "Joe");
    testPassenger2 = new Passenger(1100, "Bob");
    testPassenger3 = new Passenger(420, "Tom");
    testPassengerList = new ArrayList<Passenger>();
    testPassengerLoader = new PassengerLoader();
    testPassengerUnloader = new PassengerUnloader();

    testStop1 = new Stop(420, "fourtwenty", new Position(11.111111, 22.222222));
    testStop2 = new Stop(1100, "elevenhundred", new Position(33.333333, 44.444444));
    testStop3 = new Stop(9999, "ninenineninenine", new Position(55.555555, 66.666666));

    testPassengerLoader.loadPassenger(testPassenger1, 3, testPassengerList);
    testPassengerLoader.loadPassenger(testPassenger2, 3, testPassengerList);
    testPassengerLoader.loadPassenger(testPassenger3, 3, testPassengerList);
  }

  /**
   * Tests if the unloadPassengers() method is working correctly in a typical case.
   */
  @Test
  public void testUnloadPassengers() {
    int r1;
    int r2;
    int r3;
    int r4;
    r1 = testPassengerUnloader.unloadPassengers(testPassengerList, testStop3);
    r2 = testPassengerUnloader.unloadPassengers(testPassengerList, testStop1);
    r3 = testPassengerUnloader.unloadPassengers(testPassengerList, testStop2);
    r4 = testPassengerUnloader.unloadPassengers(testPassengerList, testStop3);
    assertEquals(0, r1);
    assertEquals(2, r2);
    assertEquals(1, r3);
    assertEquals(0, r4);
    assertEquals(0, testPassengerList.size());

  }


}
