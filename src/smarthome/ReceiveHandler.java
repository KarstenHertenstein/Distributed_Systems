/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthome;

import org.apache.thrift.TException;
import smarthome.ReceiveStatusService;


/**
 *
 * @author fs
 */
public class ReceiveHandler implements ReceiveStatusService.Iface {

    dataManager data = new dataManager();

    @Override
    public String receiveTemperatur(int n1) throws TException {
        if (!dataManager.temperatur.isEmpty() && n1 < dataManager.temperatur.size() - 1) {
            return dataManager.temperatur.size() - 1 + "$" + dataManager.temperatur.get(dataManager.temperatur.size() - 1);
        } else {
            return "No new Temperatur-data available";
        }
    }
    
    @Override
    public String receiveLuftfeuchtigkeit(int n1) throws TException {
        if (!dataManager.luftfeuchtigkeit.isEmpty() && n1 < dataManager.luftfeuchtigkeit.size() - 1) {
            return dataManager.luftfeuchtigkeit.size() - 1 + "$" + dataManager.luftfeuchtigkeit.get(dataManager.luftfeuchtigkeit.size() - 1);
        } else {
            return "No new Luftfeuchtigkeit data available";
        }
    }

    @Override
    public String receiveHelligkeit(int n1) throws TException {
        if (!dataManager.helligkeit.isEmpty() && n1 < dataManager.helligkeit.size() - 1) {
            return dataManager.helligkeit.size() - 1 + "$" + dataManager.helligkeit.get(dataManager.helligkeit.size() - 1);
        } else {
            return "No new Helligkeit data available";
        }
    }
}
