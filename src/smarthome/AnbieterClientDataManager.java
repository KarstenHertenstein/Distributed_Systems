/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthome;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 *
 * @author fs
 */
public class AnbieterClientDataManager {
    
    static List<String> temperaturListe = new ArrayList<>();
    static List<String> helligkeitListe = new ArrayList<>();
    static List<String> luftfeuchtigkeitListe = new ArrayList<>();
    final static String filepath = "data.txt";
    
    AnbieterClientDataManager(){
        
    }
    // In Listen speichern
    public void saveData(String s){
      s = removeIndex(s);
      if(s.contains("Temperatur")){
          temperaturListe.add(s);
      }else if(s.contains("Luftfeuchtigkeit")){
          luftfeuchtigkeitListe.add(s);
      }else if(s.contains("Helligkeit")){
          helligkeitListe.add(s);
      }
      persist(s);
    }
    
    // Index abschneiden, welcher zuvor für Abfrage von neusten Datensätzen hilfreich war
    public String removeIndex(String s){
      int index = s.indexOf("$");
      return s.substring(index + 1, s.length());
    }
    public void persist(String s){
        try{
            FileWriter fw = new FileWriter(filepath, true);
            fw.write(s+"\n");
            fw.flush();
            fw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
}
