import java.util.Scanner;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.ReverbType;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Desktop.*;
import java.awt.event.*;
import java.util.*;
import java.security.*;
import java.util.Base64;

public class PassMan extends JFrame{

	
	private JMenuBar bar = new JMenuBar();
	
	private JList list;
	
	private JTextArea area;
	
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	
	private JTextField textPass;
	private JTextField textUser;
	private JTextField reveal_pass;
	private JTextField reveal_user;
	
	private JScrollPane pane;
	private JScrollPane scroll;
	
	
	private JButton button;
	private JButton add;
	private JButton link;
	private JButton group;
	private JButton group2;
	private JButton reveal;
	private JButton clear;
	private JButton random;
	
	private JFileChooser filer;
	private static String filename = "default.dat";
	
	//private File mainFile;
	
	private static HashMap<String, String> KeyMap;
	private static ArrayList <String> usernames = new ArrayList<>();
	private static ArrayList <String> passwords = new ArrayList<>();
	private static ArrayList <String> uname = new ArrayList<>();
	private static DefaultListModel<String> model = new DefaultListModel<>();
	private static String[] unames;


	public PassMan() {
		
		this.setTitle("Pass Man");
		this.setSize(450,520);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		panel1Set();
		setMenuBar1();
		panel2Set();
		panel3Set();
		this.add(panel1, BorderLayout.NORTH);
		this.setJMenuBar(bar);
		this.add(panel2, BorderLayout.SOUTH);
		this.add(panel3, BorderLayout.CENTER);
		//this.pack();
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
    ///////////////////////////////////////////////////////////////////////
    //
    //				GUI INITIALIZATION FUNCTIONS FUNCTIONS::
    //			
    ///////////////////////////////////////////////////////////////////////
	
	public void setMenuBar1() {
		
		
		JMenu FileM = new JMenu("File");
		JMenuItem fileE = new JMenuItem("File controls");
		JMenuItem item = new JMenuItem("Encrypt a file");
		item.addActionListener(new FILE_ENCRYPT());
		
		


		JMenuItem exit = new JMenuItem("exit");
		exit.addActionListener(new TASK_EXIT());

		JMenu help = new JMenu("Help");
		JMenuItem readme = new JMenuItem("Readme");


		FileM.add(fileE);
		FileM.add(item);
		FileM.add(exit);
		
		help.add(readme);

		bar.add(FileM);
		bar.add(help);
		
	}

	//Groups panel.

	public void panel1Set() {
		


		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0,4));
		button = new JButton("favorites");
		link = new JButton("Link");
		group = new JButton("Group 1");
		group2 = new JButton("Group 2");
		
