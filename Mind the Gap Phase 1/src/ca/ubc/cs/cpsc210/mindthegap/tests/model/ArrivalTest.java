package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.Arrival;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for Arrival
 */
public class ArrivalTest {
    private Arrival arrival;

    @Before
    public void runBefore() {
        arrival = new Arrival(60, "Ealing Broadway Underground Station", "Westbound - Platform 1");
    }

    @Test
    public void testConstructor() {
        assertEquals("Ealing Broadway Underground Station", arrival.getDestination());
        assertEquals("Westbound - Platform 1", arrival.getPlatform());
    }

    @Test
    public void testTimeToStationInMins() {
        assertEquals(1, arrival.getTimeToStationInMins());
    }

    @Test
    public void testGetTravelDirn() {
        assertEquals("Westbound", arrival.getTravelDirn());
    }

    @Test
    public void testGetPlatformName() {
        assertEquals("Platform 1", arrival.getPlatformName());
    }

    @Test
    public void testRoundUpToNearestMinute() {
        Arrival arrival2 = new Arrival(90, "Ealing Broadway Underground Station", "Westbound - Platform 1");

        assertEquals(2, arrival2.getTimeToStationInMins());
    }
}
