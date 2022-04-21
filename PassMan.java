import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.ReverbType;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Desktop.*;
import java.awt.event.*;
import java.util.*;
import java.security.*;
import java.util.Base64;

public class PassMan extends JFrame{

	//Menu.
	private JMenuBar bar = new JMenuBar();
	
	private JList list;
	// Weird.. dont know what Idea i had for this....
	private JTextArea area;
	
	// All the panels.
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	
	// All the text fields
	private JTextField textPass;
	private JTextField textUser;
	private JTextField reveal_pass;
	private JTextField reveal_user;
	
	// All the scrollpanes.
	private JScrollPane pane;
	private JScrollPane scroll;
	
	// All the buttons.
	private JButton button;
	private JButton add;
	private JButton link;
	private JButton group;
	private JButton group2;
	private JButton reveal;
	private JButton clear;
	private JButton random;
	private JButton AllUsers;
	private JButton removeSelected;
	
	private JFileChooser filer;
	private static String filename = "default.dat";
	
	//private File mainFile;
	
	private static HashMap<String, String> KeyMap;
	private static ArrayList <String> usernames = new ArrayList<>();
	private static ArrayList <String> passwords = new ArrayList<>();
	private static ArrayList <String> uname = new ArrayList<>();

	// List for the JList
	private static DefaultListModel<String> model = new DefaultListModel<>();
	
	// Lists for the buttons panel.
	private static ArrayList<String> favorites = new ArrayList<>();
	private static ArrayList<String> group11 = new ArrayList<>();
	private static ArrayList<String> group22 = new ArrayList<>();
	private static ArrayList<String> linkS = new ArrayList<>();

