package sg.nus.edu.iss.vttp5a_ssf_day19_task2.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.edu.iss.vttp5a_ssf_day19_task2.repo.MapRepo;

@Service
public class FileReaderService {
    @Autowired
    MapRepo mapRepo;

    public void readJSONFile(String redisKey) {
        try {
            ClassPathResource resource = new ClassPathResource("products.json");
            JsonReader jsonReader = Json.createReader(resource.getInputStream());
            JsonArray jsonArray = jsonReader.readArray();

            for(int i = 0; i < jsonArray.size(); i++){
                JsonObject jsonObject = jsonArray.getJsonObject(i);
                mapRepo.create(redisKey, String.valueOf(jsonObject.getInt("id")), jsonObject.toString());
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
            
        }
    }
}
