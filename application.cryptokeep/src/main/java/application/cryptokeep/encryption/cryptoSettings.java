package application.cryptokeep.encryption;
import java.util.*;
import java.io.*;
import javax.swing.*;

import application.cryptokeep.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.LayoutManager;
import java.awt.Desktop.*;

public class cryptoSettings extends JFrame{

    protected volatile passObject pobj;

    private JPanel panel1;
    private JPanel panel2;
    //private JPanel panel3;

    private JLabel label;
    //private JButton adder;
    //The options
    private JButton fav;
    private JButton lnk;
    private JButton grp1;
    private JButton grp2;
    
    // Username for setting method
    
    private String data;

    public cryptoSettings(String data) throws ClassNotFoundException, IOException{

    	
    	this.data = data;
        this.setTitle("Wizard");
        this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
		
        CreateObject();
        setPanel1();
        setPanel2();

        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.CENTER);

		this.setResizable(false);
		this.setVisible(true);

    }

    // SETTER FOR USERNAME!!
    
    public void setUsername(String data) {
    	this.data = data;
    }
    
    public String getUsername() {
    	return data;
    }
    
    public void setPanel1(){

        panel1 = new JPanel();
        label = new JLabel("Please select an option from below: ");
        panel1.add(label);
    }

    public void setPanel2(){

        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2,2));

        fav = new JButton("Add to Favorites");
        fav.addActionListener(new addfav());
        
        lnk = new JButton("Add A Link");
        lnk.addActionListener(new addlink());
        
        grp1 = new JButton("Add to Group 1");
        grp1.addActionListener(new addgrp1());

        grp2 = new JButton("Add to Group 2");
        grp2.addActionListener(new addgrp2());

        panel2.add(fav);
        panel2.add(lnk);
        panel2.add(grp1);
        panel2.add(grp2);

    }

    public void CreateObject() throws IOException, ClassNotFoundException{

        FileInputStream fstream = new FileInputStream(new File("pobj.ser"));
        ObjectInputStream ostream = new ObjectInputStream(fstream);
        pobj = (passObject)ostream.readObject();
        pobj.getUname().add(this.data);

    }



    // ActionListeners::

    private class addlink implements ActionListener{

		public void actionPerformed(ActionEvent e){

			String linkSTR = JOptionPane.showInputDialog(null, "Please Enter the Link you would like to store:", "www.example.com");
			try {
				new ADV_IO().fileOpen("links.txt", linkSTR + "\n" + data);
                pobj.getLinkS().add(linkSTR);
                pobj.getLinkS().add(data);
                new ADV_IO().storeObject(pobj, new File("pobj.ser"));
				
				
				
				JOptionPane.showMessageDialog(null, "link added successfully", "ALERT", JOptionPane.INFORMATION_MESSAGE);
            

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}


    private class addfav implements ActionListener{

        public void actionPerformed(ActionEvent e){

            try{

                new ADV_IO().fileOpen("favorites.txt", data);
                //new PassMan().setUserList(new PassMan().getUsername());
                //new PassMan().setModelList(new PassMan().getUsername());

                pobj.getFavorites().add(data);
                new ADV_IO().storeObject(pobj, new File("pobj.ser"));

                JOptionPane.showMessageDialog(null, "Favorite added successfully", "ALERT", JOptionPane.INFORMATION_MESSAGE);
       
            }
            catch(FileNotFoundException e1){

                e1.printStackTrace();

            } catch (IOException e1) {

                e1.printStackTrace();

            }

        }

    }

    private class addgrp1 implements ActionListener{

        public void actionPerformed(ActionEvent e){

            try{

                new ADV_IO().fileOpen("g1.txt", data);

                pobj.getGroup11().add(data);
                new ADV_IO().storeObject(pobj, new File("pobj.ser"));


                //new PassMan().setUserList(new PassMan().getUsername());
                //new PassMan().setModelList(new PassMan().getUsername());
                JOptionPane.showMessageDialog(null, "Favorite added successfully", "ALERT", JOptionPane.INFORMATION_MESSAGE);
        
            }
            catch(FileNotFoundException e1){

                e1.printStackTrace();

            } catch (IOException e1) {

                e1.printStackTrace();

            }

        }

    }
    private class addgrp2 implements ActionListener{

        public void actionPerformed(ActionEvent e){

            try{

                new ADV_IO().fileOpen("g2.txt", data);

                pobj.getGroup22().add(data);
                new ADV_IO().storeObject(pobj, new File("pobj.ser"));

                //new PassMan().setUserList(new PassMan().getUsername());
                //new PassMan().setModelList(new PassMan().getUsername());
                JOptionPane.showMessageDialog(null, "Favorite added successfully", "ALERT", JOptionPane.INFORMATION_MESSAGE);
       
            }
            catch(FileNotFoundException e1){

                e1.printStackTrace();

            } catch (IOException e1) {

                e1.printStackTrace();

            }

        }

    }

    /*
    public static void main(String[] args) {
        new cryptoSettings();
    }
    */

}
