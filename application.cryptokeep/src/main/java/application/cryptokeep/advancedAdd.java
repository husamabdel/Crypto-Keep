package application.cryptokeep;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.ReverbType;
import javax.swing.*;

import application.cryptokeep.encryption.crypto;
import application.cryptokeep.encryption.cryptoSettings;
import application.cryptokeep.encryption.passObject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Desktop.*;
import java.awt.event.*;
import java.util.*;
import java.security.*;
import java.util.Base64;

public class advancedAdd  extends JFrame{

    //Pass Object to serialize after data is inputted:

    protected volatile passObject pobj;
    
    private JPanel panel;
    private JLabel label;
    private JLabel uname;
    private JLabel alia;
    private JLabel pas;
    private JTextField user;
    private JTextField alias;
    private JTextField pass;
    private JButton submit;
    
    public advancedAdd() throws ClassNotFoundException, IOException{

        this.setTitle("Advanced Add Options");
        this.setSize(225,250);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //this.pack();
        //this.setResizable(false);
        CreateObject();
        setPanel();
        this.add(panel);
        
        this.setVisible(true);

    }


    public void CreateObject() throws IOException, ClassNotFoundException{

        FileInputStream fstream = new FileInputStream(new File("pobj.ser"));
        ObjectInputStream ostream = new ObjectInputStream(fstream);
        pobj = (passObject)ostream.readObject();

    }

    public void setPanel(){

        panel = new JPanel();
        //panel.setLayout(new BorderLayout());
        label = new JLabel("Add an Alias to encrypt username!");
        uname = new JLabel("Username: ");
        alia = new JLabel("Alias: ");
        pas = new JLabel("Password: ");
        
        user = new JTextField(16);
        user.setSize(80, 20);
        uname.setLabelFor(user);
        alias = new JTextField(16);
        alias.setSize(80, 20);
        alia.setLabelFor(alias);
        pass = new JTextField(16);
        pass.setSize(80, 20);
        pas.setLabelFor(pass);

        submit = new JButton("Submit");
        submit.addActionListener(new SUBMIT());

        panel.add(label);
        panel.add(uname);
        panel.add(user);
        panel.add(alia);
        panel.add(alias);
        panel.add(pas);
        panel.add(pass);
        panel.add(submit);

    }

    public String getUsername(){
        return user.getText();
    }
    public String getPassword(){
        return pass.getText();
    }
    public String getAlias(){
        return alias.getText();
    }



    /////////////////////////////////////////////
    //
    //
    //
    //  imported from passman:::
    //
    //
    //
    /////////////////////////////////////////////




	private class thread2 extends Thread{

		@Override
		public void run(){

			try {
                new cryptoSettings(alias.getText());
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			

		}

	}

    public static byte [] GetProperKey() throws IOException, NoSuchAlgorithmException{

		byte[] HashToGet = Files.readAllBytes(Paths.get("default.dat"));
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(HashToGet);

	}






    // ActionListener for the button:

private class SUBMIT implements ActionListener{

    public void actionPerformed(ActionEvent e){

    









    		
    		String uname = user.getText();
    		String pwd = pass.getText();
            String ALIAS = alias.getText();
    		
    		try {

				int ans= JOptionPane.showConfirmDialog(null, "Would you like to add this to a group?", "ALERT", JOptionPane.YES_NO_CANCEL_OPTION);

				if(ans == 0 ){

					//Created as a separate thread to fix bugs with closing the tab.
					thread2 th = new thread2();
					th.start();

				}
				else if(ans == 1){
					JOptionPane.showMessageDialog(null, "Operation Cancelled", "ALERT", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(ans == 2){
					JOptionPane.showMessageDialog(null, "Operation Cancelled", "ALERT", JOptionPane.INFORMATION_MESSAGE);
				}

				crypto crypt = new crypto();

				byte[] encryptedUser = crypt.encrypt(uname.getBytes(), GetProperKey(), "def.txt");
				byte[] encryptedPass = crypt.encrypt(pwd.getBytes(), GetProperKey(), "def.txt");
				
				String getuBytes = Base64.getEncoder().encodeToString(encryptedUser);
				String getpBytes = Base64.getEncoder().encodeToString(encryptedPass);
				 ADV_IO io = new ADV_IO();
				 io.fileOpen("uname_list.txt", ALIAS);
				 io.fileOpen("usernames.txt", getuBytes);
				 io.fileOpen("passwords.txt", getpBytes);
                 pobj.getUname().add(ALIAS);
                 pobj.getUsernames().add(getuBytes);
                 pobj.getPasswords().add(getpBytes);
                 new ADV_IO().storeObject(pobj, new File("pobj.ser"));
				
				//KeyMap.put(uname, pwd);
				
				
			}
    		
    		catch (Exception e1) {
				
				e1.printStackTrace();
			
			}
    		
    		user.setText(null);
    		pass.setText(null);
    		alias.setText(null);
    	}










}


}