	// Not in current use.
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
		//this.setResizable(false);
		this.setVisible(true);
		
	}
	
    ///////////////////////////////////////////////////////////////////////
    //
    //				GUI INITIALIZATION FUNCTIONS FUNCTIONS::
    //			
    ///////////////////////////////////////////////////////////////////////
	
	public void setMenuBar1() {
		
		
		JMenu FileM = new JMenu("File");
		JMenu filecontrol = new JMenu("File controls");

		//JMenuItem fileE = new JMenuItem("File controls");
		JMenuItem export = new JMenuItem("Export Data");
		JMenuItem import1 = new JMenuItem("Import Data");
		

		JMenuItem item = new JMenuItem("Encrypt a file");
		item.addActionListener(new FILE_ENCRYPT());
		
		JMenuItem advanced = new JMenuItem("Advanced Add");
		advanced.addActionListener(new SUBMIT());


		JMenuItem settings = new JMenuItem("Settings");
		JMenuItem exit = new JMenuItem("exit");
		exit.addActionListener(new TASK_EXIT());

		JMenu help = new JMenu("Help");
		JMenuItem readme = new JMenuItem("Readme");
		readme.addActionListener(new READMEE());

		export.addActionListener(new exportData());

		filecontrol.add(export);
		filecontrol.add(import1);

		
		FileM.add(filecontrol);
		FileM.addSeparator();
		FileM.add(item);
		FileM.add(advanced);
		FileM.add(exit);
		
		help.add(readme);

		bar.add(FileM);
		bar.add(help);
		
	}

	//Groups panel.

	public void panel1Set() {
		


		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0,5));
		button = new JButton("favorites");
		button.addActionListener(new showFavorites());
		
		link = new JButton("Link");
		link.addActionListener(new showLinks());
		
		group = new JButton("Group 1");
		group.addActionListener(new showGrp1());
		
		group2 = new JButton("Group 2");
		group2.addActionListener(new showGrp2());
		
		AllUsers = new JButton("All");
		AllUsers.addActionListener(new showAll());
		
		panel1.add(AllUsers);
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
		
		removeSelected = new JButton("Delete");
		removeSelected.addActionListener(new REMOVE());
		
		panel2.add(clear);
		panel2.add(reveal);
		panel2.add(random);
		panel2.add(removeSelected);

	}
	
	//View and decrypt panel
	public void panel3Set(){
		
	

		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		panel3.setBackground(Color.WHITE);
		
		JLabel label = new JLabel("Crypto-Keep Password Manager!");
		label.setFont(new Font("MV Boli", Font.ROMAN_BASELINE, 25));
		ImageIcon icon = new ImageIcon("passman.png");
		label.setIcon(icon);
		label.setBorder(null);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.TOP);

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

	//////////////////////////////
	//
	//		Getters and Setters
	//
	//////////////////////////////


	public String getUsername(){

		return textUser.getText();

	}

	public void setUserList(String username){

		uname.add(username);

	}

	public void setModelList(String username){

		model.addElement(username);

	}

	public void setStucts(String username, String password, String alias){

		usernames.add(username);
		passwords.add(password);
		model.addElement(alias);

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
		
		//For the Buttons
		PrintWriter LinksList = new PrintWriter("links.txt");
		PrintWriter FavoritesList= new PrintWriter("favorites.txt");
		PrintWriter group1List = new PrintWriter("g1.txt");
		PrintWriter group2List = new PrintWriter("g2.txt");
		//END BUTTONS

		String pass = JOptionPane.showInputDialog("Please create a  Master key to proceed");

			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] passHash = md.digest(pass.getBytes());
			String finalPass = new String(passHash);

		
		//Key and Password storing
		ADV_IO keyStore = new ADV_IO();
	
			keyStore.filStartBinByte(passHash, "default.dat");
			keyStore.fileStart(finalPass, "default.txt");
			crypto cKey = new crypto();
			byte[] bytekey = cKey.keyGen().getEncoded();
			String baseKey = Base64.getEncoder().encodeToString(bytekey);
			
			//This is for the key to be stored as a String!
			keyStore.fileStart(baseKey,"def.txt");
			
			//This is for the key to be stored as a string data encoded!!
			keyStore.filStartBin(baseKey, "def.dat");

			//This if for the key to be stored as RAW byte array!! No conversion!!
			keyStore.filStartBinByte(bytekey, "bytekey.dat");
			
		
		UsernameFile.close();
		PasswordFile.close();
		decrypted_unames.close();
		LinksList.close();
		FavoritesList.close();
		group1List.close();
		group2List.close();
		
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

		//
		//
		//
		//
		//		LOADING THE BUTTONS GOES HERE!!!!
		//
		//
		//
		//
		//
    	
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
    

	//////////////////////////////////////
	//
	//		FOR THE BUTTONS
	//
	//////////////////////////////////////




    
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

	public static byte[] load_key_byte() throws IOException{

		//File file = new File("bytekey.dat");
		//DataInputStream stream = new DataInputStream(new FileInputStream(file));
		byte[] key = Files.readAllBytes(Paths.get("bytekey.dat"));
		
		return key;

	}

	public static byte [] GetProperKey() throws IOException, NoSuchAlgorithmException{

		byte[] HashToGet = Files.readAllBytes(Paths.get("default.dat"));
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(HashToGet);

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


	/////////////////////////
	//
	//	Delete a selected entry:
	//
	/////////////////////////


	public static void deleteEntry(String entry) throws FileNotFoundException {
		
		int index;
		LinkedList <String> lyst = new LinkedList<>();
		
		for(int i = 0; i < uname.size(); i++) {
			lyst.add(uname.get(i));
		}

		System.out.println(lyst.toString());
		
		for(int i = 0; i < lyst.size(); i++) {
			
			if(lyst.get(i).equals(entry)) {
				
				index = i;
				
				uname.remove(index);
				usernames.remove(index);
				passwords.remove(index);
				break;
				
			}
			
		}
		

		
		PrintWriter out = new PrintWriter("uname_list.txt");
		PrintWriter out2 = new PrintWriter("usernames.txt");
		PrintWriter out3 = new PrintWriter("passwords.txt");
		
		for(int i = 0; i < lyst.size()-1; i++) {
			
			out.println(uname.get(i));
			out2.println(usernames.get(i));
			out3.println(passwords.get(i));
			
		}
		out.close();
		out2.close();
		out3.close();
		
		

		
	}

	/////////////////////////
	//
	//
	//	When the button is clicked, call the function above to delete an enrty.
	//
	//
	////////////////////////
	

    
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
    protected class ADD_AND_ENCRYPT implements ActionListener{
    	
    	public void actionPerformed(ActionEvent e) {
    		
    		String uname = textUser.getText();
    		String pwd = textPass.getText();
    		
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
				 io.fileOpen("uname_list.txt", uname);
				 io.fileOpen("usernames.txt", getuBytes);
				 io.fileOpen("passwords.txt", getpBytes);
				 usernames.add(getpBytes);
				 passwords.add(getpBytes);
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
						byte[] yx = Base64.getDecoder().decode(usernames.get(x));
						//String dec = new String(xy);
						
						new crypto();
						String dPass = new String(crypto.decrypt(xy, GetProperKey(), "def.txt"));
						String dUser = new String(crypto.decrypt(yx, GetProperKey(), "def.txt"));
						reveal_user.setText(dUser);
						reveal_pass.setText(dPass);
					
					} 
					catch (Exception e1) {
						e1.printStackTrace();
					}

				}
    			
    			
    			
    		}
    		
    	}
    	
    }

	// ActionListener for the menu to add with alias


	//
	//
	//
	//
	//




	protected class SUBMIT implements ActionListener{
	
		public void actionPerformed(ActionEvent e){
	
			advancedAdd adder = new advancedAdd();
			setStucts(adder.getUsername(), adder.getPassword(), adder.getAlias());
			
		}
	
	}
	





  //
  //
  //
  //
  //
  //


	// Action Listener for the clear button:

	private class CLEAR_BUTTON implements ActionListener{


		public void actionPerformed(ActionEvent e){

			reveal_user.setText(null);
			reveal_pass.setText(null);

		}


	}
	
	
	
	// Action listener for the delete button.
	
	private class REMOVE implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			

			String element = (String) list.getSelectedValue();
			int ans = JOptionPane.showConfirmDialog(null, "WARNING!! You are about to delete the saved data for this selection! are you sure?", "Alert!", JOptionPane.YES_NO_OPTION);
			
			
			if(ans == JOptionPane.YES_OPTION){

				
				try {
					deleteEntry(element);
				} 
				
				catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				list.remove(list.getSelectedIndex());

			}

			else{
				JOptionPane.showMessageDialog(null, "Operation Cancelled!");
			}
			
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

	private class thread2 extends Thread{

		@Override
		public void run(){

			new cryptoSettings(textUser.getText());
			

		}

	}
	
	
	
	////////////////////////
	//	For the buttons.
	////////////////////////

	private class showFavorites implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			model.clear();
			favorites.clear();
			File file = new File("favorites.txt");
			try {
				//model.clear();
				Scanner scan = new Scanner(file);
				while(scan.hasNextLine()) {
					favorites.add(scan.nextLine());
				}
				
				model.clear();
				for(String s: favorites) {
					model.addElement(s);
				
				}
					scan.close();
					
			}
			
			
			catch (FileNotFoundException e1) {
			
				e1.printStackTrace();
			
			}
			
		}
		
	}
	
	private class showLinks implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			try {
				new links();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	
	private class showGrp1 implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			model.clear();
			group11.clear();
			File file = new File("g1.txt");
			try {
				
				Scanner scan = new Scanner(file);
				while(scan.hasNextLine()) {
					group11.add(scan.nextLine());
				}
				
			
				for(String s: group11) {
					model.addElement(s);
				
				}
					scan.close();
				
			}
			
			
			catch (FileNotFoundException e1) {
			
				e1.printStackTrace();
			
			}
			
		}
		
	}
	
	private class showGrp2 implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			model.clear();
			group22.clear();
			File file = new File("g2.txt");
			try {
				
				Scanner scan = new Scanner(file);
				while(scan.hasNextLine()) {
					group22.add(scan.nextLine());
				}
				
				model.clear();
				for(String s: group22) {
					model.addElement(s);
				
				}
					scan.close();
				
			}
			
			
			catch (FileNotFoundException e1) {
			
				e1.printStackTrace();
			
			}
			
		}
		
	}
	
	private class showAll implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			model.clear();
			for(String s : uname) {
				
				model.addElement(s);
				
			}
			
		}
		
	}

    /////////////////////////
	// For the Menu Bars::
	/////////////////////////

	private class READMEE implements ActionListener{

		public void actionPerformed(ActionEvent e){

			File file = new File("README.md");

			Desktop top = java.awt.Desktop.getDesktop();

			try {
				top.open(file);
			} catch (IOException e1) {
	
				e1.printStackTrace();

			}

		}

	}
    

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


	private class exportData implements ActionListener{

		public void actionPerformed(ActionEvent e){

			ADV_IO exporter = new ADV_IO();

			try {

				int ans = JOptionPane.showConfirmDialog(null, "WARNING!! You are about to export all passwords into an UNENCRYPTED!!! Data file, are you sure you want to proceed?", "WARNING!", JOptionPane.YES_NO_CANCEL_OPTION);

				if(ans == 0){

					exporter.fileStart("========================Crypto Keep Data for user======================== \n" + System.getProperty("user.name"), System.getProperty("user.name")+"CRYPTOKEEP");
					exporter.fileOpen(System.getProperty("user.name")+"CRYPTOKEEP", "Usernames\tPasswords\n=========================================================================\n\n");
					for(int x = 0; x < usernames.size(); x++){

						byte[] xy = Base64.getDecoder().decode(passwords.get(x));
						//String dec = new String(xy);
						
						String dPass = new String(new crypto().decrypt(xy, GetProperKey(), "def.txt"));
	
						exporter.fileOpen(System.getProperty("user.name")+"CRYPTOKEEP", (x+1)+". "+ uname.get(x) + "\t" + dPass);
	
					}
					exporter.fileOpen(System.getProperty("user.name")+"CRYPTOKEEP", "\n COMPLETED!");

					JOptionPane.showMessageDialog(null, "Operation Completed! The file was saved in the program Data!", "Success!", JOptionPane.OK_OPTION);

				}
				else if (ans == 1 || ans ==2){

					JOptionPane.showMessageDialog(null, "Operation Cancelled", "ALERT", JOptionPane.OK_OPTION);

				}

			
			}
			catch (IOException e1) {
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				
				e1.printStackTrace();
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}

		}

	}


	////////////////////////////////////////////
	//
	//		## Terminal mode
	//
	////////////////////////////////////////////

	public static void T_auth() throws FileNotFoundException, NoSuchAlgorithmException{

		File file = new File("default.txt");
		Scanner scan = new Scanner(file);
		String pwd = scan.nextLine();

		Scanner keyboard = new Scanner(System.in);

		while(true){
			System.out.print("Please enter masterkey to continue: \n\n\n");
			String pass = keyboard.nextLine();
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] passHash = md.digest(pass.getBytes());
			String finalPass = new String(passHash);


			if(finalPass.equals(pwd)){
				break;
			}
			else{
					System.out.println(
						"Error! Bad key was used during authentication! Please try again! \n\n\n"
					);
			}
		}
		scan.close();


	}

	public static void show_user_info(String val) throws FileNotFoundException{

		File file = new File("usernames.txt");
		Scanner scan = new Scanner(file);
		
		for(int x = 0; x < usernames.size(); x++){
			if(val.equals(usernames.get(x))){
				String passwd = passwords.get(x);
				System.out.println("\n\n");
				System.out.println("The user name is: \n" + usernames.get(x)+ "\n" + "The encrypted password is: \n" + passwords.get(x));
				System.out.println("\n Please try again with \"Decrypt\" [USERNAME] to try and decrypt the password");
			}
		}

	}

	public static void show_user_password(String val) throws IOException, Exception{

		File file = new File("usernames.txt");
		Scanner scan = new Scanner(file);
		
		for(int x = 0; x < usernames.size(); x++){
			if(val.equals(usernames.get(x))){
				String passwd = passwords.get(x);

				byte[] decryted = crypto.decrypt(passwd.getBytes(), GetProperKey(), "fake");
				String decoded = new String(Base64.getDecoder().decode(decryted));

				System.out.println("\n\n");
				System.out.println("The user name is: \n" + usernames.get(x)+ "\n" + "The decrypted password is: \n" + decoded);
				
			}
		}

	}

    ////////////////////////////////////////////
    //
    //		## MAIN MENTHOD::
    //
    ////////////////////////////////////////////
    
    
    
    public static void main(String []args) throws Exception{





    ////////////////////////////////////////////
	//
	//		## Terminal mode
	//
	////////////////////////////////////////////


	    	
	String[] Operators={

		"show",			// Showcase all the list of password and usernames.
		"get",			// Returns comments and usernames of certain elements.
		"encrypt",		// Adds new password.
		"decrypt",		// Decrypts and lists full values.
		"add",			// Adds to certain things.
		"remove" 		// Removes a password from the field.

	};
/*
	if(flag() == true || args[1].isEmpty() == false){
		try {
		load_txt_elements();
		load_list();
		
		if(args[0].equals(Operators[0])){
			T_auth();

			if(args[1] == null){

				System.out.println("ERROR, no username provided\n" +
				
				"USAGE:    PassMan [option] [USERNAME]\n" + 
				"OPTIONS: \n" +Operators.toString());

				System.exit(0);

			}

			String Username = args[1];
			show_user_info(Username);

			System.exit(0);

		}
		else if(args[0].equals(Operators[3])){

			T_auth();

			if(args[1] == null){

				System.out.println("ERROR, no username provided\n" +
				
				"USAGE:    PassMan [option] [USERNAME]\n" + 
				"OPTIONS: \n" +Operators.toString());

				System.exit(0);

			}

			String user = args[1];
			show_user_info(user);

			System.exit(0);

		}



		} catch (FileNotFoundException e) {

			JOptionPane.showMessageDialog(null, e.getMessage(), "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
	
	}


}
    	

	*/


    ////////////////////////////////////////////
	//
	//		## Main GUI
	//
	////////////////////////////////////////////
	
	
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
