import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.parser.French.*;
import org.parser.Korean.koreanParser;
import org.parser.German.*;

enum Languages { FRENCH, GERMAN, KOREAN, SPANISH }

public class Main extends JFrame {

	private JTextArea textEditOut;
    private JFrame frame;
    
    public static String currentText = "";
    public static Languages currentLanguages = Languages.FRENCH;
    
	public static void main(String[] args)
	{
	
		 EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    Main window = new Main();
	                    window.frame.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	}

	public Main()
	{		
		 initialize();
	}

	 private void initialize() 
	{
		frame = new JFrame();
        frame.setBounds(100, 100, 1000, 591);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);
        
        Font font = new Font("Verdana", Font.PLAIN, 11);
        
        JMenuBar menuBar = new JMenuBar();
         
        JMenu fileMenu = new JMenu("Меню");
        fileMenu.setFont(font);
        
        JMenuItem openFile = new JMenuItem("Загрузить файл с текстом");
        openFile.setFont(font);
        fileMenu.add(openFile);
        openFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FileDialog fd = new FileDialog(frame, "Выбрать текстовый файл ", FileDialog.LOAD);
				fd.setFile("*.txt");
				fd.setVisible(true);
				String filename = fd.getDirectory() + fd.getFile();
				if (filename == null)
				  System.out.println("Вы не выбрал ни один файл");
				else
				{
					currentText = fileRead(filename);
					textEditOut.setText(currentText);
					
				}
			}
		});
        
        JMenuItem exitItem = new JMenuItem("Выход из системы");
        exitItem.setFont(font);
        fileMenu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);       
			}
		}); 
        
        // Меню для выборки языка 
        JMenu fileMenu1 = new JMenu("Выбор языка");
        fileMenu1.setFont(font);
        
        JMenuItem menuFrench = new JMenuItem("Французский язык");
        menuFrench.setFont(font);
        fileMenu1.add(menuFrench);
        menuFrench.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentLanguages = Languages.FRENCH;
			}
		});
        
        JMenuItem menuGerman = new JMenuItem("Немецкий язык");
        menuGerman.setFont(font);
        fileMenu1.add(menuGerman);
        menuGerman.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentLanguages = Languages.GERMAN;
			}
		});
        
        JMenuItem menuKorean = new JMenuItem("Корейский язык");
        menuKorean.setFont(font);
        fileMenu1.add(menuKorean);
        menuKorean.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentLanguages = Languages.KOREAN;
			}
		});
        
        JMenuItem menuSpanish = new JMenuItem("Испанский язык");
        menuSpanish.setFont(font);
        fileMenu1.add(menuSpanish);
        menuSpanish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentLanguages = Languages.SPANISH;
			}
		});
        
        menuBar.add(fileMenu);
        menuBar.add(fileMenu1);
        
        frame.setJMenuBar(menuBar);	
        frame.setVisible(true);
        
        JButton btnNewButton_1 = new JButton("Парсинг текста");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try
            	{
            		processing();
            	}
            	catch(IOException e)
            	{
            		
            	}
            }
        });
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton_1.insets = new Insets(0, 0, 15, 15);
        gbc_btnNewButton_1.gridx = 12;
        gbc_btnNewButton_1.gridy = 5;
        frame.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.gridheight = 10;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 2;
        gbc_scrollPane.gridy = 0;
        frame.getContentPane().add(scrollPane, gbc_scrollPane);

        textEditOut = new JTextArea();
        scrollPane.setViewportView(textEditOut);
        textEditOut.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
	}
	// read file
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
	 
	// processing parsing languages
	private void processing()throws IOException
	{
		switch(currentLanguages)
		{
			case FRENCH:
				// processing french
				org.parser.French.MainParser.AcceptFrench(currentText);
				break;
			case GERMAN:
				// processing german
				org.parser.German.MainParser.AcceptGerman(currentText);
				break;
			case KOREAN:
				// processing korean 
				koreanParser.parserKorean(currentText);
				break;
			case SPANISH:
				// processing spanish
				org.parser.Spanish.MainParser.AcceptSpanish(currentText);
				break;
			default:
				break;
		}
	}
	 
}
