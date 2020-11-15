package sensoren;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;
import java.math.*;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import sensoren.tempSensorMqtt;

public class luftfeuchtigkeitSensorMqtt {

	
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
    
    
	public static MqttMessage generateData(){
		
		int randomNum = ThreadLocalRandom.current().nextInt(40,82+1);
		String msg = "Luftfeuchtigkeit: " + randomNum + "%";
		byte[] payload = msg.getBytes();
                
		return new MqttMessage(payload);
	}
	
	
	
	public static void send(MqttMessage msg) throws IOException,MqttException{
            if(publisher.isConnected()){
               msg.setQos(0);
               msg.setRetained(true);
               publisher.publish("luftfeuchtigkeit", msg);
            }
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException, InterruptedException, MqttException {
		
		MqttMessage msg = null;
		int daytime = 0;
		
		
		while(true){
			
                    
                    try{
                        setupClient(); 
                    }catch(MqttException e){
                        System.out.println(e.getMessage());
                    }
		    // Nachricht generieren
                    if(daytime % 8 == 0 && daytime != 24){
			msg = generateData();
                    }
                    send(msg);
                            
                    daytime++;
                    if(daytime > 24){
                       daytime = 0;
                    }
			
                    Thread.sleep(5000);

		}
		
		
		
		

	}
	
	
	
	
	

}
