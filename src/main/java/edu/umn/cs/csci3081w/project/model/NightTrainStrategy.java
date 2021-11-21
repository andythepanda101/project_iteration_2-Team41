package edu.umn.cs.csci3081w.project.model;

import java.util.Arrays;
import java.util.List;

/**
 * A strategy class that creates trains for nighttime simulation.
 */
public class NightTrainStrategy implements TrainStrategy {
  private static int trainCount = 0;
  private final List<String> trainSequence = Arrays.asList("electric", "diesel");

  /**
   * Creates an electric or diesel train based on the pre-defined sequence.
   * @param id       train identifier
   * @param line     a wrapper class that has routes
   * @param speed    speed of train
   * @return  created train
   */
  public Train createTrain(int id, Line line, double speed) {
    Train newTrain;
    if (trainSequence.get(trainCount % (trainSequence.size())).equals("electric")) {
      newTrain = new ElectricTrain(id, line, speed);
      trainCount++;
    } else {
      newTrain = new DieselTrain(id, line, speed);
      trainCount++;
    }
    return newTrain;
  }

  /**
   * Resets the number of trains that were created to 0.
   */
  public void resetCount() {
    trainCount = 0;
  }

  /**
   * Decrements the number of trains by 1.
   */
  public void decrementCount() {
    trainCount--;
  }
}
