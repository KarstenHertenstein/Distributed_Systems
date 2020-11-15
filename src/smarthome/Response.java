package smarthome;



public class Response {
	
	private Request request;
	String response;
	
	
	public Response(Request req) {
		this.request = req;
		
		
		String content = generateContent();
		// finale response generieren..
		response+= "HTTP/1.1 200 \n";
		response+= "Content-Type: text/html \n";
		response+= "Connection: close \n";
		response+= "Content-Length: " + content.length() + " \n";
		response+= "\n";
		if(!content.contains("404")){
			response+= content;
		}else if(this.request.getValidity() == false){
                        response = "";
                        response+= "HTTP/1.1 400 \n";
                        response+= "Content-Type: text/html \n";
                        response+= "Connection: close \n";
                        response+= "Content-Length: " + content.length() + " \n";
                        response+= "\n"; 
                        response+= "<html>\n";
                        response+= "<body>\n";
                        response+= "<p>Invalid HTTP package received</p>\n";
                        response+= "</body>\n";
                        response+= "</html>\n";
                }else{
			response = "";
                        response+= "HTTP/1.1 404 \n";
                        response+= "Content-Type: text/html \n";
                        response+= "Connection: close \n";
                        response+= "Content-Length: " + content.length() + " \n";
                        response+= "\n"; 
                        response+= "<html>\n";
                        response+= "<body>\n";
                        response+= "<p>Requested resource not found! Sorry!</p>\n";
                        response+= "</body>\n";
                        response+= "</html>\n";
		}
		
	}
	
	
	public String generateContent(){
		
		dataManager data = new dataManager();
		
		String content = "";
		content+= "<html>\n";
		content+= "<head>\n";
                content+= "<meta charset=\"utf-8\"/>";
		content+= "<h1><ul>Daten:</ul></h1>\n";
		content+= "</head>\n";
		content+= "<body>\n";
		String resource = this.request.getRequestedResource();
		// REST-Definitionen - einzelne Sensordaten / alle Daten
		if(resource.equals("/Helligkeit")){
			for(int i = 0; i < dataManager.helligkeit.size(); i++){
				content+= "<p>";
				String cut = dataManager.helligkeit.get(i).replaceAll("Helligkeit: ", "");
				content+= cut;
				content+= "</p>\n";
			}
			
		}else if(resource.equals("/Temperatur")){
			for(int i = 0; i < dataManager.temperatur.size(); i++){
				content+= "<p>";
				String cut = dataManager.temperatur.get(i).replaceAll("Temperatur: ", "");
				content+= cut;
				content+= "</p>\n";
			}
		}else if(resource.equals("/Luftfeuchtigkeit")){
			for(int i = 0; i < dataManager.luftfeuchtigkeit.size(); i++){
				content+= "<p>";
				String cut = dataManager.luftfeuchtigkeit.get(i).replaceAll("Luftfeuchtigkeit: ", "");
				content+= cut;
				content+= "</p>\n";
			}
		}else if(resource.equals("/Daten")){
			for(int i = 0; i < dataManager.helligkeit.size(); i++){
				content+= "<p>";
				String cut = dataManager.helligkeit.get(i).replaceAll("Helligkeit: ", "");
				content+= cut;
				content+= "<p>\n";
			}
			for(int i = 0; i < dataManager.temperatur.size(); i++){
				content+= "<p>";
				String cut = dataManager.temperatur.get(i).replaceAll("Temperatur: ", "");
				content+= cut;
				content+= "</p>\n";
			}
			for(int i = 0; i < dataManager.luftfeuchtigkeit.size(); i++){
				content+= "<p>";
				String cut = dataManager.luftfeuchtigkeit.get(i).replaceAll("Luftfeuchtigkeit: ", "");
				content+= cut;
				content+= "</p>\n";
			}
					
		}else{
			// File not found
			content+= "404";
		}
		
		content+= "</body>\n";
		content+= "</html>\n";
		
		return content;
	}
}
