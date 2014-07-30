package org.parser.French;
import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;

public class conllFrench {
	
	private static String tab = "\t";
	private static String mag = "\n";
	public static String getConllFromString(String content)
	{
		String [] listLematize = content.split(mag);
		String res = "";
		List<String> arrList = convertToConllFrench(asList(listLematize));
		for (String list : arrList)
		{
			res += list;
			System.out.print(res);
		}
		
		return res;
		
	}

	private static List<String> convertToConllFrench(List<String> list)
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
}
