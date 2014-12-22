package com.newsmetro.service;

import com.newsmetro.constant.RedisConstants;
import com.newsmetro.dao.TargetPointMapper;
import com.newsmetro.enumeration.TargetStatus;
import com.newsmetro.po.TargetPoint;
import com.newsmetro.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.List;

@Service
public class TargetPointService {

	@Autowired
	private TargetPointMapper targetPointMapper;
	
	public void addTargetPoint(TargetPoint target){
        targetPointMapper.save(target);
	}

	public List<TargetPoint> getTargetListByUserId(int userId){

		List<TargetPoint> list = targetPointMapper.findTargetByUserIdAndStatus(userId,null);
		return list;
	}

	public List<TargetPoint> getRegularTargetListByUserId(int userId) {
        List<TargetPoint> list = targetPointMapper.findTargetByUserIdAndStatus(userId, TargetStatus.REGULAR);
        return list;
    }

    public TargetPoint getTargetById(Integer id){
        return targetPointMapper.getTargetById(id);
    }

	public void deleteTarget(Integer id){
        targetPointMapper.deleteById(id);
	}

	public void updateTarget(TargetPoint target){
        targetPointMapper.update(target);
	}

    public String getTargetMd5(Integer id) {
        if(id==null){
            return null;
        }
        String md5 = getTargetMd5FromRedis(id);
        if(StringUtils.isEmpty(md5)){
            TargetPoint t = targetPointMapper.getTargetById(id);
            md5 = t.getMd5();
        }
        return md5;
    }

    private String getTargetMd5FromRedis(final Integer id){
        return RedisUtil.execute(RedisConstants.REDIS_POOL_MASTER, new RedisUtil.RedisCallback<String>() {
            public String doRedis(Jedis jedis) {
                String key = RedisConstants.REDIS_KEY_TARGET_MD5_PREFIX + id.toString();
                return jedis.get(key);
            }
        });
    }
}
