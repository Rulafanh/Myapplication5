import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomHttpHeaderParserUtil {

    public static long parseDateAsEpoch(String dateStr) {
        try {
            // Parse date using a specific format and English locale
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH);
            Date date = sdf.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            // Handle parsing error
            e.printStackTrace();
            return 0; // fallback to 0 if parsing fails
        }
    }
}
