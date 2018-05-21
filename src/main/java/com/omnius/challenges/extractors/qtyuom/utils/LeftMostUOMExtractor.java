package main.java.com.omnius.challenges.extractors.qtyuom.utils;

import com.omnius.challenges.extractors.qtyuom.QtyUomExtractor;
import com.omnius.challenges.extractors.qtyuom.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Implements {@link QtyUomExtractor} identifying as <strong>the most relevant UOM</strong> the leftmost UOM found in the articleDescription.
 * The {@link UOM} array contains the list of valid UOMs. The algorithm search for the leftmost occurence of UOM[i], if there are no occurrences then tries UOM[i+1].
 *
 * Example
 * <ul>
 * <li>article description: "black steel bar 35 mm 77 stck"</li>
 * <li>QTY: "77" (and NOT "35")</li>
 * <li>UOM: "stck" (and not "mm" since "stck" has an higher priority as UOM )</li>
 * </ul>
 *
 * @author <a href="mailto:damiano@searchink.com">Damiano Giampaoli</a>
 * @since 4 May 2018
 */
public class LeftMostUOMExtractor implements QtyUomExtractor {

    /**
     * Array of valid UOM to match. the elements with lower index in the array has higher priority
     */
    public static String[] UOM = {"stk", "stk.", "stck", "stück", "stg", "stg.", "st", "st.", "stange", "stange(n)", "tafel", "tfl", "taf", "mtr", "meter", "qm", "kg", "lfm", "mm", "m"};

    @Override
    public Pair<String, String> extract(String articleDescription)  {

        // array of unit to match
        String[] UOM = {"stk", "stk.", "stck", "stück", "stg", "stg.", "st", "st.", "stange", "stange(n)", "tafel", "tfl", "taf", "mtr", "meter", "qm", "kg", "lfm", "mm", "m"};

        List<String> MatchedList = new ArrayList<String>();  // use list to store matching result if more than one result matched

        // Pattern containing regular expression to extract QTY and UOM from article
        Pattern p = Pattern.compile(" (?x)( (\\d*\\,\\d{2}) | " +
                " (\\d+\\.\\d+) | " +
                " (\\d\\s\\d{3}\\s\\d{3}\\,?\\d) | (\\d+\\s\\.\\s\\d+) | (\\d+\\s\\,\\s\\d+) | (\\d{2}\\,\\d{3}\\,\\d{3}\\.\\d+) | (\\d+(\\.\\d*[0-9])?) " +
                " | (\\d{2}\\s\\d{3}\\s\\d{3}) )\\s+ (?: stk | stk. | stck | stück | stg | stg. | st | st. | stange | " +
                " stange(n) | tafel | tfl | taf | mtr | meter | qm | kg | lfm | mm | m) ");

        if(articleDescription != null || !articleDescription.isEmpty())   // if article description is not empty or null
        {
            Matcher m = p.matcher(articleDescription);  // matching a pattern with article description

            while (m.find())  // find the next matching sequence
            {
                if (m.groupCount() > 0) {
                    MatchedList.add(m.group());  // add matching QTY and UOM pair in list
                }
            }

            // save the first matching value from the list to string to split and save in Pair<String,String> format
            String FirstMatchedValue = MatchedList.get(0);
            if(FirstMatchedValue !=null || !FirstMatchedValue.isEmpty()) {
                // check if the FirstMatchedValue contains the UOM then save into Optional Long type String to split the value in two
                Optional<String> MatchedUOM = (Arrays.stream(UOM).filter(FirstMatchedValue::contains).findFirst());

                // store QTY by selecting substring from a FirstMatchedValue by getting index of UOM
                String QTY = FirstMatchedValue.substring(0, FirstMatchedValue.indexOf(MatchedUOM.get()));
                String SavedUOM = MatchedUOM.get();   // save UOM

                return new Pair<String, String>(QTY, SavedUOM); // return the result in pair form
            }
            else
            {
                return new Pair<String, String>(null,null); // if no value matched it will return null
            }
        }
        else {
            return  new Pair<String, String>(null,null);
        }

        //mock implementation
       // return new Pair<String, String>("54.7","mm");
    }

    /*-----------return output in pair of double and string */
    @Override
    public Pair<Double, String> extractAsDouble(String articleDescription) {

            // array of unit to match
        String[] UOM = {"stk", "stk.", "stck", "stück", "stg", "stg.", "st", "st.", "stange", "stange(n)", "tafel", "tfl", "taf", "mtr", "meter", "qm", "kg", "lfm", "mm", "m"};

        List<String> MatchedList = new ArrayList<String>();  // use list to store matching result if more than one result matched

        // Pattern containing regular expression to extract QTY and UOM from article
        Pattern p = Pattern.compile(" (?x)( (\\d*\\,\\d{2}) | " +
                " (\\d+\\.\\d+) | " +
                " (\\d\\s\\d{3}\\s\\d{3}\\,?\\d) | (\\d+\\s\\.\\s\\d+) | (\\d+\\s\\,\\s\\d+) | (\\d{2}\\,\\d{3}\\,\\d{3}\\.\\d+) | (\\d+(\\.\\d*[0-9])?) " +
                " | (\\d{2}\\s\\d{3}\\s\\d{3}) )\\s+ (?: stk | stk. | stck | stück | stg | stg. | st | st. | stange | " +
                " stange(n) | tafel | tfl | taf | mtr | meter | qm | kg | lfm | mm | m) ");

        if(articleDescription != null || !articleDescription.isEmpty())   // if article description is not empty or null
        {
            Matcher m = p.matcher(articleDescription);  // matching a pattern with article description

            while (m.find())  // find the next matching sequence
            {
                if (m.groupCount() > 0) {
                    MatchedList.add(m.group());  // add matching QTY and UOM pair in list
                }
            }

            // save the first matching value from the list to string to split and save in Pair<String,String> format
            String FirstMatchedValue = MatchedList.get(0);
            if (FirstMatchedValue != null || !FirstMatchedValue.isEmpty()) {
                // check if the FirstMatchedValue contains the UOM then save into Optional Long type String to split the value in two
                Optional<String> MatchedUOM = (Arrays.stream(UOM).filter(FirstMatchedValue::contains).findFirst());

                // store QTY by selecting substring from a FirstMatchedValue by getting index of UOM
                String QTY = FirstMatchedValue.substring(0, FirstMatchedValue.indexOf(MatchedUOM.get()));
                Double QTYdouble;
                if (QTY.contains(" "))  // if QTY contains white spaces replace them
                {
                    QTYdouble = Double.parseDouble(QTY.replace(" ", ""));
                }
                else if (QTY.contains(",")) {
                    QTYdouble = Double.parseDouble(QTY.replace(",", "."));
                }
                else {
                    QTYdouble = Double.parseDouble(QTY);
                }

                String SavedUOM = MatchedUOM.get();   // save UOM

                return new Pair<Double, String>(QTYdouble, SavedUOM); // return the result in pair form
            }
            else
            {
                return new Pair<Double, String>(0.00,null); // if no value matched it will return null
            }
        }
        else {
            return  new Pair<Double, String>(0.00,null);
        }


        //mock implementation
      //  return new Pair<Double, String>(34.5d,"m");
    }


}
