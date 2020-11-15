package sensoren;
import com.sun.xml.internal.ws.resources.WsservletMessages;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;
import java.math.*;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.*;


public class tempSensorMqtt{
        
        
        private static IMqttClient publisher;
    
        public static void setupClient() throws MqttException{
            String publisherID = UUID.randomUUID().toString();
            publisher = new MqttClient("tcp://127.0.0.1", publisherID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            publisher.connect();
        }
    
        //private static int packetNum = 0;
	public static MqttMessage generateData(int daytime) throws MqttException{
		
		double randomNum;
		if(daytime < 6){
			randomNum = ThreadLocalRandom.current().nextDouble(12, 16+1);
		}else if(daytime < 12 && daytime >= 6){
			randomNum = ThreadLocalRandom.current().nextDouble(15, 19+1);
		}else if(daytime < 16 && daytime >= 12){
			randomNum = ThreadLocalRandom.current().nextDouble(23, 25+1);
		}else if(daytime < 20 && daytime >= 16){
			randomNum = ThreadLocalRandom.current().nextDouble(20, 26+1);
		}else{
			randomNum = ThreadLocalRandom.current().nextDouble(16, 22+1);
		}
		randomNum = Math.round(randomNum * 100.0) / 100.0;
		String msg = "Temperatur: " + randomNum + "°C";
                byte[] payload = msg.getBytes();
                
		return new MqttMessage(payload);
	}
        
	
	
	
	public static void send(MqttMessage msg) throws IOException,MqttException{
            if(publisher.isConnected()){
               msg.setQos(0);
               msg.setRetained(true);
               publisher.publish("temperatur", msg);
            }
	}
	
        
	
	
	
	
	public static void main(String[] args) throws IOException, InterruptedException, MqttException {
		
		int daytime = 0;

                try{
                    setupClient(); 
                }catch(MqttException e){
                    System.out.println(e.getMessage());
                }
      
                while(true){     
                    send(generateData(daytime));
                    daytime++;
                    if(daytime > 24){
			daytime = 0;
                    }
                    Thread.sleep(5000);
                }
			
			
		
			
		
		
		
		
		

	}
	
	
	
	
	

}
