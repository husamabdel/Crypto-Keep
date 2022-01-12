
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

private JTextField reveal_pass;
private JTextField reveal_user;
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
private static String filename = "default.dat";
//private File mainFile;
private static HashMap<String, String> KeyMap;
private static ArrayList <String> usernames = new ArrayList<>();
private static ArrayList <String> passwords = new ArrayList<>();
private static ArrayList <String> uname = new ArrayList<>();



public PassMan() {
	
	this.setTitle("Pass Man");
	this.setSize(780,400);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLayout(new BorderLayout());
	panel1Set();
	panel2Set();
	panel3Set();
	this.add(panel1, BorderLayout.WEST);
	this.add(panel2, BorderLayout.CENTER);
	this.add(panel3, BorderLayout.EAST);
	//this.pack();
	this.setResizable(true);
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
	panel1.setLayout(new GridLayout(4,0));
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
	
	textUser = new JTextField(12);
	
	textPass = new JTextField(12);
	
	add = new JButton("Add");
	add.addActionListener(new ADD_AND_ENCRYPT());
	
	panel2.add(add);
	panel2.add(textUser);
	panel2.add(textPass);
	
}

//View and decrypt panel
public void panel3Set(){
	

	panel3 = new JPanel();
	panel3.setLayout(new FlowLayout());
	
	//JLabel label = new JLabel();
	//label.setText("Username and Password");
	
	reveal = new JButton("Reveal");
	reveal.addActionListener(new DECRYPT_USER_PASS());
	
	list = new JList(load_list());
	pane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	list.setVisibleRowCount(6);
	reveal_user = new JTextField(12);
	//reveal_user.setEditable(false);
	reveal_pass = new JTextField(12);
	//reveal_pass.setEditable(false);
	panel3.add(reveal);
	//panel3.add(list);
	panel3.add(pane);
	panel3.add(reveal_user);
	panel3.add(reveal_pass);
	//panel3.add(label, BorderLayout.CENTER);
	

}


///////////////////////////////////////////////////////////////////////
//
//				##STARTUP FUNCTIONS::
//
///////////////////////////////////////////////////////////////////////


public static void bypass()throws FileNotFoundException, IOException{

	while(true){

		File file = new File("default.txt");
		Scanner scan = new Scanner(file);
		String primaryKey = JOptionPane.showInputDialog("Master Key", "Please enter your master key to continue: ");
		String pass = null;
		while(scan.hasNextLine()){
			pass = scan.nextLine();
		}
		if(pass.equals(primaryKey)){
			scan.close();

			break;
		}
			else{
				JOptionPane.showMessageDialog(null,"Incorrect credentials were supplied! PLease try again!","ERROR",JOptionPane.OK_OPTION);
				continue;
			}

	}

}



//Determine if the data file exists or not This i called first in main.
public static boolean flag(){
	
	File file = new File(filename);
	
	if(file.exists()) {
		return true;
	}
	return false;
}
//start a new file if data is not set , this is called IF first function is false

public static void Setup() throws IOException {
	
	JOptionPane.showMessageDialog(null,"Welcome to Pass Man Pro, the setup process will now begin","Setup.exe",JOptionPane.OK_OPTION);
	JOptionPane.showMessageDialog(null,"A new file has been created!","FileNotFound",JOptionPane.OK_OPTION);

	PrintWriter UsernameFile = new PrintWriter("usernames.txt");
	PrintWriter decrypted_unames = new PrintWriter("uname_list.txt");
	PrintWriter PasswordFile = new PrintWriter("passwords.txt");
	String key = JOptionPane.showInputDialog("Please create an encryption key (256 AES) to proceed");
	ADV_IO keyStore = new ADV_IO();
	try {
		keyStore.filStartBin(key);
		keyStore.fileStart(key);
	} catch (IOException e) {
		JOptionPane.showMessageDialog(null, e.getMessage(),"No Such Algorithm",JOptionPane.OK_OPTION);
	}
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

public static String[] load_list(){
	
	String[] unames = new String[uname.size()];
	for(int x = 0; x < uname.size(); x++) {
		
		unames[x] = uname.get(x);
		
	}
		return unames;
}


///////////////////////////////////////////////////////////////////////
//
//				##ENCRYPTION AND DECRYPTION FUNCTIONS::
//				1. Generate secret key from text.
//				2. Encrypt with AES.
//				3. return byte[] of encrypted elements.
//
///////////////////////////////////////////////////////////////////////
	


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
			byte[] encryptedUser = encrypt(uname, getKey());
			byte[] encryptedPass = encrypt(pwd, getKey());
			
			String getuBytes = new String(encryptedUser);
			String getpBytes = new String(encryptedPass);
			 ADV_IO io = new ADV_IO();
			 io.fileOpen("uname_list.txt", uname);
			 io.fileOpen("usernames.txt", getuBytes);
			 io.fileOpen("passwords.txt", getpBytes);
			 usernames.add(uname);
			 passwords.add(pwd);
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
	public void actionPerformed(ActionEvent e){
	
		try{
			bypass();
		}
		catch(IOException e1){

			JOptionPane.showMessageDialog(null,e1.getMessage(),"FileNotFound",JOptionPane.ERROR_MESSAGE);

		}
		int y = 0;
		String selectedVaule = (String) list.getSelectedValue();
		
		for(int x = 0; x < usernames.size(); x++) {
			if(y < usernames.size()){
				String temp = uname.get(x);
				
					if(temp.equals(selectedVaule)) {
						reveal_user.setText(selectedVaule);
						reveal_pass.setText(passwords.get(x));
					}
			}
		}
	}
}





////////////////////////////////////////////
//
//		## MAIN MENTHOD::
//
////////////////////////////////////////////



public static void main(String []args)throws FileNotFoundException, IOException{
	
	if(!flag()) {
		
		try {
			Setup();
		} catch (IOException e) {
		
			JOptionPane.showMessageDialog(null, e.getMessage(), "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	try {
		load_txt_elements();
	} catch (FileNotFoundException e) {

		JOptionPane.showMessageDialog(null, e.getMessage(), "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
		
	}
	
	
		load_list();
		bypass();
		new PassMan();
	
}

}

