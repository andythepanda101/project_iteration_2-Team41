package edu.umn.cs.csci3081w.project.webserver;

import java.util.List;

import edu.umn.cs.csci3081w.project.model.*;
import com.google.gson.JsonObject;

public class RegisterVehicleCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  public RegisterVehicleCommand(VisualTransitSimulator simulator) { this.simulator = simulator; }

  @Override
  public void execute(WebServerSession session, JsonObject command) {
    int targetID = command.get("id").getAsInt();
    for (int i = 0; i < simulator.getActiveVehicles().size(); i++) {
      if (simulator.getActiveVehicles().get(i).getId() == targetID) {
        JsonObject outCommand = new JsonObject();
        outCommand.addProperty("command", "observedVehicle");
        Position currPos = simulator.getActiveVehicles().get(i).getPosition();
        String information = simulator.getActiveVehicles().get(i).getId() + "\n"
                             + "-----------------------------\n" +
                                "* Type: (" + Double.toString(currPos.getLongitude()) + ", "
                                            + Double.toString(currPos.getLatitude()) + ")\n"
                              + "* Passengers: " + simulator.getActiveVehicles().get(i).getPassengers().size() + "\n"
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
