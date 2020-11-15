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

public class helligkeitSensorMqtt {

	
    
        private static IMqttClient publisher;
    
	public static MqttMessage generateData(){
		double helligkeit = ThreadLocalRandom.current().nextDouble(500,999+1);
		String msg = "Helligkeit: " + helligkeit + " Lumen";
		byte[] payload = msg.getBytes();
                
		return new MqttMessage(payload);
	}
	
	
	public static void setupClient() throws MqttException{
            String publisherID = UUID.randomUUID().toString();
            publisher = new MqttClient("tcp://127.0.0.1", publisherID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            publisher.connect();
        }
	
	public static void send(MqttMessage msg) throws IOException,MqttException{
            if(publisher.isConnected()){
               msg.setQos(0);
               msg.setRetained(true);
               publisher.publish("helligkeit", msg);
            }
	}
	
	
	
	
	public static void main(String[] args) throws IOException, InterruptedException, MqttException {
                
                try{
                    setupClient();
                }catch(MqttException e){
                    System.out.println("Fehler beim aufsetzen des Clients: " + e.getMessage());
                }
		while(true){
                        
			// Nachricht generieren und versenden
			send(generateData());

			Thread.sleep(5000);

		}

	}
	
	
	
	
	

}

