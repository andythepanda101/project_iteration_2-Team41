package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import java.util.List;

public class GetRoutesCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  public GetRoutesCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Retrieves routes information from the simulation.
   *
   * @param session current simulation session
   * @param command the get routes command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Line> lines = simulator.getLines();
    JsonObject data = new JsonObject();
    data.addProperty("command", "updateRoutes");
    JsonArray routesArray = new JsonArray();
    for (int i = 0; i < lines.size(); i++) {
      JsonObject r = new JsonObject();
      r.addProperty("id", lines.get(i).getId());
      JsonArray stopArray = new JsonArray();
      for (int j = 0; j < (lines.get(i).getOutboundRoute().getStops().size()); j++) {
        JsonObject stopOutStruct = new JsonObject();
        stopOutStruct.addProperty("id", lines.get(i).getOutboundRoute().getStops().get(j).getId());
        stopOutStruct.addProperty("numPeople",
            lines.get(i).getOutboundRoute().getStops().get(j).getPassengers().size());
        JsonObject jsonObjOut = new JsonObject();
        jsonObjOut.addProperty("longitude",
            lines.get(i).getOutboundRoute().getStops().get(j).getPosition().getLongitude());
        jsonObjOut.addProperty("latitude",
            lines.get(i).getOutboundRoute().getStops().get(j).getPosition().getLatitude());
        stopOutStruct.add("position", jsonObjOut);
        stopArray.add(stopOutStruct);
      }

      for (int j = 0; j < (lines.get(i).getInboundRoute().getStops().size()); j++) {
        JsonObject stopOutStruct = new JsonObject();
        stopOutStruct.addProperty("id", lines.get(i).getInboundRoute().getStops().get(j).getId());
        stopOutStruct.addProperty("numPeople",
            lines.get(i).getInboundRoute().getStops().get(j).getPassengers().size());
        JsonObject jsonObjOut = new JsonObject();
        jsonObjOut.addProperty("longitude",
            lines.get(i).getInboundRoute().getStops().get(j).getPosition().getLongitude());
        jsonObjOut.addProperty("latitude",
            lines.get(i).getInboundRoute().getStops().get(j).getPosition().getLatitude());
        stopOutStruct.add("position", jsonObjOut);
        stopArray.add(stopOutStruct);
      }
      r.add("stops", stopArray);
      routesArray.add(r);
    }
    data.add("routes", routesArray);
    session.sendJson(data);
  }
}

