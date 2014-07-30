package org.parser.French;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Tokenize {

	public String[] getListString(String content)
	{
		String[] res;
		res = content.split("\\s+");
		return res;
	}
	
	static List<String> listTokens(String content)
	{
		content = SegmentFrench(content);
		List<String> res = new ArrayList<String>();
		boolean test = true;
		String except[]={"les","la","des","de","dans","ou","on","en","le","la","et"};	       
		StringTokenizer mot = new StringTokenizer(content);
		while(mot.hasMoreElements())
		{
			test = true;
			String motfinal = mot.nextToken();
			for(int i = 0; i < except.length; i++)
			{
				if ((except[i].equals(motfinal)))
	            {
					test = false;
	            }
			}
			if(test)
			{
				res.add(motfinal);
			}
		}
		return res;
	}
	
	private static String SegmentFrench(String content)
	{
		content = content.replace("?", " ");
	    content = content.replace(":", ".\n");
	    content = content.replace(";", ".\n");
	    
	    content = content.replace(".", "\n . \n");
	    
	    content = content.replaceAll("\\n?\\s,", ""); 
	    
		return content;
	}
	
}
