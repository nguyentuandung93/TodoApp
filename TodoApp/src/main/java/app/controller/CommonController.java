package app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonController {
	public static String getNow() {
		Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(calendar.getTime());
	}
	public static String getNow(String format) {
		Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(calendar.getTime());
	}
	public static boolean compareDate(String string1, String string2) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date1 = simpleDateFormat.parse(string1);
			Date date2 = simpleDateFormat.parse(string2);
			if (date1.before(date2)) {
			    return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
		
	}
}
