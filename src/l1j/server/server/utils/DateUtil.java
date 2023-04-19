package l1j.server.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Calendar A utility class that collects object-related functions
 *
 * @author croute
 * @since 2011.02.10
 */
public class DateUtil
{

    /**
     * calendar object yyyy-MM-dd HH:mm:ss Convert to a string of the form.
     *
     * @param cal calendar object
     * @return converted string
     */
    public static String StringFromCalendar(Calendar cal)
    {
        // Convert date to string for correspondence
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(cal.getTime());
    }

    /**
     * Converts a calendar object to a string in the format yyyy-MM-dd.
     *
     * @param cal calendar object
     * @return converted string
     */
    public static String StringSimpleFromCalendar(Calendar cal)
    {
        // Convert date to string for correspondence
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal.getTime());
    }

    /**
     * Converts a string of the form yyyy-MM-dd HH:mm:ss to a calendar object.
     * If the conversion fails, today's date is returned.
     *
     * @param date string representing the date
     * @return converted calendar object
     */
    public static Calendar CalendarFromString(String date)
    {
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(formatter.parse(date));
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    /**
     * Converts a string of the form yyyy-MM-dd to a calendar object.
     * If the conversion fails, today's date is returned.
     *
     * @param date string representing the date
     * @return converted calendar object
     */
    public static Calendar CalendarFromStringSimple(String date)
    {
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            cal.setTime(formatter.parse(date));
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
}
