package sq.util;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisUtil {
    private RedisTemplate redisTemplate;
    private static RedisUtil cur;

    public static RedisUtil getInstance(RedisTemplate redisTemplate) {
        if (cur == null) {
            cur = new RedisUtil(redisTemplate);
        }
        return cur;
    }

    private RedisUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public  <T> List<T>  getList(String hashKey) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> hash = redisTemplate
                .opsForHash();

        Map<String, T> redisList = (HashMap)hash.entries(hashKey);
        if (redisList == null) {
            return null;
        }
        return redisList.values().stream().collect(Collectors.toList());
    }
    public <T> void upadteList(String hashKey,String key,T t) {
        HashOperations<String, Object, Object> hash = redisTemplate
                .opsForHash();
        Map<String, T> redisList = (Map) hash.entries(hashKey);
        if (redisList == null) {
            return;
        }
        hash.put(hashKey, key, t);
    }
    public <T> void deleteList(String key,String hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate
                .opsForHash();
        Map<String, T> redisList = (Map) hash.entries(hashKey);
        if (redisList == null) {
            return;
        }
        hash.delete(hashKey, key);
    }
}
