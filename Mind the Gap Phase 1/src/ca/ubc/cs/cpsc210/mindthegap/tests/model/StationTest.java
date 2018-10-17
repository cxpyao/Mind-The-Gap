package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.*;
import ca.ubc.cs.cpsc210.mindthegap.model.exception.ArrivalException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for Station
 */
public class StationTest {
    private Station station;

    @Before
    public void runBefore() {
        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);

        station = new Station("940GZZLUBKE", "Barkingside Underground Station", l1);
    }

    @Test
    public void testConstructor() {
        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);

        assertEquals("940GZZLUBKE", station.getID());
        assertEquals("Barkingside Underground Station", station.getName());
        assertEquals(l1, station.getLocn());
    }

    @Test
    public void hasLine() {
        Line central = new Line(LineResourceData.CENTRAL, "central", "Central");
        central.addStation(station);
        station.addLine(central);

        assertTrue(station.hasLine(central));
    }

    @Test
    public void testAddALine() {
        Line central = new Line(LineResourceData.CENTRAL, "central", "Central");
        central.addStation(station);
        station.addLine(central);

        assertEquals(1, station.getLines().size());
        assertTrue(station.getLines().contains(new Line(LineResourceData.CENTRAL, "central", "Central")));
    }

    @Test
    public void testAddMultipleLines() {
        Line central = new Line(LineResourceData.CENTRAL, "central", "Central");
        Line piccadilly = new Line (LineResourceData.PICCADILLY, "piccadilly", "Piccadilly");
        central.addStation(station);
        piccadilly.addStation(station);
        station.addLine(central);
        station.addLine(piccadilly);

        assertEquals(2, station.getLines().size());
    }

    @Test
    public void testRemoveALine() {
        station.addLine(new Line(LineResourceData.CENTRAL, "central", "Central"));
        station.removeLine(new Line(LineResourceData.CENTRAL, "central", "Central"));

        assertTrue(station.getLines().isEmpty());
    }

    @Test
    public void testRemoveMultiplelines() {
        station.addLine(new Line(LineResourceData.CENTRAL, "central", "Central"));
        station.addLine(new Line (LineResourceData.PICCADILLY, "piccadilly", "Piccadilly"));

        station.removeLine(new Line(LineResourceData.CENTRAL, "central", "Central"));
        station.removeLine(new Line (LineResourceData.PICCADILLY, "piccadilly", "Piccadilly"));

        assertTrue(station.getLines().isEmpty());
    }

    @Test
    public void testAddAGoodArrival() throws ArrivalException {
        Line l1 = new Line(LineResourceData.CENTRAL, "central", "Central");
        Arrival a1 = new Arrival(60, "Ealing Broadway Underground Station", "Westbound - Platform 1");
        l1.addStation(station);

        station.addLine(l1);
        station.addArrival(l1, a1);

        assertEquals(1, station.getNumArrivalBoards());
    }

    @Test
    public void testAddMultipleGoodArrivalsOnDifferentLines() throws ArrivalException{
        loadGoodArrivals();

        assertEquals(2, station.getNumArrivalBoards());
    }

    @Test
    public void testAddABadArrival() throws ArrivalException {
        Line l1 = new Line(LineResourceData.CENTRAL, "central", "Central");
        Arrival a1 = new Arrival(60, "Ealing Broadway Underground Station", "Westbound - Platform 1");

        try {
            station.addArrival(l1, a1);
        } catch (ArrivalException e) {
            System.out.println("ArrivalException Caught");
        }
    }

    @Test
    public void testAddMultipleBadArrivals() throws ArrivalException {
        try {
            loadBadArrivals();
        } catch (ArrivalException e) {
            System.out.println("ArrivalException Caught");
        }
    }

    @Test
    public void testClearArrivalBoard() throws ArrivalException {
        loadGoodArrivals();
        station.clearArrivalBoards();

        assertEquals(0, station.getNumArrivalBoards());
    }

    public void loadGoodArrivals() throws ArrivalException {
        Line l1 = new Line(LineResourceData.CENTRAL, "central", "Central");
        Line l2 = new Line(LineResourceData.PICCADILLY, "piccadilly", "Piccadilly");
        l1.addStation(station);
        l2.addStation(station);

        station.addLine(l1);
        station.addLine(l2);

        Arrival a1 = new Arrival(60, "Ealing Broadway Underground Station", "Westbound - Platform 1");
        Arrival a2 = new Arrival(120, "White City Underground Station", "Westbound - Platform 1");
        Arrival a3 = new Arrival(30, "North Acton Underground Station", "Westbound - Platform 1");
        Arrival a4 = new Arrival(180, "West Ruislip Underground Station", "Westbound - Platform 1");

        station.addArrival(l1, a1);
        station.addArrival(l1, a2);
        station.addArrival(l2, a3);
        station.addArrival(l2, a4);
    }

    public void loadBadArrivals() throws ArrivalException {
        Line l1 = new Line(LineResourceData.CENTRAL, "central", "Central");

        Arrival a1 = new Arrival(60, "Ealing Broadway Underground Station", "Westbound - Platform 1");
        Arrival a2 = new Arrival(120, "White City Underground Station", "Westbound - Platform 1");
        Arrival a3 = new Arrival(30, "North Acton Underground Station", "Westbound - Platform 1");
        Arrival a4 = new Arrival(180, "West Ruislip Underground Station", "Westbound - Platform 1");

        station.addArrival(l1, a1);
        station.addArrival(l1, a2);
        station.addArrival(l1, a3);
        station.addArrival(l1, a4);
    }
}
