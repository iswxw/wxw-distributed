package com.wxw.common.Idempotency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 15:00 2020/12/23
 * @description：用于操作 Token 相关的 Service 类，里面存在 Token 创建与验证方法
 *  1. Token 创建方法： 使用 UUID 工具创建 Token 串，设置以 “idempotent_token:“+“Token串” 作为 Key，
 *       以用户信息当成 Value，将信息存入 Redis 中。
 *  2. Token 验证方法： 接收 Token 串参数，加上 Key 前缀形成 Key，再传入 value 值，执行 Lua 表达式
 *    （Lua 表达式能保证命令执行的原子性）进行查找对应 Key 与删除操作。执行完成后验证命令的返回结果，
 *    如果结果不为空且非0，则验证成功，否则失败。
 * @link: https://xie.infoq.cn/article/f00d4cf4368e0c98d9543b9a3
 * @version: v_0.0.1
 */
@Slf4j
@Service
public class TokenUtilService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 存入 Redis 的 Token 键的前缀
     */
    private static final String IDEMPOTENT_TOKEN_PREFIX = "idempotent_token:";

    /**
     * 创建 Token 存入 Redis，并返回该 Token
     *
     * @param value 用于辅助验证的 value 值
     * @return 生成的 Token 串
     */
    public String generateToken(String value) {
        // 实例化生成 ID 工具对象
        String token = UUID.randomUUID().toString();
        // 设置存入 Redis 的 Key
        String key = IDEMPOTENT_TOKEN_PREFIX + token;
        // 存储 Token 到 Redis，且设置过期时间为5分钟
        stringRedisTemplate.opsForValue().set(key, value, 5, TimeUnit.MINUTES);
        // 返回 Token
        return token;
    }

    /**
     * 验证 Token 正确性
     * @param token token 字符串
     * @param value value 存储在Redis中辅助验证信息
     * @return
     */
    public boolean validToken(String token,String value){
        // 设置 Lua 脚本，其中 KEYS 是 key，KEYS 是 value
        String script = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        // 根据 Key 前缀拼接 Key
        String key = IDEMPOTENT_TOKEN_PREFIX + token;
        // 执行 Lua 脚本
        Long result = stringRedisTemplate.execute(redisScript, Arrays.asList(key, value));
        // 根据返回结果判断是否成功成功匹配并删除 Redis 键值对，若果结果不为空和0，则验证通过
        if (result != null && result != 0L) {
            log.info("验证 token={},key={},value={} 成功", token, key, value);
            return true;
        }
        log.info("验证 token={},key={},value={} 失败", token, key, value);
        return false;
    }





}
