package edu.umn.cs.csci3081w.project.model;

import java.util.Arrays;
import java.util.List;

/**
 * A strategy class that creates buses for nighttime simulation.
 */
public class NightBusStrategy implements BusStrategy {
  private static int busCount = 0;
  private final List<String> busSequence = Arrays.asList("small", "small", "small", "large");

  /**
   * Creates a large or small bus based on the pre-defined sequence.
   * @param id       bus identifier
   * @param line     a wrapper class that has routes
   * @param speed    speed of bus
   * @return  created bus
   */
  public Bus createBus(int id, Line line, double speed) {
    Bus newBus;
    if (busSequence.get(busCount % (busSequence.size())).equals("small")) {
      newBus = new SmallBus(id, line, speed);
      busCount++;
    } else {
      newBus = new LargeBus(id, line, speed);
      busCount++;
    }
    return newBus;
  }

  /**
   * Resets the number of buses that were created to 0.
   */
  public void resetCount() {
    busCount = 0;
  }

  /**
   * Decrements the number of buses by 1.
   */
  public void decrementCount() {
    busCount--;
  }
}
