package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.Branch;
import ca.ubc.cs.cpsc210.mindthegap.parsers.BranchStringParser;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for Branch
 */
public class BranchTest {
    private Branch branch;

    @Before
    public void runBefore() {
        branch = new Branch("[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857],[0.090015,51.5757],[0.066195,51.5765],[0.045369,51.5762],[0.028537,51.5755],[0.008202,51.5683],[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272],[-0.083176,51.5174],[-0.088948,51.5134],[-0.097562,51.5149],[-0.111578,51.5183],[-0.12047,51.5176],[-0.130406,51.5164],[-0.141899,51.5152],[-0.149719,51.5143],[-0.15895,51.5134],[-0.175491,51.5117],[-0.187149,51.5103],[-0.196102,51.5091],[-0.205677,51.5071],[-0.218812,51.5044],[-0.224295,51.512],[-0.247248,51.5166],[-0.259754,51.5235],[-0.292704,51.5302],[-0.323447,51.5367],[-0.346052,51.5424],[-0.368702,51.5482],[-0.398904,51.5569],[-0.410699,51.5607],[-0.437875,51.5697]]]");
    }

    @Test
    public void testConstructor() {
        assertFalse(branch.getPoints().isEmpty());
    }

    @Test
    public void testBranchStringParserWithNoValues() {
        assertTrue(new Branch("").getPoints().isEmpty());
    }

    @Test
    public void testBranchStringParserWithOneValue() {
        LatLon l1 = new LatLon(51.6037, 0.093493);
        Branch testBranch = new Branch("[[[0.093493,51.6037]]]");

        assertFalse(testBranch.getPoints().isEmpty());
        assertEquals(1, testBranch.getPoints().size());
        assertTrue(testBranch.getPoints().contains(l1));
    }

    @Test
    public void testBranchStringParserWithTwoValues() {
        LatLon l1 = new LatLon(51.6037, 0.093493);
        LatLon l2 = new LatLon(51.5956, 0.091015);
        Branch testBranch = new Branch("[[[0.093493,51.6037],[0.091015,51.5956]]]");

        assertFalse(testBranch.getPoints().isEmpty());
        assertEquals(2, testBranch.getPoints().size());
        assertTrue(testBranch.getPoints().contains(l1));
        assertTrue(testBranch.getPoints().contains(l2));
    }

    @Test
    public void testBranchStringParserWithMultipleValues() {
        LatLon l1 = new LatLon(51.6037, 0.093493);
        LatLon l2 = new LatLon(51.5956, 0.091015);
        LatLon l3 = new LatLon(51.5857, 0.088596);
        LatLon l4 = new LatLon(51.5757, 0.090015);
        LatLon l5 = new LatLon(51.5765, 0.066195);

        assertFalse(branch.getPoints().isEmpty());

        assertEquals(36, branch.getPoints().size());
        assertTrue(branch.getPoints().contains(l1));
        assertTrue(branch.getPoints().contains(l2));
        assertTrue(branch.getPoints().contains(l3));
        assertTrue(branch.getPoints().contains(l4));
        assertTrue(branch.getPoints().contains(l5));

        assertEquals(l1, branch.getPoints().get(0));
        assertEquals(l2, branch.getPoints().get(1));
        assertEquals(l3, branch.getPoints().get(2));
        assertEquals(l4, branch.getPoints().get(3));
        assertEquals(l5, branch.getPoints().get(4));
    }
}
