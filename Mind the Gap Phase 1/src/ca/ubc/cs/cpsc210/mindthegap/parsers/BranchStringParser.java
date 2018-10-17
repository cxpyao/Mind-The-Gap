package ca.ubc.cs.cpsc210.mindthegap.parsers;


import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Parser for branch strings in TfL line data
 */
public class BranchStringParser {

    /**
     * Parse a branch string obtained from TFL
     *
     * @param branch  branch string
     * @return       list of lat/lon points parsed from branch string
     */

    public static List<LatLon> parseBranch(String branch) {
        List<LatLon> latLonList = new LinkedList<LatLon>();
        String[] modBranch1;
        String modBranch2;
        Double lat = null;
        Double lon = null;

        if (!branch.isEmpty()) {
            modBranch1 = branch.split(",");
            for (String nextString: modBranch1) {
                if (nextString.contains("[")) {
                    modBranch2 = nextString.replace("[", "");
                } else {
                    modBranch2 = nextString.replace("]", "");
                }

                if (lon == null) {
                    lon = Double.parseDouble(modBranch2);
                } else {
                    lat = Double.parseDouble(modBranch2);
                    latLonList.add(new LatLon(lat, lon));
                    lat = null;
                    lon = null;
                }
            }
        }

        return latLonList;
    }
}
