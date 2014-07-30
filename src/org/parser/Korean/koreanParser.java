package org.parser.Korean;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.security.auth.login.Configuration;
import kaist.cilab.parser.berkeleyadaptation.BerkeleyParserWrapper;
import edu.berkeley.nlp.PCFGLA.BerkeleyParser;
import edu.berkeley.nlp.PCFGLA.Option;
import edu.berkeley.nlp.PCFGLA.OptionParser;
import edu.berkeley.nlp.PCFGLA.GrammarTrainer.Options;
import edu.berkeley.nlp.syntax.Tree;
import edu.berkeley.nlp.syntax.Trees.PennTreeReader;


	public class koreanParser {

	static String resultFor = "";
	public static void parserKorean(String content)
	{
		BerkeleyParserWrapper bpw = new BerkeleyParserWrapper("models/parser/KorGrammar_BerkF_FIN");
		String text = content; //readFile("input.txt");
		String res = bpw.parse(text);
		writeFile("data/Korean_Output.txt", res);
		try {
            PennTreeReader treeReader = new PennTreeReader(
                            new InputStreamReader(new FileInputStream("Korean_Output.txt"),
                                            Charset.forName("UTF-8")));// GB18030")));
            while (treeReader.hasNext()) {
                    Tree<String> rootedTree = treeReader.next();
                    // if (rootedTree.getChildren().size()>1)
                    // System.err.println(rootedTree);
                    if (rootedTree.getLabel().equals("ROOT"))
                            rootedTree = rootedTree.getChildren().get(0);
                    printDependencies(rootedTree);
            }
            if(!(resultFor.equals("")))
            {
            	writeFile("data/Korean_conllResult.txt", resultFor);
            }

    } catch (Exception ex) {
            ex.printStackTrace();
    }


	}
	
	// Чтение из файла
	private String readFile(String pathName)
	{
		String result = "";
        BufferedReader input = null;

        try 
        {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(pathName), "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();  
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        try {
            String tmp;
            while ((tmp = input.readLine()) != null){
                result += tmp;
                result += "\n";
            }
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try 
        {
            input.close();
        } catch (IOException e) 
        {
            e.printStackTrace(); 
        }
        
        return result;
	}
	
	// Запись в файл
	private static void writeFile(String pathName, String content)
	{
		try
        {
            OutputStream f = new FileOutputStream(pathName, false);
            OutputStreamWriter writer = new OutputStreamWriter(f, "UTF-8");
            BufferedWriter out = new BufferedWriter(writer);
            out.write(content);
            out.flush();
            f.close();
            writer.close();
        }
        catch(IOException ex)
        {
            System.err.println(ex);
        }
	}
	/**
     * @param sentence
     */
    private static void printDependencies(Tree<String> tree) {
            // System.out.println(tree);
            if (tree.getYield().size() <= 1) {
                    System.out.println(0 + "\t_\t_\t_");
                    return;
            }
            int thisHead = findHead(tree);
            int nWordsFound = printDependencies(tree, thisHead, 0, 0);
            int nWords = tree.getYield().size();
            while (nWords < nWordsFound) {
                    nWordsFound++;
                    System.out.println(0 + "\t_\t_\t_");
                    System.err.println("too short");
            }
            System.out.println("");
    }

    private static int printDependencies(Tree<String> tree, int parent,
                    int previousWords, int parentOfParent) {
            for (Tree<String> child : tree.getChildren()) {
                    if (previousWords == parent - 1) { // we are at the parent of this
                                                                                            // (sub)tree
                    		resultFor += parentOfParent + //"\t_\t_\t_");
                             (previousWords+1) +
                             "\t" + child.getChildren().get(0).getLabel() +
                             "\t" + child.getLabel()+                           
                             "\t" + parentOfParent + "\n";
                            if (child.getYield().size() > 1)
                                    System.err.println(child);
                            previousWords++;
                    } else if (child.isPreTerminal()) {
                    		resultFor += parent +
                             (previousWords) +
                             "\t" + child.getChildren().get(0).getLabel() +
                             //"\t" + child.getChildren().get(0).get
                             "\t" + child.getLabel()+
                             "\t" + parent + "\n";
                            previousWords++;
                    } else {
                            int thisHead = previousWords + findHead(child);
                            printDependencies(child, thisHead, previousWords, parent);
                            previousWords += child.getYield().size();
                    }
            }
            return previousWords;

    }

    /**
     * @param tree
     * @return
     */
    private static int findHead(Tree<String> tree) {
            String headLabel = tree.getLabel();
            headLabel = headLabel.substring(0, headLabel.length() - 1);// cut off
                                                                                                                                    // the *
            int headIndex = -2;
            int previousWords = 0;
            for (Tree<String> child : tree.getChildren()) {
                    if (child.isPreTerminal() && child.getLabel().equals(headLabel)) { // found
                                                                                                                                                            // a
                                                                                                                                                            // potential
                                                                                                                                                            // head
                            headIndex = previousWords;
                            previousWords++;
                    } else
                            previousWords += child.getYield().size();
            }
            return headIndex + 1; // +1 since indices start with 1
    }

}
