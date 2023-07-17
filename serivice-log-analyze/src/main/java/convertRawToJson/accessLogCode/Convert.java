package convertRawToJson.accessLogCode;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Convert {
//    @SuppressWarnings("unchecked")
	public static void Start(String file) {
        //specify the path of the input file
        // Create a JsonObject
		
		
        JsonObject jsonObject = new JsonObject();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
                String line;
                int lineNumber = 1;
                //read each line from the inputfile
                while((line = reader.readLine()) != null){
                        //preocess each line and extract log information
                        LogEntry logEntry = createLogEntry.extractLogEntry(line);
                        jsonObject.add("line_" + lineNumber, createLogEntry.create(logEntry));
                        //add log information to the JsonObject
                        lineNumber ++;
                }
        }catch (IOException e){
                System.out.println("Something has wrong");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(jsonObject);
        //specify the path of the output JSON file
        String outputfile = "src/main/resources/com/it/loganalyze/log/access.json";
        //write the Json string to the output
        try (FileWriter fileWriter = new FileWriter(outputfile)){
            fileWriter.write(jsonString);
        }catch (IOException e){
        		System.out.println("Something has wrong");
        }
	}
}