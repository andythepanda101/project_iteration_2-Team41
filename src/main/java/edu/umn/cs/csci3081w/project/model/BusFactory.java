package edu.umn.cs.csci3081w.project.model;

/**
 * A factory class that creates buses based on the selected strategy.
 */
public class BusFactory extends VehicleFactory {
  private BusStrategy busType;

  /**
   * Creates a bus based on the strategy.
   * @param id  bus identifier
   * @param line route of in/out bound
   * @param speed speed of bus
   * @return created bus
   */
  public Bus createVehicle(int id, Line line, double speed) {
    Bus newBus = busType.createBus(id, line, speed);
    return newBus;
  }

  public void setBusStrategy(BusStrategy description) {
    busType = description;
  }

  public BusStrategy getStrategy() {
    return busType;
  }
}
