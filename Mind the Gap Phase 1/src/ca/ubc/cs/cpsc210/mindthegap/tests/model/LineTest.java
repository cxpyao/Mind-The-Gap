package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.Branch;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for Line
 */
public class LineTest {
    private Line line;

    @Before
    public void runBefore() {
        line = new Line(LineResourceData.CENTRAL, "central", "Central");
    }

    @Test
    public void testConstructor() {
        assertEquals("Central", line.getName());
        assertEquals("central", line.getId());
    }

    @Test
    public void testGetColour() {
        assertEquals(0xFFDC241F, line.getColour());
    }

    @Test
    public void testAddAStation() {
        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);
        Station s1 = new Station("940GZZLUBKE", "Barkingside Underground Station", l1);

        line.addStation(s1);

        assertEquals(1, line.getStations().size());
        assertTrue(line.hasStation(s1));
    }

    @Test
    public void testAddMultipleStations() {
        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);
        LatLon l2 = new LatLon(51.626612, 0.046767);
        LatLon l3 = new LatLon(51.527227, -0.0555);
        LatLon l4 = new LatLon(51.514309, -0.149719);

        Station s1 = new Station("940GZZLUBKE", "Barkingside Underground Station", l1);
        Station s2 = new Station("940GZZLUBKH", "Buckhurst Hill Underground Station", l2);
        Station s3 = new Station("940GZZLUBLG", "Bethnal Green Underground Station", l3);
        Station s4 = new Station("940GZZLUBND", "Bond Street Underground Station", l4);

        loadStations();

        assertEquals(4, line.getStations().size());
        assertTrue(line.hasStation(s1));
        assertTrue(line.hasStation(s2));
        assertTrue(line.hasStation(s3));
        assertTrue(line.hasStation(s4));
    }

    @Test
    public void testRemoveAStation() {
        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);
        Station s1 = new Station("940GZZLUBKE", "Barkingside Underground Station", l1);

        loadStations();
        line.removeStation(s1);

        assertEquals(3, line.getStations().size());
        assertFalse(line.hasStation(s1));
    }

    @Test
    public void testRemoveMultipleStations() {
        LatLon l4 = new LatLon(51.514309, -0.149719);
        Station s4 = new Station("940GZZLUBND", "Bond Street Underground Station", l4);

        loadStations();
        removeStations();

        assertFalse(line.getStations().isEmpty());
        assertEquals(1, line.getStations().size());
        assertTrue(line.hasStation(s4));
    }

    @Test
    public void testClearStations() {
        loadStations();
        line.clearStations();

        assertTrue(line.getStations().isEmpty());
    }

    @Test
    public void testAddABranch() {
        Branch b1 = new Branch("[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857],[0.090015,51.5757],[0.066195,51.5765],[0.045369,51.5762],[0.028537,51.5755],[0.008202,51.5683],[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272],[-0.083176,51.5174],[-0.088948,51.5134],[-0.097562,51.5149],[-0.111578,51.5183],[-0.12047,51.5176],[-0.130406,51.5164],[-0.141899,51.5152],[-0.149719,51.5143],[-0.15895,51.5134],[-0.175491,51.5117],[-0.187149,51.5103],[-0.196102,51.5091],[-0.205677,51.5071],[-0.218812,51.5044],[-0.224295,51.512],[-0.247248,51.5166],[-0.259754,51.5235],[-0.292704,51.5302],[-0.323447,51.5367],[-0.346052,51.5424],[-0.368702,51.5482],[-0.398904,51.5569],[-0.410699,51.5607],[-0.437875,51.5697]]]");

        line.addBranch(b1);

        assertTrue(line.getBranches().contains(b1));
        assertEquals(1, line.getBranches().size());
    }

    @Test
    public void testAddMultipleBranches() {
        Branch b1 = new Branch("[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857],[0.090015,51.5757],[0.066195,51.5765],[0.045369,51.5762],[0.028537,51.5755],[0.008202,51.5683],[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272],[-0.083176,51.5174],[-0.088948,51.5134],[-0.097562,51.5149],[-0.111578,51.5183],[-0.12047,51.5176],[-0.130406,51.5164],[-0.141899,51.5152],[-0.149719,51.5143],[-0.15895,51.5134],[-0.175491,51.5117],[-0.187149,51.5103],[-0.196102,51.5091],[-0.205677,51.5071],[-0.218812,51.5044],[-0.224295,51.512],[-0.247248,51.5166],[-0.259754,51.5235],[-0.292704,51.5302],[-0.323447,51.5367],[-0.346052,51.5424],[-0.368702,51.5482],[-0.398904,51.5569],[-0.410699,51.5607],[-0.437875,51.5697]]]");
        Branch b2 = new Branch("[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857],[0.090015,51.5757],[0.066195,51.5765],[0.045369,51.5762],[0.028537,51.5755],[0.008202,51.5683],[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272],[-0.083176,51.5174],[-0.088948,51.5134],[-0.097562,51.5149],[-0.111578,51.5183],[-0.12047,51.5176],[-0.130406,51.5164],[-0.141899,51.5152],[-0.149719,51.5143],[-0.15895,51.5134],[-0.175491,51.5117],[-0.187149,51.5103],[-0.196102,51.5091],[-0.205677,51.5071],[-0.218812,51.5044],[-0.224295,51.512],[-0.247248,51.5166],[-0.259754,51.5235],[-0.28098,51.518],[-0.301457,51.515]]]");
        Branch b3 = new Branch("[[[0.11378,51.6937],[0.103097,51.6718],[0.083793,51.6454],[0.055486,51.6415],[0.046767,51.6266],[0.03398,51.6069],[0.027347,51.5919],[0.021449,51.5807],[0.008202,51.5683],[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272],[-0.083176,51.5174],[-0.088948,51.5134],[-0.097562,51.5149],[-0.111578,51.5183],[-0.12047,51.5176],[-0.130406,51.5164],[-0.141899,51.5152],[-0.149719,51.5143],[-0.15895,51.5134],[-0.175491,51.5117],[-0.187149,51.5103],[-0.196102,51.5091],[-0.205677,51.5071],[-0.218812,51.5044],[-0.224295,51.512],[-0.247248,51.5166],[-0.259754,51.5235],[-0.292704,51.5302],[-0.323447,51.5367],[-0.346052,51.5424],[-0.368702,51.5482],[-0.398904,51.5569],[-0.410699,51.5607],[-0.437875,51.5697]]]");
        Branch b4 = new Branch("[[[0.11378,51.6937],[0.103097,51.6718],[0.083793,51.6454],[0.055486,51.6415],[0.046767,51.6266],[0.03398,51.6069],[0.027347,51.5919],[0.021449,51.5807],[0.008202,51.5683],[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272],[-0.083176,51.5174],[-0.088948,51.5134],[-0.097562,51.5149],[-0.111578,51.5183],[-0.12047,51.5176],[-0.130406,51.5164],[-0.141899,51.5152],[-0.149719,51.5143],[-0.15895,51.5134],[-0.175491,51.5117],[-0.187149,51.5103],[-0.196102,51.5091],[-0.205677,51.5071],[-0.218812,51.5044],[-0.224295,51.512],[-0.247248,51.5166],[-0.259754,51.5235],[-0.28098,51.518],[-0.301457,51.515]]]");

        loadBranches();

        assertEquals(4, line.getBranches().size());
        assertTrue(line.getBranches().contains(b1));
        assertTrue(line.getBranches().contains(b2));
        assertTrue(line.getBranches().contains(b3));
        assertTrue(line.getBranches().contains(b4));
    }

    public void loadStations() {
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
    }

    public void removeStations() {
        LatLon l1 = new LatLon(51.585695, 0.08859599999999999);
        LatLon l2 = new LatLon(51.626612, 0.046767);
        LatLon l3 = new LatLon(51.527227, -0.0555);

        Station s1 = new Station("940GZZLUBKE", "Barkingside Underground Station", l1);
        Station s2 = new Station("940GZZLUBKH", "Buckhurst Hill Underground Station", l2);
        Station s3 = new Station("940GZZLUBLG", "Bethnal Green Underground Station", l3);

        line.removeStation(s1);
        line.removeStation(s2);
        line.removeStation(s3);
    }

    public void loadBranches() {
        Branch b1 = new Branch("[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857],[0.090015,51.5757],[0.066195,51.5765],[0.045369,51.5762],[0.028537,51.5755],[0.008202,51.5683],[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272],[-0.083176,51.5174],[-0.088948,51.5134],[-0.097562,51.5149],[-0.111578,51.5183],[-0.12047,51.5176],[-0.130406,51.5164],[-0.141899,51.5152],[-0.149719,51.5143],[-0.15895,51.5134],[-0.175491,51.5117],[-0.187149,51.5103],[-0.196102,51.5091],[-0.205677,51.5071],[-0.218812,51.5044],[-0.224295,51.512],[-0.247248,51.5166],[-0.259754,51.5235],[-0.292704,51.5302],[-0.323447,51.5367],[-0.346052,51.5424],[-0.368702,51.5482],[-0.398904,51.5569],[-0.410699,51.5607],[-0.437875,51.5697]]]");
        Branch b2 = new Branch("[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857],[0.090015,51.5757],[0.066195,51.5765],[0.045369,51.5762],[0.028537,51.5755],[0.008202,51.5683],[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272],[-0.083176,51.5174],[-0.088948,51.5134],[-0.097562,51.5149],[-0.111578,51.5183],[-0.12047,51.5176],[-0.130406,51.5164],[-0.141899,51.5152],[-0.149719,51.5143],[-0.15895,51.5134],[-0.175491,51.5117],[-0.187149,51.5103],[-0.196102,51.5091],[-0.205677,51.5071],[-0.218812,51.5044],[-0.224295,51.512],[-0.247248,51.5166],[-0.259754,51.5235],[-0.28098,51.518],[-0.301457,51.515]]]");
        Branch b3 = new Branch("[[[0.11378,51.6937],[0.103097,51.6718],[0.083793,51.6454],[0.055486,51.6415],[0.046767,51.6266],[0.03398,51.6069],[0.027347,51.5919],[0.021449,51.5807],[0.008202,51.5683],[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272],[-0.083176,51.5174],[-0.088948,51.5134],[-0.097562,51.5149],[-0.111578,51.5183],[-0.12047,51.5176],[-0.130406,51.5164],[-0.141899,51.5152],[-0.149719,51.5143],[-0.15895,51.5134],[-0.175491,51.5117],[-0.187149,51.5103],[-0.196102,51.5091],[-0.205677,51.5071],[-0.218812,51.5044],[-0.224295,51.512],[-0.247248,51.5166],[-0.259754,51.5235],[-0.292704,51.5302],[-0.323447,51.5367],[-0.346052,51.5424],[-0.368702,51.5482],[-0.398904,51.5569],[-0.410699,51.5607],[-0.437875,51.5697]]]");
        Branch b4 = new Branch("[[[0.11378,51.6937],[0.103097,51.6718],[0.083793,51.6454],[0.055486,51.6415],[0.046767,51.6266],[0.03398,51.6069],[0.027347,51.5919],[0.021449,51.5807],[0.008202,51.5683],[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272],[-0.083176,51.5174],[-0.088948,51.5134],[-0.097562,51.5149],[-0.111578,51.5183],[-0.12047,51.5176],[-0.130406,51.5164],[-0.141899,51.5152],[-0.149719,51.5143],[-0.15895,51.5134],[-0.175491,51.5117],[-0.187149,51.5103],[-0.196102,51.5091],[-0.205677,51.5071],[-0.218812,51.5044],[-0.224295,51.512],[-0.247248,51.5166],[-0.259754,51.5235],[-0.28098,51.518],[-0.301457,51.515]]]");

        line.addBranch(b1);
        line.addBranch(b2);
        line.addBranch(b3);
        line.addBranch(b4);
    }
}