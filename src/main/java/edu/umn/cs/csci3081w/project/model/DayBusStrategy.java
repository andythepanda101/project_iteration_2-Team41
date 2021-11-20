package edu.umn.cs.csci3081w.project.model;
import java.util.Arrays;
import java.util.List;

public class DayBusStrategy implements BusStrategy {
  private static int busCount = 0;
  private final List<String> busSequence = Arrays.asList("large", "large", "small");

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

  public void resetCount() { busCount = 0; }

  public void decrementCount() { busCount--; }
}