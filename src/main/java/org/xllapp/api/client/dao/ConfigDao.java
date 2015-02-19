package org.xllapp.api.client.dao;

import java.util.List;
import java.util.Map;

import org.xllapp.api.client.entity.Config;

import com.ffcs.icity.mybatis.MyBatisRepository;

/**
*
*
* @author dylan.chen Nov 10, 2014
* 
*/
@MyBatisRepository
public interface ConfigDao {
	
	List<Config> query(Map<String, Object> parameters);
	
}