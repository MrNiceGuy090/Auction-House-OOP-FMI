package service;
import java.io.*;
import java.nio.file.*;

public class ReaderService {
    private static ReaderService instance = null;

    private ReaderService() { }

    public static ReaderService getInstance()
    {
        if (instance == null)
            instance = new ReaderService();

        return instance;
    }

    public String readFile(String filename){
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
            String text = "";
            String line = "";
            while((line = reader.readLine()) != null) {
                text += line + "\n";
            }
            return text;
        } catch (NoSuchFileException e) {
            System.out.println("The file with the name " + filename + " doesn't exist.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
        return "";
    }

}
