package smarthome;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;




public class Connection extends Thread{
	
	private Socket socket;
	// Output an Client senden
	private PrintWriter writer;
	// Input von Client bekommen
	private BufferedReader reader;
	
	// Konstruktor erwartet TCP-Socket
	public Connection(Socket s) throws IOException{
		this.socket = s;
		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		writer = new PrintWriter(s.getOutputStream());
	}
	
	@Override
	public void run(){
		String req = "";
		
		try {
			// Wenn Request VOLLSTÄNDIG gelesen ODER nichts gelesen wurde
			while(reader.ready() || req.length() == 0){
				req = req + (char) reader.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(req);
		
		// Request wird verarbeitet (angeforderte ressource ermittelt)
		Request r = new Request(req);
		// Response wird entsprechend generiert
                
                Response resp = new Response(r); 
                
		
		
		// Response an Client (den Browser in unserem Fall)
		writer.write(resp.response.toCharArray());
		writer.flush();
		
		// Reihenfolge beim schließen beachten
		writer.close();
		try {
			reader.close();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
	}
	
}
