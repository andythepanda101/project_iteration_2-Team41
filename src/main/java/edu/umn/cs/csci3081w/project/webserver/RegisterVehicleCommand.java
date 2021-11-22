package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Position;
import edu.umn.cs.csci3081w.project.model.Train;
import java.util.List;

/**
 * A class to retrieve information from a vehicle. Used to show the
 * information of a vehicle when it is clicked.
 */
public class RegisterVehicleCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  public RegisterVehicleCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Creates a String to report the information of a vehicle that is clicked.
   *
   * @param session current simulation session
   * @param command A command that contains the ID of the vehicle whose
   *                information will be displayed
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    int targetID = command.get("id").getAsInt();
    for (int i = 0; i < simulator.getActiveVehicles().size(); i++) {
      if (simulator.getActiveVehicles().get(i).getId() == targetID) {
        JsonObject outCommand = new JsonObject();
        outCommand.addProperty("command", "observedVehicle");
        Position currPos = simulator.getActiveVehicles().get(i).getPosition();
        String vehicleType;
        if (simulator.getActiveVehicles().get(i) instanceof Train) {
          vehicleType = "TRAIN";
        } else {
          vehicleType = "BUS";
        }
        String information = simulator.getActiveVehicles().get(i).getId() + "\n"
                              + "-----------------------------\n"
                              + "* Type: " + vehicleType + "\n"
                              + "* Position: (" + Double.toString(currPos.getLongitude()) + ", "
                                                + Double.toString(currPos.getLatitude()) + ")\n"
                              + "* Passengers: "
                              + simulator.getActiveVehicles().get(i).getPassengers().size() + "\n"
                              + "* CO2: ";
        List<Integer> co2List = simulator.getActiveVehicles().get(i).getLast5CO2();
        for (int j = 0; j < co2List.size() - 1; j++) {
          information += co2List.get(j) + ", ";
        }
        information += co2List.get(co2List.size() - 1);

        outCommand.addProperty("text", information);
        session.sendJson(outCommand);
      }
    }
  }

}
