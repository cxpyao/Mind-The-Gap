package ca.ubc.cs.cpsc210.mindthegap.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents an estimated arrival with time to arrival in seconds,
 * name of destination and platform at which train arrives.  Platform
 * data is generally assumed to be of the form:
 *    "Travel Direction - Platform Name"
 * with an arbitrary number of spaces either side of "-" and at the
 * start and end of the string.  In some cases, the "-" could
 * be missing.
 */
public class Arrival implements Comparable<Arrival> {
    private int timeToStation;

    private String destination;
    private String platform;
    private String travelDirn;
    private String platformName;
    List<String> platformNameParts = new LinkedList<String>();

    /**
     * Constructs a new arrival with the given time to station (in seconds),
     * destination and platform.
     *
     * @param timeToStation  time until train arrives at station (in seconds)
     * @param destination    name of destination station
     * @param platform       platform at which train will arrive
     */
    public Arrival(int timeToStation, String destination, String platform) {
        this.timeToStation = timeToStation;
        this.destination = destination;
        this.platform = platform;
    }

    /**
     * Get direction of travel as indicated by platform prefix (part of platform prior to "-" with
     * leading and trailing whitespace trimmed).  If platform does not contain "-", returns
     * "Unknown direction".
     *
     * @return direction of travel
     */
    public String getTravelDirn() {
        if (platform.contains("-")) {
            platform.split("-");

            for (String retVal: platform.split("-")) {
                platformNameParts.add(retVal.trim());
            }

            travelDirn = platformNameParts.get(0);

            return travelDirn;
        }

        return "Unknown direction";
    }

    /**
     * Get platform name as indicated by platform suffix (part of platform after "-" with leading
     * and trailing whitespace trimmed). If platform does not contain "-", returns platform
     * (with leading and trailing whitespace trimmed).
     *
     * @return  platform name
     */
    public String getPlatformName() {
        if (platform.contains("-")) {
            platform.split("-");

            for (String retVal: platform.split("-")) {
                platformNameParts.add(retVal.trim());
            }

            platformName = platformNameParts.get(1);

            return platformName;
        }

        return platform.trim();
    }

    /**
     * Get time until train arrives at station rounded up to nearest minute.
     *
     * @return  time until train arrives at station in minutes
     */
    public int getTimeToStationInMins() {
        int timeInMins = (int) Math.ceil((double) timeToStation / 60);

        return timeInMins;
    }

    public String getDestination() {
        return destination;
    }

    public String getPlatform() {
        return platform;
    }

    /**
     * Order train arrivals by time until train arrives at station
     * (shorter times ordered before longer times)
     */
    @Override
    public int compareTo(Arrival arrival) {
        return this.timeToStation - arrival.timeToStation;
    }
}
