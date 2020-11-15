/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthome;

import smarthome.ReceiveHandler;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerTransport;
import smarthome.ReceiveStatusService;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TServer;
/**
 * @author fs
 */
public class ThriftServer extends Thread {
    public static ReceiveHandler handler;
    public static ReceiveStatusService.Processor processor;
    
    
    public void start(){
        try{
            handler = new ReceiveHandler();
            processor = new ReceiveStatusService.Processor(handler);
            
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    simple(processor);
                }
            };
            
            new Thread(r).start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    public static void simple(ReceiveStatusService.Processor processor){
        try{
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
            
            System.out.println("Starting Thriftserver..");
            server.serve();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
