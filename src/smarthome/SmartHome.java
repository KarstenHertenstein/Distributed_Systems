/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthome;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttException;


/**
 *
 * @author fs
 */
public class SmartHome {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, MqttException{
        
                
                
                ServerSocket server = new ServerSocket(8080);
		DatagramSocket dsocket = new DatagramSocket(8080);

		System.out.println("Server running....");
		long start = System.currentTimeMillis();
		
                MqttSubscriberClient client = new MqttSubscriberClient();
                // Thread für Thriftserver starten
                ThriftServer thrift = new ThriftServer();
                thrift.start();
                
                
		while(true){

			// tcp verbindung aktzeptieren und conn instanz erzeugen, welche neuen thread öffnet
			Socket socket = server.accept();
			Connection tcpConnection = new Connection(socket);
			tcpConnection.start();
                        
		}
                
                
    }
    
}
