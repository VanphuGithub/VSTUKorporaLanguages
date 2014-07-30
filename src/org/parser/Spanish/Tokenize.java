package org.parser.Spanish;
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
		content = SegmentSpanish(content);
		List<String> res = new ArrayList<String>();
		boolean test = true;
		String stopWords = MainParser.fileRead("data/stop-words-spanish.txt");
		
		String except[]= stopWords.split("\\r?\\n");	
		String outExcept = "List words excepted from text:\n\n";
		
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
					outExcept += motfinal + "\n";
	            }
			}
			if(test)
			{
				res.add(motfinal);
			}
		}
		MainParser.fileWrite("test-stopwords.txt", outExcept);
		return res;
		
	}
	
	private static String SegmentSpanish(String content)
	{
		content = content.replace("?", " ");
	    content = content.replace(":", ".\n");
	    content = content.replace(";", ".\n");
	    
	    content = content.replace(".", "\n . \n");
	    content = content.replace(",", "\n , \n");
	    content = content.replaceAll("\\n?\\s,", ""); 
	    
		return content;
	}
	
}
