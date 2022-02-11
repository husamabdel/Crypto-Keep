import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.LayoutManager;
import java.awt.Desktop.*;

public class cryptoSettings extends JFrame{

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

    public cryptoSettings(){

        this.setTitle("Wizard");
        this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
		
        setPanel1();
        setPanel2();

        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.CENTER);

		this.setResizable(false);
		this.setVisible(true);

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



    // ActionListeners::

    private class addlink implements ActionListener{

		public void actionPerformed(ActionEvent e){

			String linkSTR = JOptionPane.showInputDialog(null, "Please Enter the Link you would like to store:", "www.example.com");
			try {
				new ADV_IO().fileOpen("links.txt", linkSTR);
				JOptionPane.showMessageDialog(null, "link added successfully", "ALERT", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}


    private class addfav implements ActionListener{

        public void actionPerformed(ActionEvent e){

            try{

                new ADV_IO().fileOpen("favorites.txt", new PassMan().getUsername());
                //new PassMan().setUserList(new PassMan().getUsername());
                //new PassMan().setModelList(new PassMan().getUsername());
                JOptionPane.showMessageDialog(null, "Favorite added successfully", "ALERT", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
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

                new ADV_IO().fileOpen("g1.txt", new PassMan().getUsername());
                //new PassMan().setUserList(new PassMan().getUsername());
                //new PassMan().setModelList(new PassMan().getUsername());
                JOptionPane.showMessageDialog(null, "Favorite added successfully", "ALERT", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
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

                new ADV_IO().fileOpen("g2.txt", new PassMan().getUsername());
                //new PassMan().setUserList(new PassMan().getUsername());
                //new PassMan().setModelList(new PassMan().getUsername());
                JOptionPane.showMessageDialog(null, "Favorite added successfully", "ALERT", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
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