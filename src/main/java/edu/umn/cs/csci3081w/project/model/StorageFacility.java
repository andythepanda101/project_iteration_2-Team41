package edu.umn.cs.csci3081w.project.model;

/**
 * A class that represents StorageFacility in the simulation.
 */
public class StorageFacility {
  private int smallBusesNum;
  private int largeBusesNum;
  private int electricTrainsNum;
  private int dieselTrainsNum;

  /**
   * Default constructor for storage facility.
   * Sets all variables to 0 if no information available.
   */
  public StorageFacility() {
    smallBusesNum = 0;
    largeBusesNum = 0;
    electricTrainsNum = 0;
    dieselTrainsNum = 0;
  }

  /**
   * Constructor for storage facility.
   * @param smallBusesNum  number of small buses available
   * @param largeBusesNum number of large buses available
   * @param electricTrainsNum number of electric trains available
   * @param dieselTrainsNum number of diesel trains available
   */
  public StorageFacility(
      int smallBusesNum, int largeBusesNum, int electricTrainsNum, int dieselTrainsNum) {
    this.smallBusesNum = smallBusesNum;
    this.largeBusesNum = largeBusesNum;
    this.electricTrainsNum = electricTrainsNum;
    this.dieselTrainsNum = dieselTrainsNum;
  }

  public int getSmallBusesNum() {
    return smallBusesNum;
  }

  public int getLargeBusesNum() {
    return largeBusesNum;
  }

  public int getElectricTrainsNum() {
    return electricTrainsNum;
  }

  public int getDieselTrainsNum() {
    return dieselTrainsNum;
  }

  public void setSmallBusesNum(int busesNum) {
    this.smallBusesNum = busesNum;
  }

  public void setLargeBusesNum(int busesNum) {
    this.largeBusesNum = busesNum;
  }

  public void setElectricTrainsNum(int trainsNum) {
    this.electricTrainsNum = trainsNum;
  }

  public void setDieselTrainsNum(int trainsNum) {
    this.dieselTrainsNum = trainsNum;
  }

  /**
   * Decrements number of small buses in the storage facility.
   */
  public void decrementSmallBusesNum() {
    smallBusesNum--;
  }

  /**
   * Decrements number of large buses in the storage facility.
   */
  public void decrementLargeBusesNum() {
    largeBusesNum--;
  }

  /**
   * Decrements number of electric trains in the storage facility.
   */
  public void decrementElectricTrainsNum() {
    electricTrainsNum--;
  }

  /**
   * Decrements number of diesel trains in the storage facility.
   */
  public void decrementDieselTrainsNum() {
    dieselTrainsNum--;
  }

  /**
   * Increments number of small buses in the storage facility.
   */
  public void incrementSmallBusesNum() {
    smallBusesNum++;
  }

  /**
   * Increments number of large buses in the storage facility.
   */
  public void incrementLargeBusesNum() {
    largeBusesNum++;
  }

  /**
   * Increments number of electric trains in the storage facility.
   */
  public void incrementElectricTrainsNum() {
    electricTrainsNum++;
  }

  /**
   * Increments number of diesel trains in the storage facility.
   */
  public void incrementDieselTrainsNum() {
    dieselTrainsNum++;
  }
}
