package edu.umn.cs.csci3081w.project.model;

import java.util.Arrays;
import java.util.List;

public class DayTrainStrategy implements TrainStrategy {
  private static int trainCount = 0;
  private final List<String> trainSequence = Arrays.asList("electric", "electric", "electric", "diesel");

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

  public void resetCount() { trainCount = 0; }

  public void decrementCount() { trainCount--; }
}
