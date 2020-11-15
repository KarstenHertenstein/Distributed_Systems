package smarthome;

public class Request {
	
	private String requestedResource;
        private boolean isValid;
	
	public Request(String req){
		String linesOfHeader[] = req.split("\n");
		// zweites "wort" in erster zeile des header ist unsere angeforderte ressource
		this.requestedResource = linesOfHeader[0].split(" ")[1];
                
                if(linesOfHeader[linesOfHeader.length-1].contains("\r\n\r\n")){
                    isValid = true;
                }else{
                    isValid = false;
                }
	}
	
	public String getRequestedResource(){
		return this.requestedResource;
	}
        public boolean getValidity(){
            return this.isValid;
        }

}
