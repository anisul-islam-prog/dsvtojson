package dsvtojson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FlexibleDateParser {
    private List<ThreadLocal<SimpleDateFormat>> threadLocals = new  ArrayList<ThreadLocal<SimpleDateFormat>>();

    public FlexibleDateParser(List<String> formats){
        threadLocals.clear();
        for (final String format : formats) {
            ThreadLocal<SimpleDateFormat> dateFormatTL = new ThreadLocal<SimpleDateFormat>() {
                protected SimpleDateFormat initialValue() {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
//                    sdf.setTimeZone(tz); 
//                    sdf.setLenient(false);
                    return sdf;
                }
            };
            threadLocals.add(dateFormatTL);
        }       
    }

    public Date parseDate(String dateStr) throws ParseException {
        for (ThreadLocal<SimpleDateFormat> tl : threadLocals) {
            SimpleDateFormat sdf = tl.get();
            try {
                return sdf.parse(dateStr);
            } catch (ParseException e) {
                // Ignore and try next date parser
            }
        }
        // All parsers failed
        return null;
    }       
}
