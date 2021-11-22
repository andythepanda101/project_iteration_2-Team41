package edu.umn.cs.csci3081w.project.model;

/**
 * A factory class that creates trains based on the selected strategy.
 */
public class TrainFactory extends VehicleFactory {
  private TrainStrategy trainType;

  /**
   * Creates a train based on the strategy.
   * @param id  train identifier
   * @param line route of in/out bound
   * @param speed speed of train
   * @return created train
   */
  public Train createVehicle(int id, Line line, double speed) {
    Train newTrain = trainType.createTrain(id, line, speed);
    return newTrain;
  }

  public void setTrainStrategy(TrainStrategy description) {
    trainType = description;
  }

  public TrainStrategy getStrategy() {
    return trainType;
  }
}


