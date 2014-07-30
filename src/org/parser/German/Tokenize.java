package org.parser.German;
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
		String stopWords = MainParser.fileRead("data/stop-words-german.txt");
		
		String except[]= stopWords.split("\\r?\\n");	
	//	String outExcept = "List words excepted from text:\n\n";
       
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
				//	outExcept += motfinal + "\n";
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
	    content = content.replace(",", "\n , \n");
	    content = content.replaceAll("\\n?\\s,", ""); 
	    content = content.replaceAll("[^0-9a-zA-Z \\.-äüöß]", "");
		return content;
	}
	
}
