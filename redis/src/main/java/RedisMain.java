import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisMain {

    private static Logger log = LoggerFactory.getLogger(RedisMain.class);

    RedisMain(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");

        jedis.set("test","HelloRedis");

        log.info("test:"+jedis.get("test"));

        log.info("存储数据到列表：");
        saveToList(jedis);
    }

    /**
     * 存储数据到列表
     * @param jedis
     */
    private void saveToList(Jedis jedis){

        jedis.lpush("list","list");
        jedis.lpush("list","list-1");
        jedis.lpush("list","list-2");

        // 获取存储的数据并输出
        List<String> list = jedis.lrange("list", 0 ,2);
        for(Object obj:list)
            log.info("list:"+obj.toString());
    }

    public static void main(String[] args){

        new RedisMain();
    }
}
