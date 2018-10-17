package ca.ubc.cs.cpsc210.mindthegap.tests.parsers;

import ca.ubc.cs.cpsc210.mindthegap.model.Arrival;
import ca.ubc.cs.cpsc210.mindthegap.model.ArrivalBoard;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;

import java.util.Iterator;

/**
 * Tests for TFL Arrivals parser
 */
public class AbstractTfLArrivalsParserTest {
    protected Station stn;

    /**
     *
     * @param lineId  a line id
     * @return number of arrivals on line with given id at station
     */
    protected int countArrivals(String lineId) {
        int countArrivals = 0;

        for (ArrivalBoard next : stn) {
            if (next.getLine().getId().equals(lineId)) {
                countArrivals += next.getNumArrivals();
            }
        }
        return countArrivals;
    }

    /**
     *
     * @param lineId     a line id
     * @param direction  a direction of travel
     * @return number of arrivals on line with given id in given travel direction at station
     */
    protected int countArrivalsInDirection(String lineId, String direction) {
        int countArrivals = 0;

        for(ArrivalBoard ab : stn) {
            if(ab.getLine().getId().equals(lineId) && ab.getTravelDirn().equals(direction)) {
                for(Arrival arr : ab) {
                    countArrivals++;
                }
            }
        }

        return countArrivals;
    }

    /**
     *
     * @param lineId       a line id
     * @param direction    a direction of travel
     * @return first arrival on line with given id in given travel direction at station
     */
    protected Arrival getFirstArrivalInDirection(String lineId, String direction) {
        for(ArrivalBoard ab : stn) {
            if(ab.getLine().getId().equals(lineId) && ab.getTravelDirn().equals(direction)) {
                Iterator<Arrival> itr = ab.iterator();
                return itr.next();
            }
        }

        return null;
    }

    /**
     *
     * @param lineId       a line id
     * @param direction    a direction of travel
     * @return last arrival on line with given id in given travel direction at station
     */
    protected Arrival getLastArrivalInDirection(String lineId, String direction) {
        for (ArrivalBoard ab : stn) {
            if (ab.getLine().getId().equals(lineId) && ab.getTravelDirn().equals(direction)) {
                Iterator<Arrival> itr = ab.iterator();
                Arrival next = null;

                while (itr.hasNext())
                    next = itr.next();

                return next;
            }
        }

        return null;
    }
}
