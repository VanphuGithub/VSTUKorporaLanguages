package org.parser.Spanish;
import static java.util.Arrays.asList;
import java.util.ArrayList;
import java.util.List;

public class conllSpanish {
	private static String tab = "\t";
	private static String mag = "\n";
	
	private static List<String> convertToConllSpanish(List<String> list)
	{
		List<String> res = new ArrayList<String>();
		
		int i = 1;
		for (String line : list)
		{
			String [] words = line.split(tab);
			if(words.length >= 3)
			{
				
				String conll = "";
				
				conll += Integer.toString(i);
				conll += tab;		
				
				conll += words[0];
				conll += tab;	
				
				conll += words[2];
				conll += tab;	
				
				conll += words[1];
				conll += tab;	
				
				conll += words[1];
				conll += tab;	

				conll += "_";
				
				res.add(conll);
				
	            res.add(mag);
	            i++;
			}
		}
		
		return res;
	}

	public static String getConllFromString(String outputContent) {
		// TODO Auto-generated method stub
		String [] listLematize = outputContent.split(mag);
		String res = "";
		List<String> arrList = convertToConllSpanish(asList(listLematize));
		for (String list : arrList)
		{
			res += list;
			System.out.print(res);
		}
		
		return res;
	}


}
