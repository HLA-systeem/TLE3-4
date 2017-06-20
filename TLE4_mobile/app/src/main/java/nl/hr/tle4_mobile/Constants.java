package nl.hr.tle4_mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static String flight = "KL0808";
    public static String flightInfo;
    public static String luggageArrival;
    public static String luggageTag = "";
    public static String luggageTagPrev = "p";
    //{"163A84A5", "703DFA73","DOC2F973","408E274","25AA2B11"};
    public static String luggageID1 = "";
    public static String luggageID2 = "";
    public static int currentLug = 0;

    public static Map<String, Long> getluggageIDs(){
        Map<String,Long> luggageIDs = new HashMap<String,Long>();
        luggageIDs.put("163A84A5", 1000L *60L); //1  minuut
        luggageIDs.put("703DFA73", 3000L *60L);
        luggageIDs.put("DOC2F973", 2000L *60L);
        luggageIDs.put("408E274", 1000L *60L);
        luggageIDs.put("25AA2B11", 1500L *60L);
        return luggageIDs;
    }
}



