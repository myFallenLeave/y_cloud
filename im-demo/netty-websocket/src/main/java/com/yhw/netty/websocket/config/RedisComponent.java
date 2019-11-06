package com.yhw.netty.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author yhw
 */
@Component
public class RedisComponent {

    @Autowired
    private  RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate(){
        return redisTemplate;
    }


    //============================== 字符串操作 =====================================


    /**
     * 持久化保存
     * @param key
     * @param value
     */
    public void setStr(String key,String value){
        getStringTemplate().set(key,value);
    }


    /**
     * 超时保存
     * @param key
     * @param value
     * @param secondsTimeOut 过期秒数
     */
    public void setStrEx(String key,String value,long secondsTimeOut){
        setStrEx(key, value, secondsTimeOut, TimeUnit.SECONDS);
    }


    /**
     * 超时保存
     * @param key
     * @param value
     * @param timeOut
     * @param timeUnit
     */
    public void setStrEx(String key,String value,long timeOut,TimeUnit timeUnit){
        getStringTemplate().set(key, value, timeOut, timeUnit);
    }


    public String  getStr(String key){
        return getStringTemplate().get(key);
    }


    private ValueOperations<String, String> getStringTemplate(){
        return redisTemplate.opsForValue();
    }


    //====================== object =======================================


    public Object getObj(String key){
        return getObjectTemplate().get(key);
    }

    /**
     * 保存的对象，必须要实现序列化接口
     **/
    public void setObjEx(String key,Object value,long timeOut){
        setObjEx(key,value,timeOut,TimeUnit.SECONDS);
    }


    public void setObj(String key,Object value){
        getObjectTemplate().set(key,value);
    }


    public void setObjEx(String key,Object value,long timeOut,TimeUnit timeUnit){
        getObjectTemplate().set(key,value,timeOut,timeUnit);
    }


    private ValueOperations<String, Object> getObjectTemplate(){
        return redisTemplate.opsForValue();
    }


    //===================== map ================================
    public Map getMap(String key){
        return getHashTemplate().entries(key);
    }


    public void setMap(String key,Map map){
        getHashTemplate().putAll(key,map);
    }



    public HashOperations getHashTemplate(){
        return redisTemplate.opsForHash();
    }


    //======================= other =========================


    public ListOperations getListTemplate(){
        return redisTemplate.opsForList();
    }




    public SetOperations getSetTemplate(){
        return redisTemplate.opsForSet();
    }




    public ZSetOperations getZsetTemplate(){
        return redisTemplate.opsForZSet();
    }


    //============================= 删除操作 =======================================


    /**
     * 删除单个
     * @param key
     */
    public void delKey(String key){
        redisTemplate.delete(key);
    }


    /**
     * 多个删除
     * @param keys
     */
    public void delKey(String... keys){
        Set<String> set = Stream.of(keys).collect(Collectors.toSet());
        redisTemplate.delete(set);
    }


    /**
     * 删除key集合
     * @param keys
     */
    public void delKey(Collection<String> keys){
        Set<String> set = keys.stream().collect(Collectors.toSet());
        redisTemplate.delete(set);
    }


    /**
     * 模糊删除(传入前置key)
     * @param prefixKey
     */
    public void delLikeKey(String prefixKey){
        Set<String> keys = redisTemplate.keys(prefixKey.concat("*"));
        redisTemplate.delete(keys);
    }

    public void send(String channel, Object message){
        redisTemplate.convertAndSend(channel,message);
    }
}
