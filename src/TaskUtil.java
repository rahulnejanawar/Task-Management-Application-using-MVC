import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskUtil {

	
	public static boolean isValidDate(String endDt)
	{
		/*
		 * 1. validate if string is a valid date format repre..
		 * 2. if date is greater than or equal to today
		 */
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			
			Date dt = sdf.parse(endDt);
			
			Date currDt = new Date();
			if(dt.compareTo(currDt)<0)
				return false;
			else 
				return true;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static boolean validateName(String name)
	{
		if(name==null || name.trim().equals(""))
			return false;
		
		if(name.split(" ").length > 1)
			return false;
		
		if(!Character.isLetter(name.charAt(0)))
			return false;
		
		for(int i = 0 ; i < name.length() ; i++)
		{
			char c = name.charAt(i);
			
			if(!(Character.isDigit(c) || Character.isLetter(c)))
				return false;
		}
		
		return true;
	}
	
}