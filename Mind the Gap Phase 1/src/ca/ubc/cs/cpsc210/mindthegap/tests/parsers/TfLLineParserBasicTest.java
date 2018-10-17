package ca.ubc.cs.cpsc210.mindthegap.tests.parsers;

import ca.ubc.cs.cpsc210.mindthegap.TfL.DataProvider;
import ca.ubc.cs.cpsc210.mindthegap.TfL.FileDataProvider;
import ca.ubc.cs.cpsc210.mindthegap.model.Branch;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import ca.ubc.cs.cpsc210.mindthegap.parsers.BranchStringParser;
import ca.ubc.cs.cpsc210.mindthegap.parsers.TfLLineParser;
import ca.ubc.cs.cpsc210.mindthegap.parsers.exception.TfLLineDataMissingException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonException;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Unit test for TfLLineParser
 */
public class TfLLineParserBasicTest {
    private String lineData;

    @Before
    public void setUp() throws Exception {
        DataProvider dataProvider = new FileDataProvider("./res/raw/central_inbound.json");
        lineData = dataProvider.dataSourceToString();
    }

    @Test
    public void testBasicLineParsing() {
        try {
            (new TfLLineParser()).parseLine(null, lineData);
        } catch (TfLLineDataMissingException e) {
            fail("TfLLineDataMissingException not expected");
        }
    }

    @Test
    public void checkingIdAndName() throws TfLLineDataMissingException {
        Line central = new TfLLineParser().parseLine(LineResourceData.CENTRAL, lineData);

        assertEquals("Central", central.getName());
        assertEquals("central", central.getId());
    }

    @Test
    public void checkingBranches() throws TfLLineDataMissingException {
        Line central = new TfLLineParser().parseLine(LineResourceData.CENTRAL, lineData);

        assertEquals(6, central.getBranches().size());
    }

    @Test
    public void checkingStations() throws TfLLineDataMissingException {
        Line central = new TfLLineParser().parseLine(LineResourceData.CENTRAL, lineData);

        assertFalse(central.getStations().isEmpty());
        assertEquals(49, central.getStations().size());

        for(int i = 0; i < central.getStations().size(); i++) {
            assertTrue(central.getStations().get(i).hasLine(central));
        }
    }


    @Test
    public void someStationsMissingData() throws TfLLineDataMissingException, Exception {
        DataProvider dataProvider = new FileDataProvider("./res/raw/somestationsmissingdata.json");
        String missingData = dataProvider.dataSourceToString();

        Line central = new TfLLineParser().parseLine(LineResourceData.CENTRAL, missingData);

        assertEquals(45,central.getStations().size());

        for(int i = 0; i < central.getStations().size(); i++) {
            assertTrue(central.getStations().get(i).hasLine(central));
        }
    }

    @Test
    public void allStationsMissingData() throws TfLLineDataMissingException, Exception {
        DataProvider dataProvider = new FileDataProvider("./res/raw/allstationsmissingdata.json");
        String missingData = dataProvider.dataSourceToString();

        try {
            Line central = new TfLLineParser().parseLine(LineResourceData.CENTRAL, missingData);
        } catch (JsonException e) {
            System.out.println("Json Exception Caught");
        } catch (TfLLineDataMissingException e) {
            System.out.println("TfLLineDataMissingException Caught");
        }
    }

    @Test
    public void lineNameMissing() throws TfLLineDataMissingException, Exception {
        DataProvider dataProvider = new FileDataProvider("./res/raw/lineNamemissing.json");
        String missingData = dataProvider.dataSourceToString();

        try {
            Line central = new TfLLineParser().parseLine(LineResourceData.CENTRAL, missingData);
        } catch (JsonException e) {
            System.out.println("Json Exception Caught");
        } catch (TfLLineDataMissingException e) {
            System.out.println("TfLLineDataMissingException Caught");
        }
    }

    @Test
    public void lineIdMissing() throws TfLLineDataMissingException, Exception{
        DataProvider dataProvider = new FileDataProvider("./res/raw/lineIdmissing.json");
        String missingData = dataProvider.dataSourceToString();

        try {
            Line central = new TfLLineParser().parseLine(LineResourceData.CENTRAL, missingData);
        } catch (JsonException e) {
            System.out.println("Json Exception Caught");
        } catch (TfLLineDataMissingException e) {
            System.out.println("TfLLineDataMissingException Caught");
        }
    }

    @Test
    public void stopPointSequencesMissing() throws TfLLineDataMissingException, Exception {
        DataProvider dataProvider = new FileDataProvider("./res/raw/stoppointsequencesmissing.json");
        String missingData = dataProvider.dataSourceToString();

        try {
            Line central = new TfLLineParser().parseLine(LineResourceData.CENTRAL, missingData);
        } catch (JsonException e) {
            System.out.println("Json Exception Caught");
        } catch (TfLLineDataMissingException e) {
            System.out.println("TfLLineDataMissingException Caught");
        }
    }

    @Test
    public void incorrectFormat() throws TfLLineDataMissingException, JsonException, Exception {
        DataProvider dataProvider = new FileDataProvider("./res/raw/incorrectFormat.json");
        String incorrectFormat = dataProvider.dataSourceToString();

        try {
            Line central = new TfLLineParser().parseLine(LineResourceData.CENTRAL, incorrectFormat);
        } catch (JsonException e) {
            System.out.println("Caught Json Exception!");
        } catch (TfLLineDataMissingException e) {
            fail("Supposed to throw JsonException");
        }

    }
}