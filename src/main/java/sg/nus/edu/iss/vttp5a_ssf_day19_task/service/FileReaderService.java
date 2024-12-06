package sg.nus.edu.iss.vttp5a_ssf_day19_task.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.constants.Constants;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.repo.MapRepo;

@Service
public class FileReaderService {

    @Autowired
    MapRepo mapRepo;

    public void loadFileToRedis(String dataDir){
        try{
            InputStream fis = new FileInputStream(new File(dataDir));
            JsonReader jsonReader = Json.createReader(fis);
            JsonArray jArray = jsonReader.readArray();

            for(int i = 0; i < jArray.size(); i++){
                JsonObject jObject = jArray.getJsonObject(i);

                JsonObject jObjectToSave = Json.createObjectBuilder().add("id", jObject.getString("id"))
                .add("name", jObject.getString("name")).add("description", jObject.getString("description"))
                .add("due_date", convertStringDateToLong(jObject.getString("due_date"))).add("priority_value", jObject.getString("priority_level"))
                .add("status", jObject.getString("status")).add("created_at", convertStringDateToLong(jObject.getString("created_at")))
                .add("updated_at", convertStringDateToLong(jObject.getString("updated_at"))).build();

                mapRepo.create(Constants.REDISKEY, jObjectToSave.getString("id"), jObjectToSave.toString());
            }
            
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
    }

    private String convertStringDateToLong(String date){
        LocalDateTime localDateTime = LocalDateTime.parse(date.substring(date.indexOf(",") + 2) 
        + " 00:00:00", DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
        ZoneId zoneId = ZoneId.of("UTC");
        long epochMilli = localDateTime.atZone(zoneId).toInstant().toEpochMilli();
        return String.valueOf(epochMilli);
    }
}
