import java.util.Scanner;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.desktop.*;
import java.awt.event.*;
import java.util.*;
import java.security.*;

public class PassMan extends JFrame{

	private JScrollPane scroll;
	private JMenuBar bar;
	private JList list;
	private JTextArea area;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JTextField textPass;
	private JTextField textUser;
	private JScrollPane pane;
	private JButton button;
	private JButton add;
	private JButton link;
	private JButton group;
	private JButton group2;
	private JButton reveal;
	private JFileChooser filer;
	private String filename = "default.dat";
	private File mainFile;
	private HashMap<String, String> KeyMap;
	private ArrayList <String> usernames = new ArrayList<>();
	private ArrayList <String> passwords = new ArrayList<>();
	


	public PassMan() {
		
		this.setTitle("Pass Man");
		this.setSize(850,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.pack();
		this.setResizable(false);
		this.setVisible(true);		
		
	}
	
    ///////////////////////////////////////////////////////////////////////
    //
    //				GUI INITIALIZATION FUNCTIONS FUNCTIONS::
    //			
    ///////////////////////////////////////////////////////////////////////
	
	public void setMenuBar() {
		
		bar = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		JMenuItem item = new JMenuItem("file");
		menu1.add(item);
		
	}
	
	//Groups panel.
	public void panel1Set() {
		
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0,4));
		button = new JButton("favorites");
		
	}
	
	//Adding Passwords Panel
	public void panel2Set() {
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		
		textUser = new JTextField();
		textUser.setBounds(0, 0, 150, 30);
		
		
		textPass = new JTextField();
		textPass.setBounds(0, 0, 150, 30);
		
		add = new JButton("Add");
		
		
	}
	
	//View and decrypt panel
	public void panel3Set(){
	
		panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		
		reveal = new JButton("Reveal");
		
	
}
	
	
    ///////////////////////////////////////////////////////////////////////
    //
    //				##STARTUP FUNCTIONS::
    //
    ///////////////////////////////////////////////////////////////////////
	
	
	
	//Determine if the data file exists or not.
	public boolean flag(){
		
		File file = new File(filename);
		
		if(file.exists()) {
			return true;
		}
		return false;
	}
	//start a new file if data is not set
	
	public void Setup() throws IOException {
		
		JOptionPane.showMessageDialog(null,"Welcome to Pass Man Pro, the setup process will now begin","Setup.exe",JOptionPane.OK_OPTION);
		JOptionPane.showMessageDialog(null,"A new file has been created!","FileNotFound",JOptionPane.OK_OPTION);
		ADV_IO stream = new ADV_IO();
		stream.filStartBin(null);
		stream.fileStart(null);
		PrintWriter UsernameFile = new PrintWriter("usernames.txt");
		PrintWriter PasswordFile = new PrintWriter("passwords.txt");
		UsernameFile.close();
		PasswordFile.close();
		
	}
	
	

    
    public void load_elements() throws FileNotFoundException {
    	
    	DataInputStream stream = new DataInputStream(new FileInputStream(filename));
    	Scanner input = new Scanner(stream);
    	while(input.hasNextByte()){
    		
    		
    	}
  
    	
    }
    
    //Initializing the data structures, this method is called second. #2
    public void load_txt_elements() throws FileNotFoundException{
    	
    	//Load username file..
    	File read = new File("usernames.txt");
    	Scanner Input = new Scanner(read);
    	while(Input.hasNextLine()) {
    		usernames.add(Input.nextLine());
    	}
    	Input.close();
    	//Load Password File...
    	File pass = new File("passwords.txt");
    	Scanner passIn = new Scanner(pass);
    	while(passIn.hasNextLine()) {
    		passwords.add(passIn.nextLine());
    	}
    	passIn.close();
    	//Add HashMap Data Structure.
    	
    	
    	for(int x = 0; x < passwords.size() && x < usernames.size(); x++){
    		
    		KeyMap.put(usernames.get(x), passwords.get(x));
    		
    		}
    	
    }
    
    
    ///////////////////////////////////////////////////////////////////////
    //
    //				##ENCRYPTION AND DECRYPTION FUNCTIONS::
    //				1. Generate secret key from text.
    //				2. Encrypt with AES.
    //				3. return byte[] of ecrypted elements.
    //				
    //							
    //
    ///////////////////////////////////////////////////////////////////////
    	
    public SecretKeySpec returner(String key)throws NoSuchAlgorithmException{

        SecretKeySpec keyspec;
        keyspec = new SecretKeySpec(key.getBytes(), "AES");
        return keyspec;
        
    }
	
    public byte[] encrypt(String TEXT, String key)throws Exception{
        
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec spec = returner(key);
        byte[] enc = TEXT.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, spec);
        byte[] after = cipher.doFinal(enc);
        
        return after;
    
    }
    
    
	
	
	
}









