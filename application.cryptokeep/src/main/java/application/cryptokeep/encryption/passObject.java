package application.cryptokeep.encryption;

import java.io.Serializable;
import java.util.ArrayList;

public class passObject implements Serializable{
	
	private String username;
	private long pin;
	private ArrayList <String> usernames = new ArrayList<String>();
	private ArrayList <String> passwords = new ArrayList<String>();
	private ArrayList <String> uname = new ArrayList<String>();
	private ArrayList<String> favorites = new ArrayList<String>();
	private ArrayList<String> group11 = new ArrayList<String>();
	private ArrayList<String> group22 = new ArrayList<String>();
	private ArrayList<String> linkS = new ArrayList<String>();
	
	public passObject(String username, long pin, ArrayList <String> usernames, ArrayList <String> passwords, ArrayList <String> uname, ArrayList <String> favorites, ArrayList <String> group11, ArrayList <String> group22, ArrayList <String> linkS ) {
		
		this.username = username;
		this.pin = pin;
		this.usernames = usernames;
		this.passwords = passwords;
		this.uname = uname;
		this.favorites = favorites;
		this.group11 = group11;
		this.group22 = group22;
		this.linkS = linkS;
		
	}
	
	public passObject() {
		
		
		
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public ArrayList<String> getUsernames() {
		return usernames;
	}
	public void setUsernames(ArrayList<String> usernames) {
		this.usernames = usernames;
	}
	public ArrayList<String> getPasswords() {
		return passwords;
	}
	public void setPasswords(ArrayList<String> passwords) {
		this.passwords = passwords;
	}
	public ArrayList<String> getUname() {
		return uname;
	}
	public void setUname(ArrayList<String> uname) {
		this.uname = uname;
	}
	public ArrayList<String> getFavorites() {
		return favorites;
	}
	public void setFavorites(ArrayList<String> favorites) {
		this.favorites = favorites;
	}
	public ArrayList<String> getGroup11() {
		return group11;
	}
	public void setGroup11(ArrayList<String> group11) {
		this.group11 = group11;
	}
	public ArrayList<String> getGroup22() {
		return group22;
	}
	public void setGroup22(ArrayList<String> group22) {
		this.group22 = group22;
	}
	public ArrayList<String> getLinkS() {
		return linkS;
	}
	public void setLinkS(ArrayList<String> linkS) {
		this.linkS = linkS;
	}

	
	
}
