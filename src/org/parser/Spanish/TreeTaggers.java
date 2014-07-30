package org.parser.Spanish;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;
import static java.util.Arrays.asList;


public class TreeTaggers {
  private static String symbolTab = "\t";
  private static String symbolMag = "\n";
  public static String treeTaggerGerman(String pathToTreeTagger, 
		  String pathToModelPar,
		  String content)
  {
	 String [] words = content.split("\\s+");
	 List<String> mywords =  new ArrayList<String>();
	 mywords = Tokenize.listTokens(content);
	 String res = "";
	 
	 List<String> listTree = treeTagger(mywords, pathToModelPar, pathToTreeTagger);
	 
	 for(String tmp : listTree)
	 {
		 res += tmp + symbolMag;
		 System.out.print(res);
	 }
	 return res;
  }
  
  
  private static List<String> treeTagger(List<String> content, String pathToModelPar, final String pathToTreeTagger)
  {
	  final List<String> res = new ArrayList<String>();
	  System.setProperty("treetagger.home", pathToTreeTagger);
		TreeTaggerWrapper tt = new TreeTaggerWrapper<String>();
		 try {
		     try {
				tt.setModel(pathToModelPar);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     tt.setHandler(new TokenHandler<String>() {
		         public void token(String token, String pos, String lemma) {
		            res.add(token + "\t" + pos + "\t" + lemma);
		         }
		     });
		     try {
				tt.process(content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TreeTaggerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 finally {
		     tt.destroy();
		 }
	  return res;
  }
}
