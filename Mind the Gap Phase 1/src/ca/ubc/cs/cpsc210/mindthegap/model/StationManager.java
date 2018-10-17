package ca.ubc.cs.cpsc210.mindthegap.model;

import ca.ubc.cs.cpsc210.mindthegap.model.exception.StationException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import ca.ubc.cs.cpsc210.mindthegap.util.SphericalGeometry;

import java.util.*;

import static ca.ubc.cs.cpsc210.mindthegap.util.SphericalGeometry.distanceBetween;

/**
 * Manages all tube stations on network.
 *
 * Singleton pattern applied to ensure only a single instance of this class
 * is globally accessible throughout application.
 */
public class StationManager implements Iterable<Station> {
    public static final int RADIUS = 10000;
    private static StationManager instance;
    private Set<Station> stations;

    private Station selectedStn;

    /**
     * Constructs station manager with empty set of stations and null as the selected station
     */
    private StationManager() {
        stations = new HashSet<Station>();
        selectedStn = null;
    }

    /**
     * Gets one and only instance of this class
     *
     * @return  instance of class
     */
    public static StationManager getInstance() {
        if(instance == null) {
            instance = new StationManager();
        }

        return instance;
    }

    public Station getSelected() {
        return selectedStn;
    }

    /**
     * Get station with given id or null if no such station is found in this manager
     *
     * @param id  the id of this station
     *
     * @return  station with given id or null if no such station is found
     */
    public Station getStationWithId(String id) {
        for (Station nextStation: stations) {
            if (nextStation.getID().equals(id)) {
                return nextStation;
            }
        }

        return null;
    }

    /**
     * Set the station selected by user
     *
     * @param selected   station selected by user
     * @throws StationException when station manager doesn't contain selected station
     */
    public void setSelected(Station selected) throws StationException {
        if (!stations.contains(selected)) {
            throw new StationException("Station does not exist.");
        } else {
            selectedStn = getStationWithId(selected.getID());
        }
    }

    /**
     * Clear selected station (selected station is null)
     */
    public void clearSelectedStation() {
        selectedStn = null;
    }

    /**
     * Add all stations on given line. Station added only if it is not already in the collection.
     *
     * @param line  the line from which stations are to be added
     */
    public void addStationsOnLine(Line line) {
        for (Station nextStation: line.getStations()) {
            if (!stations.contains(nextStation)) {
                stations.add(nextStation);
            }
        }
    }

    /**
     * Get number of stations managed
     *
     * @return  number of stations added to manager
     */
    public int getNumStations() {
        return stations.size();
    }

    /**
     * Remove all stations from station manager and
     * clear selected station.
     */
    public void clearStations() {
        stations.clear();
        clearSelectedStation();
    }


    /**
     * Find nearest station to given point.  Returns null if no station is closer than RADIUS metres. If
     * two or more stations are at an equal distance from the given point, any one of those stations is
     * returned.
     *
     * @param pt  point to which nearest station is sought
     * @return    station closest to pt but less than 10,000m away; null if no station is within RADIUS metres of pt
     */
    public Station findNearestTo(LatLon pt) {
        Station nearestStation = null;
        List<Station> stationsWithinRadius = new ArrayList<>();

        for (Station nextStation : stations) {
            if (SphericalGeometry.distanceBetween(nextStation.getLocn(), pt) < RADIUS) {
                stationsWithinRadius.add(nextStation);
            }
        }

        for (Station nextStation: stationsWithinRadius) {
            if (nearestStation == null) {
                nearestStation = nextStation;
            }
            else {
                if (SphericalGeometry.distanceBetween(nextStation.getLocn(), pt) <
                        SphericalGeometry.distanceBetween(nearestStation.getLocn(), pt)) {
                    nearestStation = nextStation;
                }
            }
        }

        return nearestStation;
    }

    @Override
    public Iterator<Station> iterator() {
        return stations.iterator();
    }
}
