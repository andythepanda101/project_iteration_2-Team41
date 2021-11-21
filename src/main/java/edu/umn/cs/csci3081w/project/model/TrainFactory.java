package edu.umn.cs.csci3081w.project.model;

public class TrainFactory extends VehicleFactory {
  private TrainStrategy trainType;

  public void setTrainStrategy(TrainStrategy description) {
    trainType = description;
  }

  public Train createVehicle(int id, Line line, double speed) {
    Train newTrain = trainType.createTrain(id, line, speed);
    return newTrain;
  }

  public TrainStrategy getStrategy() { return trainType; }
}


