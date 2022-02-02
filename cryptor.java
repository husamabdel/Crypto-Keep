import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Desktop.*;
import java.awt.event.*;
import java.util.*;
import java.security.*;

public class cryptor extends JFrame{
    
    private String select;
    private File file;
    private SecretKeySpec key;
    private byte [] content;
    private JButton button;
    private JButton button2;
    private JButton button3;
    private JLabel label;
    private JPanel panel;
    private String filename;

    // Constructor

    public cryptor(SecretKeySpec key){

        this.key = key;
        this.setTitle("File Encrption");
        this.setSize(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panels();
        this.add(panel);
        this.setVisible(true);

    }

    public cryptor()throws Exception{


        this.setTitle("File Encrption");
        this.setSize(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panels();
        this.add(panel);
        this.setVisible(true);

    }



    public void setKey(SecretKeySpec key){

        this.key = key;

    }


    //sets the contents
    public void panels(){

        panel = new JPanel();
        label = new JLabel("Select a file and hit encrypt");
        button = new JButton("select file");
        button.addActionListener(new getfile());
        button2 = new JButton("encrypt");
        button2.addActionListener(new writeBytesToFile());
        button3 = new JButton("decrypt");
        button3.addActionListener(new decrypt());

        panel.add(label);
        panel.add(button);
        panel.add(button2);
        panel.add(button3);

    }
    
    //sets the file names
    //public static void setFileName(){

        //file = new File(fileSelected());

    //}

    //gets the byte array of the file
    public byte[] getFileByte()throws Exception{


        return content = Files.readAllBytes(file.toPath());

    }

    
    
    //public static void SPEC()throws Exception{

        //String k = JOptionPane.showInputDialog("Please enter a key:");
        //File f = new File("C:\\Packages\\def.dat");
        //Scanner scan = new Scanner(f);
        //int x = 0;
        //String b = scan.next();
        //key = new SecretKeySpec(k.getBytes(), "AES");
        

    //}



    //encrypts the file, called in the next one
    public byte[] encryptFile(byte[] fileContent)throws Exception{

        fileContent = content;

        //KeyGenerator.getInstance("AES").generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(content);

    }

    public byte[] decryptFile(byte[] fileContent)throws Exception{

        fileContent = content;

         //KeyGenerator.getInstance("AES").generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(content);

    }

    //writes bytes to the new file.
    
private class writeBytesToFile implements ActionListener{
    public void actionPerformed(ActionEvent e){

    try{
        DataOutputStream stream = new DataOutputStream( new FileOutputStream(file.getAbsolutePath()+".PWNED"));
        for(int x = 0; x < getFileByte().length; x++){
            stream.writeByte(encryptFile(getFileByte())[x]);
        }
        stream.close();
        file.delete();
        }

        catch(Exception e1 ){
            e1.printStackTrace();
        }

    }

}

private class decrypt implements ActionListener{

    public void actionPerformed(ActionEvent e){
        try{

            filename = JOptionPane.showInputDialog("here", "Please enter a new name for the file WITH FILE EXTENSION");
            DataOutputStream stream = new DataOutputStream( new FileOutputStream(filename));
            for(int x = 0; x < getFileByte().length; x++){
                stream.writeByte(decryptFile(getFileByte())[x]);
            }
            stream.close();
            file.delete();
            }
    
            catch(Exception e1 ){
                e1.printStackTrace();
            }
    
        }
}


    //gets the file name.

    private class getfile implements ActionListener{
    
        public void actionPerformed(ActionEvent e){

            try{
        JFileChooser choose = new JFileChooser();
        int res = choose.showOpenDialog(null);
        if(res == JFileChooser.APPROVE_OPTION){
        select = choose.getSelectedFile().getAbsolutePath();
        file = new File(select);
            }
        }   
        catch(Exception e1){
            e1.printStackTrace();
        }
    
    }
}






    //public static void main(String[] args)throws Exception{
        
            //new cryptor();

    //}

}
