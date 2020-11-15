/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthome;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


/**
 *
 * @author fs
 */
public class MqttSubscriberClient {
    
    
    private IMqttClient client;
    
    
    public MqttSubscriberClient() throws MqttException{ 
        this.client = new MqttClient("tcp://127.0.0.1", MqttClient.generateClientId());
        client.setCallback(new MqttCallback(){
            @Override
            public void connectionLost(Throwable throwable){}
            
            @Override
            public void messageArrived(String t, MqttMessage m){
                System.out.println("Message: " + m.toString());
                if(m.toString().contains("Temperatur")){
                    dataManager.temperatur.add(m.toString());
                }else if(m.toString().contains("Luftfeuchtigkeit")){
                    dataManager.luftfeuchtigkeit.add(m.toString());
                }else if(m.toString().contains("Helligkeit")){
                    dataManager.helligkeit.add(m.toString());
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken t){}
        });
        
        client.connect();
        client.subscribe("temperatur");
        client.subscribe("luftfeuchtigkeit");
        client.subscribe("helligkeit");
    }
    
    
    
  
    
    
    
    	
}
