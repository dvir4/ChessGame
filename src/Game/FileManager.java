package Game;

import board.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;



public class FileManager {
    public static final String path  = "";
    public static HashMap<Location,String> loadPiecesMap() {
        HashMap<Location,String> toolMap = new HashMap<>();
        File file = new File(path);
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));
            String line = read.readLine();
            while (line != null) {
                String [] tokens = line.split(",");
                Location location = new Location( board.File.valueOf(tokens[0]),Integer.parseInt(tokens[1]));
                toolMap.put(location,tokens[2]+","+tokens[3]);
                line = read.readLine();
            }
        }catch (IOException e) {
            System.err.println("Failed reading file");
        }
        return toolMap;
    }

}
