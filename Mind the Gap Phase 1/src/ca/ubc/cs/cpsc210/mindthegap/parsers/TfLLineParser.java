package ca.ubc.cs.cpsc210.mindthegap.parsers;

import ca.ubc.cs.cpsc210.mindthegap.model.*;
import ca.ubc.cs.cpsc210.mindthegap.parsers.exception.TfLLineDataMissingException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;

import javax.json.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A parser for the data returned by TFL line route query
 */
public class TfLLineParser extends TfLAbstractParser {

    /**
     * Parse line from JSON response produced by TfL.
     *
     * @param lmd              line meta-data
     * @return                 line parsed from TfL data
     * @throws JsonException   when JSON data does not have expected format
     * @throws TfLLineDataMissingException when
     * <ul>
     *  <li> JSON data is missing lineName, lineId or stopPointSequences elements </li>
     *  <li> or, for a given sequence: </li>
     *    <ul>
     *      <li> the stopPoint array is missing, or </li>
     *      <li> all station elements are missing one of name, lat, lon or stationId elements </li>
     *    </ul>
     * </ul>
     */
    public static Line parseLine(LineResourceData lmd, String jsonResponse)
            throws JsonException, TfLLineDataMissingException {
        JsonObject rootJSON = null;

        JsonReader reader = Json.createReader(new StringReader(jsonResponse));

        try {
            rootJSON = reader.readObject();
        } catch (Exception e) {
            throw new JsonException("Json file does not have expected format");
        }


        String lineName = null;
        String lineId = null;
        Line line = null;

        try {
            lineName = rootJSON.getString("lineName");
            lineId = rootJSON.getString("lineId");
            line = new Line(lmd, lineId, lineName);
        } catch (Exception e) {
            throw new TfLLineDataMissingException("Missing lineName or lineId");
        }

        JsonArray rawBranches = rootJSON.getJsonArray("lineStrings");
        addBranches(rawBranches, line);


        JsonArray stopPointSequences = null;
        stopPointSequences = rootJSON.getJsonArray("stopPointSequences");

        if (stopPointSequences == null) {
            throw new TfLLineDataMissingException("Missing stopPointSequences");
        }

        for (int i = 0; i < stopPointSequences.size(); i++) {
            JsonArray stopPoint = null;

            stopPoint = stopPointSequences.getJsonObject(i).getJsonArray("stopPoint");

            if (stopPoint.isEmpty()) {
                throw new TfLLineDataMissingException("Missing stopPoint array.");
            }

            createStations(stopPoint, line);

        }

        if (line.getStations().isEmpty()) {
            throw new TfLLineDataMissingException("All stations missing data.");
        }

        StationManager.getInstance().addStationsOnLine(line);

        // NOTE: you must complete the rest of the implementation of this method
        return line;
    }

    public static Station createStation(String id, String name, String latString, String lonString) {
        Station station = null;
        if (StationManager.getInstance().getStationWithId(id) != null) {
            station = StationManager.getInstance().getStationWithId(id);
            return station;
        }
        else if (id != null && name != null && latString != null && lonString != null) {
            name = TfLAbstractParser.parseName(name);
            Double lat = Double.parseDouble(latString);
            Double lon = Double.parseDouble(lonString);
            LatLon locn = new LatLon(lat, lon);

            station = new Station(id, name, locn);
        }

        return station;
    }

    public static void createStations(JsonArray stopPoint, Line line) throws TfLLineDataMissingException {
        for (int x = 0; x < stopPoint.size(); x++) {
            String id = null;
            String name = null;
            String latString = null;
            String lonString = null;

            JsonObject stopPoint2 = stopPoint.getJsonObject(x);

            try {
                id = stopPoint2.getString("stationId");
                name = stopPoint2.getString("name");
                latString = stopPoint2.get("lat").toString();
                lonString = stopPoint2.get("lon").toString();
            } catch (Exception e) {
                break;
            }

            Station station = createStation(id, name, latString, lonString);

            if (station != null && !line.getStations().contains(station)) {
                line.addStation(station);
            }
        }
    }

    public static void addBranches(JsonArray rawBranches, Line line) {
        ArrayList<String> branches = new ArrayList<>();

        for (int i = 0; i < rawBranches.size(); i++) {
            branches.add(rawBranches.getString(i));
        }

        for (String nextString : branches) {
            Branch newBranch = new Branch(nextString);
            line.addBranch(newBranch);
        }
    }
}
