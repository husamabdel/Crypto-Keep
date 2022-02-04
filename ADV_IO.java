
import java.util.Scanner;
import java.util.Date; 	//Use date object if you want to fork, for data signature to sign in file!
import java.io.*;
public class ADV_IO {
	
	String path, data;
	
	
	
	//STOP complaining about the lack of documentation use the toString DAMMIT!
	public void fileOpen(String path, String data) throws IOException{
		this.path = path;
		this.data = data;
		
		FileWriter write = new FileWriter(path, true);
		PrintWriter file = new PrintWriter(write);
		file.println(data);
		file.close();
	}
	
	public void fileOpenBin(String path, String data)throws IOException{
		
		this.path = path;
		this.data = data;
		
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(path, true));
		stream.writeUTF(data);
		stream.close();
		
	}

	//Open the file as a byte array!
	public void fileOpenBinByte(String path, byte[] data)throws IOException{
		
		this.path = path;
		//this.data = data;
		
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(path, true));
		stream.write(data);
		stream.close();
		
	}

	public void fileStart(String data, String filename)throws IOException{
		
		this.data = data;
		
		PrintWriter Outputfile = new PrintWriter(filename);
		Outputfile.println(data);
		Outputfile.close();
		
	}
	
	public void filStartBin(String data, String filename) throws IOException {
		
		this.data = data;
		
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(filename));
		stream.writeUTF(data);
		stream.close();
		
	}

	// Start a new file and save data as a Byte array.
	public void filStartBinByte(byte[] data, String filename) throws IOException{

		DataOutputStream stream = new DataOutputStream(new FileOutputStream(filename));
		stream.write(data);

	}
	
	public String IO_stream(String path) throws FileNotFoundException {
		this.path = path;
		String task_data = null;
		File read = new File(path);
		Scanner reader = new Scanner(read);
		while(reader.hasNextLine()) {task_data = reader.nextLine();}
		reader.close();
		return task_data;
	
	}
	
	public String toString() {
		String h = "The class has 3 methods\n 1. fileOpen: will open an existing file and write to the parameter.\n" + 
	"2. fileStart: will create a new file, will print data into the file with the data being in the parameter.\n"
	+ "3. IO_stream: will open a file and return a string of all the data in the file.";
		
		return h;
	}

		

	}
