package com.pw.example.demo4.config.shiro;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/7/31.
 */
@Component
public class RedisSessionDao extends AbstractSessionDAO {
    private final static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    private final static String KEYPRE="shiro:user";
    // Session超时时间，单位为毫秒
    private long expireTime=1800000;

    @Autowired
    private RedisTemplate redisTemplate;

    long sessionExpireTime;

    public RedisSessionDao() {
        super();
    }

    public RedisSessionDao(long expireTime, RedisTemplate redisTemplate) {
        super();
        this.expireTime = expireTime;
        this.redisTemplate = redisTemplate;
    }

    @Override // 更新session
    public void update(Session session) throws UnknownSessionException {
        logger.info("session===============update================");
        if (session == null || session.getId() == null) {
            return;
        }
        session.setTimeout(expireTime);
        redisTemplate.opsForValue().set(KEYPRE+session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override // 删除session
    public void delete(Session session) {
        logger.info("session===============delete================");
        if (null == session) {
            return;
        }
        redisTemplate.opsForValue().getOperations().delete(KEYPRE+session.getId());
    }

    @Override
    // 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，可以在将session加入redis时指定一个session前缀，统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合
    public Collection<Session> getActiveSessions() {
        logger.info("session==============getActiveSessions=================");
        return redisTemplate.keys(KEYPRE+"*");
    }

    @Override// 加入session
    protected Serializable doCreate(Session session) {
        logger.info("session===============doCreate================");
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        redisTemplate.opsForValue().set(KEYPRE+session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
        return sessionId;
    }

    @Override// 读取session
    protected Session doReadSession(Serializable sessionId) {
        logger.info("session==============doReadSession=================");
        if (sessionId == null) {
            return null;
        }
        return (Session) redisTemplate.opsForValue().get(KEYPRE+sessionId);
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;

    }
}

