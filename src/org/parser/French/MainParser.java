package org.parser.French;
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


public class MainParser {
	
	
	public static void AcceptFrench(String contentInput)
	{
		String modelNameMalt = "fremalt-1.7";
		String InputContent = contentInput; //fileRead("input.txt");
		String pathToModelPar = "treeTagger/french.par";
		String pathToTreeTagger = "treeTagger";
		String OutputContent = TreeTaggers.treeTaggerFrench(pathToTreeTagger, pathToModelPar, InputContent);
		//fileWrite("output.txt", OutputContent);
		String conllOut = conllFrench.getConllFromString(OutputContent);
		fileWrite("data/French_conllOut.txt", conllOut);
		String maltParserRes = maltParser.maltParserFrench(modelNameMalt, conllOut);
		fileWrite("data/French_output.txt", maltParserRes);
	}
	
	
// Запись в файл
	public static void fileWrite(String fileName, String content){
        try
        {
            OutputStream f = new FileOutputStream(fileName, false);
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

	// Чтение из файла
    public static String fileRead(String fileName){
        String result = "";
        BufferedReader input = null;

        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            String tmp;
            while ((tmp = input.readLine()) != null){
                result += tmp;
                result += "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }
}
