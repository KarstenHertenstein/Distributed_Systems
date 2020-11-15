/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthome;

/**
 *
 * @author fs
 */

import java.io.IOException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import smarthome.ReceiveStatusService;


public class AnbieterClient {
    
    static int hellIndex = 0;
    static int tempIndex = 0;
    static int luftIndex = 0;
    
    public static void main(String [] args) throws IOException, InterruptedException {
        
        AnbieterClientDataManager data = new AnbieterClientDataManager();
        
        int i = 0;
        try {
            TTransport transport;
     
            transport = new TSocket("localhost", 9090);
            transport.open();
                
            TProtocol protocol = new  TBinaryProtocol(transport);
            ReceiveStatusService.Client client = new ReceiveStatusService.Client(protocol);
            while(i < 5000){
              perform(client, hellIndex, tempIndex, luftIndex, data);
              i++;
              Thread.sleep(2000);
            }
            

            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        } 
  }

  private static void perform(ReceiveStatusService.Client client, int hell, int temp, int luft, AnbieterClientDataManager data) throws TException
  {
    String temperatur = client.receiveTemperatur(temp);
    String luftfeuchtigkeit = client.receiveLuftfeuchtigkeit(luft);
    String helligkeit = client.receiveHelligkeit(hell);
    
    
    System.out.println(temperatur);
    System.out.println(luftfeuchtigkeit);
    System.out.println(helligkeit);
    System.out.println("------------------------------");
    
    
    // Prüfung, da evtl. keine neuen Daten vorliegen
    if(helligkeit.contains("$")){
       hellIndex = getIndex(helligkeit); 
       data.saveData(helligkeit);
    }
    if(temperatur.contains("$")){
       tempIndex = getIndex(temperatur);
       data.saveData(temperatur);
    } 
    if(luftfeuchtigkeit.contains("$")){
       luftIndex = getIndex(luftfeuchtigkeit);
       data.saveData(luftfeuchtigkeit);
    }
    

  }
  
  // Schneidet Index ab von Nachricht und gibt diesen zurück
  public static int getIndex(String str){
      String buf = "";
      int i = 0;
      while(str.charAt(i) != '$'){
          buf+=str.charAt(i);
          i++;
      }
      //System.out.println(buf);
      return Integer.parseInt(buf);
  }
  
}
