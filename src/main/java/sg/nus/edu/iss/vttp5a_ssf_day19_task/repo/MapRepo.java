package sg.nus.edu.iss.vttp5a_ssf_day19_task.repo;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp5a_ssf_day19_task.constants.Constants;

@Repository
public class MapRepo {
    @Autowired
    @Qualifier(Constants.TEMPLATE01)
    RedisTemplate<String, String> redisTemplate;

    public void create(String redisKey, String hashKey, String hashValue){
        redisTemplate.opsForHash().put(redisKey, hashKey, hashValue);
    }

    public Object get(String redisKey, String hashKey){
        return redisTemplate.opsForHash().get(redisKey, hashKey);
    }

    public long delete(String redisKey, String hashKey){
        return redisTemplate.opsForHash().delete(redisKey, hashKey);
    }

    public boolean keyExists(String redisKey, String hashKey){
        return redisTemplate.opsForHash().hasKey(redisKey, hashKey);
    }

    public Map<Object, Object> getEntries(String redisKey){
        return redisTemplate.opsForHash().entries(redisKey);
    }

    public Set<Object> getKeys(String redisKey){
        return redisTemplate.opsForHash().keys(redisKey);
    }

    public long size(String redisKey){
        return redisTemplate.opsForHash().size(redisKey);
    }

    public void exprie(String redisKey, Integer expireValue){
        Duration expiredDuration = Duration.ofSeconds(expireValue);
        redisTemplate.expire(redisKey, expiredDuration);
    }
}
