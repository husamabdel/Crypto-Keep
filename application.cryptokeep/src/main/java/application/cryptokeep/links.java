package application.cryptokeep;
import java.util.Scanner;
import javax.swing.*;

import application.cryptokeep.encryption.passObject;

import java.awt.*;
import java.awt.Desktop.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
public class links extends JFrame{

	protected passObject pobj;

	private JList list;
	private JScrollPane pane;
	private JButton button;
	private JLabel label;
	private JPanel panel;
	private DefaultListModel <String> model = new DefaultListModel<String>();
	private DefaultListModel <String> users = new DefaultListModel<String>();

	public links() throws ClassNotFoundException, IOException {
		
		CreateObject();
		addToModel();
		setPanel();
		
		this.setTitle("Links");
		this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.add(panel);
		
		this.setVisible(true);
		
	}



	
    public void CreateObject() throws IOException, ClassNotFoundException{

        FileInputStream fstream = new FileInputStream(new File("pobj.ser"));
        ObjectInputStream ostream = new ObjectInputStream(fstream);
        pobj = (passObject)ostream.readObject();

    }



	
	public void setPanel() {
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		pane = new JScrollPane();
		list = new JList(model);
		pane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		label = new JLabel("Select an option and click launch when ready");
		label.setLocation(0, 0);
		
		button = new JButton("Launch");
		button.addActionListener(new buttonListener());
		
		panel.add(label, BorderLayout.NORTH);
		panel.add(pane, BorderLayout.CENTER);
		panel.add(button, BorderLayout.SOUTH);
		
	}
	
	
	
	public void addToModel() throws FileNotFoundException {
		
		File file = new File("links.txt");
		Scanner scan = new Scanner(file);
		
		ArrayList <String> linkList = pobj.getLinkS();

		for(String link : linkList) {
			
		model.addElement(link);	

			
		}
		
		for(int x = 0; x < model.size(); x++) {
			
			if(x%2 != 0 || x==0) {
			users.addElement(model.get(x));
			model.remove(x);
			}
		
		}
		scan.close();
	
	}
	
	private class buttonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			Desktop desk = Desktop.getDesktop();
			try {
				
				URI uri = new URI(users.get(list.getSelectedIndex()));
				desk.browse(uri);
				
			} 
			
			catch (URISyntaxException e1) {
				
				e1.printStackTrace();
				
			} catch (IOException e1) {
				
				e1.printStackTrace();
				
			}
			
		}
		
	}
	
	
	
}
