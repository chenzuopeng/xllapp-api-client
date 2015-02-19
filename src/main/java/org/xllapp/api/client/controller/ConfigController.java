package org.xllapp.api.client.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.xllapp.api.client.dao.ConfigDao;

import com.ffcs.icity.api.core.CachedJSONController;
import com.ffcs.icity.api.core.exception.ApiException;
import com.ffcs.icity.api.core.exception.InvalidRequestArgumentException;

import static com.ffcs.icity.api.util.RequestArgumentHelper.*;

/**
 *
 *
 * @author dylan.chen Nov 10, 2014
 * 
 */
public class ConfigController extends CachedJSONController {

	private ConfigDao configDao;

	@Override
	public String getCacheKey(Map<String, Object> requestArgument) {
		String key = "";
		List<String> groups = getList(requestArgument, "groups");
		if (!CollectionUtils.isEmpty(groups)) {
			key = key + StringUtils.join(groups, "$");
		}
		List<String> keys = getList(requestArgument, "keys");
		if (!CollectionUtils.isEmpty(keys)) {
			key = key + "." + StringUtils.join(keys, "#");
		}
		return key;
	}

	@Override
	public void verifyBusiArgument(Map<String, Object> requestArgument) throws InvalidRequestArgumentException {
		if (CollectionUtils.isEmpty(getList(requestArgument, "groups")) && CollectionUtils.isEmpty(getList(requestArgument, "keys"))) {
			throw new InvalidRequestArgumentException("groups and keys can not both be empty");
		}
	}

	@Override
	public String[] getSignItems(Map<String, Object> requestArgument) {
		List<Object> items = new ArrayList<Object>();
		items.add(getString(requestArgument, "imsi"));
		items.add(getString(requestArgument, "imei"));
		List<String> groups = getList(requestArgument, "groups");
		if (!CollectionUtils.isEmpty(groups)) {
			items.addAll(groups);
		}
		List<String> keys = getList(requestArgument, "keys");
		if (!CollectionUtils.isEmpty(keys)) {
			items.addAll(keys);
		}
		return items.toArray(new String[0]);
	}

	@Override
	public Object handleRequest(Map<String, Object> requestArgument) throws ApiException {
		return this.configDao.query(requestArgument);
	}

	@Autowired
	public void setConfigDao(ConfigDao configDao) {
		this.configDao = configDao;
	}

}
