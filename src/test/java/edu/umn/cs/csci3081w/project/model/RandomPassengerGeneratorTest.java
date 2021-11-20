package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RandomPassengerGeneratorTest {

  RandomPassengerGenerator testRandomPassengerGenerator;
  List<Stop> testStops = new ArrayList<Stop>();
  List<Double> testProbabilities = new ArrayList<Double>();

  /**
   * Setup by creating stops and probabilities for testing RandomPassengerGenerator
   */
  @BeforeEach
  public void setup(){
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    Stop stop3 = new Stop(2, "test stop 3", new Position(-93.226632, 44.975392));
    testStops.add(stop1);
    testStops.add(stop2);
    testStops.add(stop3);
    testProbabilities.add(0.1);
    testProbabilities.add(0.2);
    testProbabilities.add(0.3);
    testRandomPassengerGenerator = new RandomPassengerGenerator(testStops, testProbabilities);
  }

  /**
   * Testing the state after using the constructor
   */
  @Test
  public void testConstructorNormal(){
    assertEquals(testStops, testRandomPassengerGenerator.getStops());
    assertEquals(testProbabilities, testRandomPassengerGenerator.getProbabilities());

  }

  /**
   * Tests that RandomPassengerGenerator's generate() method is working
   * correctly generating random passengers, to test needs for DETERMINISTIC = true,
   * and we set DETERMINISTIC_VALUE = 0.0 for our testing purposes to
   * guarantee passenger generation at a stop
   */
  @Test
  public void testGenerate(){
  assertEquals(9, testRandomPassengerGenerator.generatePassengers());
  assertEquals(4, testRandomPassengerGenerator.getStops().get(0).getPassengers().size());
  assertEquals(5, testRandomPassengerGenerator.getStops().get(1).getPassengers().size());
  assertEquals(0, testRandomPassengerGenerator.getStops().get(2).getPassengers().size());
  }
}
