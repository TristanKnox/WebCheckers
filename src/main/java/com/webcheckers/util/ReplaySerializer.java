package com.webcheckers.util;

import com.webcheckers.model.Replay;

import java.io.*;

public class ReplaySerializer {

  public static void serialize(Replay replay){
    try {
      FileOutputStream fileOut = new FileOutputStream("appl/replay.txt");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(replay);
      out.close();
      fileOut.close();
      System.out.printf("Serialized data is saved in replay.ser");
    } catch (IOException i) {
      i.printStackTrace();
    }
  }


    public static Replay deserialize(String fileName){
      Replay replay = null;
      try {
          FileInputStream fileIn = new FileInputStream(fileName);
          ObjectInputStream in = new ObjectInputStream(fileIn);
          replay = (Replay) in.readObject();
          System.out.println("Replay Created");
          in.close();
          fileIn.close();
      } catch (FileNotFoundException e){
          System.out.println(fileName + " not found");
      } catch (IOException i) {
        i.printStackTrace();
        return null;
      } catch (ClassNotFoundException c) {
        System.out.println("Replay class not found");
        c.printStackTrace();
        return null;
      }
      return replay;
    }
}
