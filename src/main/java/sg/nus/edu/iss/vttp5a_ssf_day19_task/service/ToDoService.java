package sg.nus.edu.iss.vttp5a_ssf_day19_task.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.constants.Constants;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.model.LocalDateConverter;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.model.ToDo;
import sg.nus.edu.iss.vttp5a_ssf_day19_task.repo.MapRepo;

@Service
public class ToDoService {
    @Autowired
    MapRepo mapRepo;

    public List<ToDo> getToDoListFromRedis(String redisKey){
        LocalDateConverter localDateConverter = new LocalDateConverter();
        Map<String, String> entries = getEntries(Constants.REDISKEY);
        List<ToDo> toDoList = new ArrayList<>();

        for(Entry<String, String> entry:entries.entrySet()){
            JsonReader jsonReader = Json.createReader(new StringReader(entry.getValue()));
            JsonObject jsonObject = jsonReader.readObject();
            System.out.println(jsonObject.getString("id"));
            System.out.println(jsonObject.getString("name"));
            System.out.println(jsonObject.getString("description"));


            ToDo toDo = new ToDo(jsonObject.getString("id"),jsonObject.getString("name"), 
            jsonObject.getString("description"), localDateConverter.convert(Long.parseLong(jsonObject.getString("due_date"))), 
            jsonObject.getString("priority_value"), jsonObject.getString("status"), 
            localDateConverter.convert(Long.parseLong(jsonObject.getString("created_at"))), 
            localDateConverter.convert(Long.parseLong(jsonObject.getString("updated_at"))));

            toDoList.add(toDo);
        }
        return toDoList;
    }

    public List<ToDo> getFilteredList(String redisKey, String priority){
        List<ToDo> toDoList = getToDoListFromRedis(redisKey);
        if(priority.equals("All")){
            return toDoList;
        } else {
            List<ToDo> filteredList = toDoList.stream().filter(x -> x.getStatus().equals(priority)).collect(Collectors.toList());
            return filteredList;
        }  
    }

    public String createJSONString(ToDo toDo){
        LocalDateConverter localDateConverter = new LocalDateConverter();

        JsonObject jObject = Json.createObjectBuilder().add("id", toDo.getId())
        .add("name", toDo.getName()).add("description", toDo.getDescription())
        .add("due_date", localDateConverter.convertStringDateToLong(toDo.getDueDate().toString()))
        .add("priority_value", toDo.getPriority()).add("status", toDo.getStatus())
        .add("created_at", localDateConverter.convertStringDateToLong(toDo.getCreatedAt().toString()))
        .add("updated_at", localDateConverter.convertStringDateToLong(toDo.getUpdatedAt().toString())).build();

        return jObject.toString();
    }

    public void create(String redisKey, String hashKey, String hashValue){
        mapRepo.create(redisKey, hashKey, hashValue);
    }

    public String get(String redisKey, String hashKey){
        return mapRepo.get(redisKey, hashKey);
    }

    public long delete(String redisKey, String hashKey){
        return mapRepo.delete(redisKey, hashKey);
    }

    public boolean keyExists(String redisKey, String hashKey){
        return mapRepo.keyExists(redisKey, hashKey);
    }

    public Map<String, String> getEntries(String redisKey){
        return mapRepo.getEntries(redisKey);
    }

    public Set<String> getKeys(String redisKey){
        return mapRepo.getKeys(redisKey);
    }

    public long size(String redisKey){
        return mapRepo.size(redisKey);
    }
}
