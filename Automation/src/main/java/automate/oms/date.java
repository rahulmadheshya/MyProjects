package automate.oms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class date {

	public static void main (String args[])
	
	{
		LocalDate today = LocalDate.now();
		String formattedDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
		String currentDate[] = formattedDate.split(",");
		String currentYear = currentDate[1].replaceAll(" ", "");
		String currentMonth = (currentDate[0].split(" "))[1];
		String currentDay = (currentDate[0].split(" "))[0];
		int selectMonth = Integer.parseInt(currentDay);
		
		System.out.println(currentYear + " " + currentMonth + " " + currentDay + " " + selectMonth);

	}
	
	
}