		panel1.add(button);
		panel1.add(link);
		panel1.add(group);
		panel1.add(group2);
		
	}	
	
	//Adding Passwords Panel
	public void panel2Set() {
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());	
		
		reveal = new JButton("Reveal");
		reveal.addActionListener(new DECRYPT_USER_PASS());

		clear = new JButton("Clear");
		clear.addActionListener(new CLEAR_BUTTON());
		
		random = new JButton("Generate password");
		random.addActionListener(new RANDOM_PASS());
		
		panel2.add(clear);
		panel2.add(reveal);
		panel2.add(random);

	}
	
	//View and decrypt panel
	public void panel3Set(){
		
	

		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		panel3.setBackground(Color.WHITE);
		
		JLabel label = new JLabel();
		ImageIcon icon = new ImageIcon("passman.png");
		label.setIcon(icon);

		textUser = new JTextField(12);
		textUser.setText("username here");
		textPass = new JTextField(12);
		textPass.setText("password here");
		add = new JButton("Add");
		add.addActionListener(new ADD_AND_ENCRYPT());
		
		
		panel3.add(add);
		panel3.add(textUser);
		panel3.add(textPass);
		
		
		////////////////////////////////////////////
		
		
		
		
		//JLabel label = new JLabel();
		//label.setText("Username and Password");
		
		
    	list = new JList(model);
    	pane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		list.setVisibleRowCount(6);
		reveal_user = new JTextField(15);
		reveal_user.setEditable(false);
		reveal_pass = new JTextField(15);
		reveal_pass.setEditable(false);

		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(reveal_user, BorderLayout.NORTH);
		p.add(reveal_pass, BorderLayout.SOUTH );

		//panel3.add(reveal);
		//panel3.add(list);
		panel3.add(pane);
		panel3.add(p);
		panel3.add(label);
		//panel3.add(label, BorderLayout.CENTER);
		
}
	
	
    ///////////////////////////////////////////////////////////////////////
    //
    //				##STARTUP FUNCTIONS::
    //
    ///////////////////////////////////////////////////////////////////////
	
	
	
	//Determine if the data file exists or not This i called first in main.
	public static boolean flag(){
		
		File file = new File("passwords.txt");
		
		if(file.exists()) {
			return true;
		}
			else{return false;}
			
	}
	//start a new file if data is not set , this is called IF first function is false
	
		public static void Setup() throws Exception {
		
		JOptionPane.showMessageDialog(null,"Welcome to Crypto Keep Pro, the setup process will now begin","Setup.exe",JOptionPane.OK_OPTION);
		JOptionPane.showMessageDialog(null,"A new file has been created!","FileNotFound",JOptionPane.OK_OPTION);

		PrintWriter UsernameFile = new PrintWriter("usernames.txt");
		PrintWriter decrypted_unames = new PrintWriter("uname_list.txt");
		PrintWriter PasswordFile = new PrintWriter("passwords.txt");
		String pass = JOptionPane.showInputDialog("Please create a  Master key to proceed");

			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] passHash = md.digest(pass.getBytes());
			String finalPass = new String(passHash);

		
		//Key and Password storing
		ADV_IO keyStore = new ADV_IO();
	
			keyStore.filStartBin(finalPass, "default.dat");
			keyStore.fileStart(finalPass, "default.txt");
			crypto cKey = new crypto();
			byte[] bytekey = cKey.keyGen().getEncoded();
			String baseKey = Base64.getEncoder().encodeToString(bytekey);
			keyStore.fileStart(baseKey,"def.txt");
			keyStore.filStartBin(baseKey, "def.dat");
		
		
		UsernameFile.close();
		PasswordFile.close();
		decrypted_unames.close();
		
	}
	
	

    //not in use now.
    //public void load_elements() throws FileNotFoundException {
    	
    	//DataInputStream stream = new DataInputStream(new FileInputStream(filename));
    	//Scanner input = new Scanner(stream);
    	//while(input.hasNextByte()){
    		
    		
    	//}
  
    	
    //}
    
    //Initializing the data structures, this method is called second. #2
    public static void load_txt_elements() throws FileNotFoundException{
    	
    	//Load username file..
    	File read = new File("usernames.txt");
    	Scanner Input = new Scanner(read);
    	while(Input.hasNextLine()) {
    		try {
				usernames.add(Input.nextLine());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	File reader = new File("uname_list.txt");
    	Scanner Inputer = new Scanner(reader);
    	while(Inputer.hasNextLine()) {
    		try {
				uname.add(Inputer.nextLine());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	
    	//Load Password File...
    	File pass = new File("passwords.txt");
    	Scanner passIn = new Scanner(pass);
    	while(passIn.hasNextLine()) {
    		passwords.add(passIn.nextLine());
    	}
    	//Add HashMap Data Structure.
    	
    	while(Inputer.hasNextLine() && passIn.hasNextLine()) {
    		
    		KeyMap.put(Inputer.nextLine(), passIn.nextLine());
    		
    	}
    	
    	Inputer.close();
    	Input.close();
    	passIn.close();


    	
    	//for(int x = 0; x < passwords.size()-1 && x < usernames.size()-1; x++){
    		
    		//KeyMap.put(usernames.get(x), passwords.get(x));
    		
    		//}
    	
    }
    
    // THe function for the Jlist to fill.
    
    public static void load_list(){
    	
    	unames = new String[uname.size()*2];
    	for(int x = 0; x < uname.size(); x++) {
    		
    		model.addElement(uname.get(x));

    	}
    		
    }
    
    
    ///////////////////////////////////////////////////////////////////////
    //
    //				##ENCRYPTION AND DECRYPTION FUNCTIONS::
    //				1. Generate secret key from text.
    //				2. Encrypt with AES.
    //				3. return byte[] of encrypted elements.
    //
    ///////////////////////////////////////////////////////////////////////

	//get the key for the encryption and decryption functions.
    	
	public String load_key() throws FileNotFoundException{

		File file = new File("def.dat");
		Scanner scan = new Scanner(file);
		String key = new String(scan.next());
		scan.close();
		return key;

	}

	public String load_key_string() throws FileNotFoundException{

		File file = new File("def.txt");
		Scanner scan = new Scanner(file);
		String key = scan.nextLine();
		scan.close();
		return key;

	}

public static void auth() throws FileNotFoundException, NoSuchAlgorithmException{

		File file = new File("default.txt");
		Scanner scan = new Scanner(file);
		String pwd = scan.nextLine();

		while(true){
			String pass = JOptionPane.showInputDialog("Please enter Master key to proceed");
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] passHash = md.digest(pass.getBytes());
			String finalPass = new String(passHash);


			if(finalPass.equals(pwd)){
				break;
			}
			else{
				JOptionPane.showMessageDialog(null,"Error! bad Master Key!","ERR",JOptionPane.OK_OPTION);
			}
		}
		scan.close();


	}





    
    /*
    public static SecretKeySpec returner(String key)throws NoSuchAlgorithmException{

        SecretKeySpec keyspec;
        keyspec = new SecretKeySpec(key.getBytes(), "AES");
        return keyspec;
        
    }
	
    public byte[] encrypt(String TEXT, String key)throws Exception{
        
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec spec = returner(getKey());
        byte[] enc = TEXT.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, spec);
        byte[] after = cipher.doFinal(enc);
        
        return after;
    
    }
    //// GET BACK HEREE
    public String getKey() throws FileNotFoundException, NoSuchAlgorithmException {
    	
    	File file = new File("default.txt");
    	Scanner fileStream = new Scanner(file);
    	
    	while(fileStream.hasNextLine()) {
    	String spec = fileStream.nextLine();
    	if(!fileStream.hasNextLine()) {
    		
        	return spec;
    		
    		}
    	}
    	return null;
    
    }
    
    
    public byte[] decrypt(String TEXT, String key)throws Exception{
        
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec spec = returner(getKey());
        byte[] dec = TEXT.getBytes();
        cipher.init(Cipher.DECRYPT_MODE, spec);
        byte[] after = cipher.doFinal(dec);
        
        return after;
    
    }
    
			*/
    
    ////////////////////////////////////////////
    //
    //		## Action Listeners::
    //
    ////////////////////////////////////////////
    
 
    
    // Action listener for add button (Panel 2)
    private class ADD_AND_ENCRYPT implements ActionListener{
    	
    	public void actionPerformed(ActionEvent e) {
    		
    		String uname = textUser.getText();
    		String pwd = textPass.getText();
    		
    		try {

				crypto crypt = new crypto();

				byte[] encryptedUser = crypt.encrypt(uname, load_key(), "def.txt");
				byte[] encryptedPass = crypt.encrypt(pwd, load_key(), "def.txt");
				
				String getuBytes = Base64.getEncoder().encodeToString(encryptedUser);
				String getpBytes = Base64.getEncoder().encodeToString(encryptedPass);
				 ADV_IO io = new ADV_IO();
				 io.fileOpen("uname_list.txt", uname);
				 io.fileOpen("usernames.txt", getuBytes);
				 io.fileOpen("passwords.txt", getpBytes);
				 usernames.add(uname);
				 passwords.add(pwd);
				 model.addElement(uname);
				
				//KeyMap.put(uname, pwd);
				
				
			}
    		
    		catch (Exception e1) {
				
				e1.printStackTrace();
			
			}
    		
    		textUser.setText(null);
    		textPass.setText(null);
    		
    	}
    	
    }
    
    //Action Listener for the reveal button
    
    private class DECRYPT_USER_PASS implements ActionListener{
    	
    	@Override
    	public void actionPerformed(ActionEvent e) {
    	
			try {
				auth();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				
				e1.printStackTrace();
			}
    		String selectedVauled = (String) list.getSelectedValue();
    		
    		for(int x = 0; x < uname.size(); x++) {
    			
    			
    			if(selectedVauled.equals(uname.get(x))) {
    				
					try {
						
						byte[] xy = Base64.getDecoder().decode(passwords.get(x));
						String dec = new String(xy);
						
						String dPass = new String(new crypto().decrypt(dec, load_key(), "def.txt"));
						reveal_user.setText(selectedVauled);
						reveal_pass.setText(dPass);
					
					} 
					catch (Exception e1) {
						e1.printStackTrace();
					}

				}
    			
    			
    			
    		}
    		
    	}
    	
    }
  

	// Action Listener for the clear button:

	private class CLEAR_BUTTON implements ActionListener{


		public void actionPerformed(ActionEvent e){

			reveal_user.setText(null);
			reveal_pass.setText(null);

		}


	}

  // Action Listener to generate a random password.
    
	
	private class RANDOM_PASS implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			
			String length = JOptionPane.showInputDialog("Please enter the desired length of the password: ");
			
			
			
			int len = Integer.parseInt(length);
			
			crypto pass = new crypto();
			try {
				//byte[] en = Base64.getDecoder().decode(pass.GenPass().getBytes());
				String passwd = pass.GenPass();
				char[] c = passwd.toCharArray();
				char[] trans = new char[len];
				
				for(int x = 0; x < len; x++) {
					
					trans[x] = c[x];
					
				}
				String doFinal = new String(trans);
				
				
				textPass.setText(doFinal);
				
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	
    
    /////////////////////////
	// For the Menu Bars::
	/////////////////////////
    

	private class TASK_EXIT implements ActionListener{

		public void actionPerformed(ActionEvent e){

			System.exit(0);

		}

	}

	private class FILE_ENCRYPT implements ActionListener{

		public void actionPerformed(ActionEvent e){

			try {
			
				new cryptor(new crypto().returner(load_key_string()));
				
				
				 	
			
			
			} 
			
			
			catch (Exception e1) {
				e1.printStackTrace();
			}
			

		}

	}

    
    ////////////////////////////////////////////
    //
    //		## MAIN MENTHOD::
    //
    ////////////////////////////////////////////
    
    
    
    public static void main(String []args) throws Exception{
    	
    	if(flag() == false) {
    		try {
				Setup();
				new PassMan();
			} catch (IOException e) {
			
				JOptionPane.showMessageDialog(null, e.getMessage(), "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
				
			}
    		
    	}
    	
    		
		else if(flag() == true){
				try {
				load_txt_elements();
				load_list();
				auth();
				new PassMan();
				} catch (FileNotFoundException e) {
	
					JOptionPane.showMessageDialog(null, e.getMessage(), "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
			
			}
    	

		}
    }
	
}

