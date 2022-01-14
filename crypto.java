import java.util.Scanner;
import java.io.*;
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

public class crypto {



    //Used to convert encoded String key from file into actual key.
    public SecretKeySpec returner(String key)throws NoSuchAlgorithmException{

        SecretKeySpec keyspec;
        keyspec = new SecretKeySpec(key.getBytes(), "AES");
        return keyspec;
        
    }


    //Generates secret key, can be stored in main class
    public SecretKey keyGen(){

        try {
            
            SecretKey gen = KeyGenerator.getInstance("AES").generateKey();

            return gen;
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"NoSuchAlgorithmException",JOptionPane.ERROR_MESSAGE);
            
        }
        return null;

    }

	//encryption method.
    public byte[] encrypt(String TEXT, String key, String filename)throws Exception{
        
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec spec = returner(getKey(filename));
        byte[] enc = TEXT.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, spec);
        byte[] after = cipher.doFinal(enc);
        
        return after;
    
    }
    //get key from data file
    public String getKey(String filename) throws FileNotFoundException, NoSuchAlgorithmException {
    	
    	File file = new File(filename);
    	Scanner fileStream = new Scanner(file);
    	
        String[] spec = new String[5];

        int x = 0;
    	while(fileStream.hasNextLine()) {
    	    spec[x] = fileStream.nextLine();
            x++;
    	}
        fileStream.close();
    	return spec[0];
    
    }
    
    // decryption takes place here...
    public byte[] decrypt(String TEXT, String key, String filename)throws Exception{
        
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec spec = returner(getKey(filename));
        byte[] dec = TEXT.getBytes();
        cipher.init(Cipher.DECRYPT_MODE, spec);
        byte[] after = cipher.doFinal(dec);
        
        return after;
    
    }

    /*
    public static void main(String[] args) throws Exception {
        

        PrintWriter outputfile = new PrintWriter("randomfile.txt");
        String k = new String(keyGen().getEncoded());
        outputfile.println(k);
        PrintWriter output = new PrintWriter("randfile.txt");
        output.println("this text should get encrypted");
        outputfile.close();
        output.close();

        File file = new File("randomfile.txt");
        File text = new File("randfile.txt");
        Scanner fileGetter = new Scanner(text);
        Scanner keyGetter = new Scanner(file);
        
        

            String enc = fileGetter.nextLine();
        
      
        
            String key = keyGetter.nextLine();
        
        System.out.println("The data: " + enc + "\t" + key);

        String s = new String(encrypt(enc, key, "randomfile.txt"));
        System.out.println("The encrypted text is: " + s);
        String c = new String(decrypt(s, key, "randomfile.txt"));
        System.out.println("The decrypted text is: " + c);
        fileGetter.close();
        keyGetter.close();




    }
    */

    
}
