package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

/**
 * A class that obtains update commands for the simulation.
 */
public class UpdateCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Constructor for update commands.
   * @param simulator current simulation session
   */
  public UpdateCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Updates the state of the simulation.
   *
   * @param session current simulation session
   * @param command the update simulation command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    simulator.update();
  }

}
