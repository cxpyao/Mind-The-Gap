package ca.ubc.cs.cpsc210.mindthegap.parsers;

import ca.ubc.cs.cpsc210.mindthegap.model.Arrival;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import ca.ubc.cs.cpsc210.mindthegap.model.exception.ArrivalException;
import ca.ubc.cs.cpsc210.mindthegap.parsers.exception.TfLArrivalsDataMissingException;

import javax.json.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.fail;

/**
 * A parser for the data returned by the TfL station arrivals query
 */
public class TfLArrivalsParser extends TfLAbstractParser {

    /**
     * Parse arrivals from JSON response produced by TfL query.  Parsed arrivals are
     * added to given station assuming that arrival is on a line that passes
     * through the station and that corresponding JSON object has all of:
     * timeToStation, platformName, lineID and one of destinationName or towards.  If
     * any of the aforementioned elements is missing, or if arrival is on a line
     * that does not pass through the station, the arrival is not added to the station.
     *
     * @param stn             station to which parsed arrivals are to be added
     * @param jsonResponse    the JSON response produced by TfL
     * @throws JsonException  when JSON response does not have expected format
     * @throws TfLArrivalsDataMissingException  when all arrivals are missing at least one of the following:
     *      <ul>
     *          <li>timeToStation</li>
     *          <li>platformName</li>
     *          <li>lineId</li>
     *          <li>destinationName and towards</li>
     *      </ul>
     *  or if all arrivals are on a line that does not run through given station.
     */
    public static void parseArrivals(Station stn, String jsonResponse)
            throws JsonException, TfLArrivalsDataMissingException {

        JsonReader reader = Json.createReader(new StringReader(jsonResponse));

        JsonArray arrivals = null;
        try {
            arrivals = reader.readArray();
        } catch (Exception e) {
            throw new JsonException("Json file does not have the expected format.");
        }

        for (int i = 0; i < arrivals.size(); i++) {
            int timeToStation = 0;
            String platformName = null;
            String lineId = null;
            String destinationName = null;
            String towards = null;

            timeToStation = arrivals.getJsonObject(i).getInt("timeToStation");
            platformName = arrivals.getJsonObject(i).getString("platformName");
            lineId = arrivals.getJsonObject(i).getString("lineId");
            destinationName = arrivals.getJsonObject(i).getString("destinationName");
            towards = arrivals.getJsonObject(i).getString("towards");

            if (stn.hasLine(new Line(LineResourceData.CENTRAL, lineId, "dummy line"))) {
                if (timeToStation != 0 && lineId != null && destinationName != null && platformName != null && towards != null) {
                    Arrival arrival = createArrival(timeToStation,platformName, destinationName);
                    try {
                        stn.addArrival(getLine(stn, lineId), arrival);
                    } catch (ArrivalException e) {
                        throw new TfLArrivalsDataMissingException("No arrivals!");
                    }
                }

                else {
                    return;
                }
            }

        }

        if (stn.getNumArrivalBoards() == 0) {
            throw new TfLArrivalsDataMissingException("No arrivals!");
        }
    }

        private static Arrival createArrival(int timeToStation, String platformName, String destinationName) {
            Arrival arrival = new Arrival(timeToStation, platformName, destinationName);

            return arrival;
        }

        private static Line getLine(Station stn, String lineId){
            for (Line next: stn.getLines()) {
                if (next.getId().equals(lineId)) {
                    return next;
                }
            }

            return null;
        }

        // NOTE: you must complete the rest of the implementation of this method
    }
