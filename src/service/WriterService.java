package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriterService {
    private static WriterService instance = null;

    public String filePath;

    private WriterService()
    {
        filePath = "src\\files\\History.csv";
    }

    public static WriterService getInstance()
    {
        if (instance == null)
            instance = new WriterService();

        return instance;
    }

    public void writeToFile(String actionName, Date timestamp){
        try {
            FileWriter fileWriter = new FileWriter(String.valueOf(Paths.get(filePath)), true);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strTimestamp = dateFormat.format(timestamp);

            fileWriter.write(actionName +"," +strTimestamp  +"\n");
            fileWriter.close();

        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }
}
