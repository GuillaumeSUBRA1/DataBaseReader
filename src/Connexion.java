
public class Connexion {

	String database;
	String login;
	String password;
	
	public Connexion(String database, String login, String password) {
		super();
		this.database = database;
		this.login = login;
		this.password = password;
	}
	
	public String getDatabase() {return database;}
	public void setDatabase(String database) {this.database = database;}
	public String getLogin() {return login;}
	public void setLogin(String login) {this.login = login;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	

}