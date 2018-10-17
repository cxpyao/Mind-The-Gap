package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import ca.ubc.cs.cpsc210.mindthegap.model.StationManager;
import ca.ubc.cs.cpsc210.mindthegap.model.exception.StationException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for StationManager
 */
public class StationManagerTest {
    private StationManager stnManager;

    @Before
    public void setUp() {
        stnManager = StationManager.getInstance();
        stnManager.clearSelectedStation();
        stnManager.clearStations();
    }

    @Test
    public void testStationManagerConstructor() {
        assertEquals(stnManager.getSelected(), null);
    }

    @Test
    public void testGetStationWithExistingID() {
        loadStations();

        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);
        Station s1 = new Station("940GZZLUBKE", "Barkingside Underground Station", l1);

        assertEquals(s1, stnManager.getStationWithId("940GZZLUBKE"));
    }

    @Test
    public void testGetStationWithNonExistantID() {
        loadStations();

        assertNull(stnManager.getStationWithId("Hogwarts"));
    }

    @Test
    public void testSetGoodSelected() throws StationException {
        loadStations();

        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);
        Station s1 = new Station("940GZZLUBKE", "Barkingside Underground Station", l1);

        stnManager.setSelected(s1);

        assertEquals(s1, stnManager.getSelected());
    }

    @Test (expected = StationException.class)
    public void testSetBadSelected() throws StationException {
        loadStations();

        LatLon l1 = new LatLon(1, 2);
        stnManager.setSelected(new Station("Scotland","Hogwarts", l1));
    }

    @Test
    public void testClearSelectedStn() throws StationException{
        loadStations();

        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);
        Station s1 = new Station("940GZZLUBKE", "Barkingside Underground Station", l1);

        stnManager.setSelected(s1);
        stnManager.clearSelectedStation();

        assertNull(stnManager.getSelected());
    }

    @Test
    public void testAddStationsOnLine() {
        loadStations();

        assertEquals(4, stnManager.getNumStations());
    }

    @Test
    public void testClearStations() {
        loadStations();
        stnManager.clearStations();

        assertEquals(0, stnManager.getNumStations());
    }

    @Test
    public void testThereIsAStationNearby() {
        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);
        Station s1 = new Station("940GZZLUBKE", "Barkingside Underground Station", l1);

        loadStations();

        assertEquals(s1, stnManager.findNearestTo(new LatLon(51.585696, 0.08859599999999998)));
    }

    @Test
    public void testThereAreNoStationsNearby() {
        loadStations();

        assertNull(stnManager.findNearestTo(new LatLon(800000, 100000)));
    }

    public void loadStations() {
        Line line = new Line(LineResourceData.CENTRAL, "central", "Central");

        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);
        LatLon l2 = new LatLon(51.626612, 0.046767);
        LatLon l3 = new LatLon(51.527227, -0.0555);
        LatLon l4 = new LatLon(51.514309, -0.149719);

        Station s1 = new Station("940GZZLUBKE", "Barkingside Underground Station", l1);
        Station s2 = new Station("940GZZLUBKH", "Buckhurst Hill Underground Station", l2);
        Station s3 = new Station("940GZZLUBLG", "Bethnal Green Underground Station", l3);
        Station s4 = new Station("940GZZLUBND", "Bond Street Underground Station", l4);

        line.addStation(s1);
        line.addStation(s2);
        line.addStation(s3);
        line.addStation(s4);

        stnManager.addStationsOnLine(line);
    }
}