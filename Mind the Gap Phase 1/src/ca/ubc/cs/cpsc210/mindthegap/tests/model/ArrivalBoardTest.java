package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.Arrival;
import ca.ubc.cs.cpsc210.mindthegap.model.ArrivalBoard;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for ArrivalBoard
 */
public class ArrivalBoardTest {
    private ArrivalBoard arrivalBoard;

    @Before
    public void runBefore() {
        Line line = new Line(LineResourceData.CENTRAL, "central", "Central");
        arrivalBoard = new ArrivalBoard(line, "Westbound");
    }

    @Test
    public void testConstructor() {
        assertEquals("Westbound", arrivalBoard.getTravelDirn());
        assertEquals(new Line(LineResourceData.CENTRAL, "central", "Central"), arrivalBoard.getLine());
    }

    @Test
    public void testGetNumArrivals() {
        loadArrivals();

        assertEquals(4, arrivalBoard.getNumArrivals());
    }

    @Test
    public void testClear() {
        loadArrivals();
        arrivalBoard.clearArrivals();

        assertEquals(0, arrivalBoard.getNumArrivals());
    }

    @Test
    public void testEquals() {
        Line line = new Line(LineResourceData.CENTRAL, "central", "Central");
        ArrivalBoard arrivalBoard2 = new ArrivalBoard(line, "Westbound");

        assertTrue(arrivalBoard.equals(arrivalBoard2));
    }

    @Test
    public void testArrivalsInOrder() {
        loadArrivals();

        Iterator<Arrival> itr = arrivalBoard.iterator();
        while (itr.hasNext()) {
            Object station = itr.next().getDestination();
            System.out.print(station + " , ");
        }
        System.out.println();
    }

    private void loadArrivals() {
        Arrival a1 = new Arrival(60, "Ealing Broadway Underground Station", "Westbound - Platform 1");
        Arrival a2 = new Arrival(120, "White City Underground Station", "Westbound - Platform 1");
        Arrival a3 = new Arrival(30, "North Acton Underground Station", "Westbound - Platform 1");
        Arrival a4 = new Arrival(180, "West Ruislip Underground Station", "Westbound - Platform 1");

        arrivalBoard.addArrival(a1);
        arrivalBoard.addArrival(a2);
        arrivalBoard.addArrival(a3);
        arrivalBoard.addArrival(a4);
    }
}
