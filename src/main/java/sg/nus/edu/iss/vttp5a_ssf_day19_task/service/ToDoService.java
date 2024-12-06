package sg.nus.edu.iss.vttp5a_ssf_day19_task.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp5a_ssf_day19_task.repo.MapRepo;

@Service
public class ToDoService {
    @Autowired
    MapRepo mapRepo;

    public void create(String redisKey, String hashKey, String hashValue){
        mapRepo.create(redisKey, hashKey, hashValue);
    }

    public Object get(String redisKey, String hashKey){
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
