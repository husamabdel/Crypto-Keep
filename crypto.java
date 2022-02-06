import java.util.Scanner;
import java.io.*;
import java.math.BigInteger;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
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


	private byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private byte[] specs;
    private IvParameterSpec ivspec = new IvParameterSpec(iv);

	//Used to convert encoded String key from file into actual key.
    public SecretKeySpec returner(String key)throws NoSuchAlgorithmException{

        SecretKeySpec keyspec;
        byte[] decodedBytes = Base64.getDecoder().decode(key.getBytes());
        keyspec = new SecretKeySpec(Arrays.copyOf(decodedBytes,16), "AES");
        
        return keyspec;
        
    }
    
    public SecretKeySpec returner2(byte[] key)throws NoSuchAlgorithmException{

        SecretKeySpec keyspec;
        keyspec = new SecretKeySpec(key, "AES");
        
        return keyspec;
        
    }


    //Generates secret key, can be stored in main class
    public SecretKey keyGen(){

        try {
            
        	KeyGenerator g = KeyGenerator.getInstance("AES");
        	g.init(128);
        	
            SecretKey gen = g.generateKey();

            return gen;
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"NoSuchAlgorithmException",JOptionPane.ERROR_MESSAGE);
            
        }
        return null;

    }

	//encryption method.
    public byte[] encrypt(byte[] TEXT, byte[] key, String filename)throws Exception{
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec spec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, spec, ivspec);
        byte[] after = cipher.doFinal(TEXT);
        
        return after;
    
    }
    //get key from data file
    public String getKey(String filename) throws FileNotFoundException, NoSuchAlgorithmException {
    	
    	File file = new File(filename);
    	Scanner fileStream = new Scanner(file);
    	
        //String[] spec = new String[5];

        //int x = 0;
    	//while(fileStream.hasNextLine()) {
    	    String spec = fileStream.nextLine();
            //x++;
    	//}
        fileStream.close();
    	return spec;
    
    }
    
    public byte[] getKeyByte(String filename) throws FileNotFoundException, NoSuchAlgorithmException {
    	
    	File file = new File(filename);
    	Scanner fileStream = new Scanner(file);
    	
        int x = 0;
    	while(fileStream.hasNext()) {
    	    specs[x] = fileStream.nextByte();
            x++;
    	}
        fileStream.close();
    	return specs;
    
    }
    
    // decryption takes place here...
    public byte[] decrypt(byte[] TEXT, byte[] key, String filename)throws Exception{
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec spec = new SecretKeySpec(key, "AES");
        
        cipher.init(Cipher.DECRYPT_MODE, spec, ivspec);
        byte[] after = cipher.doFinal(TEXT);
        
        return after;
    
    }

    //generate random password.

    public String GenPass() throws NoSuchAlgorithmException {
    	
    	Random rand = new Random();
    	
    	int num = rand.nextInt();
    	
    	BigInteger num2 = BigInteger.valueOf(num);
   
    	MessageDigest hash = MessageDigest.getInstance("SHA-256");
    	byte [] toBeRandom = Base64.getEncoder().encode(hash.digest(num2.toByteArray()));
    	String randomPassword = new String(toBeRandom);
    
    	return randomPassword;
    	
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
