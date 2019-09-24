package com.hm.mapper;

import com.hm.pojo.UserInfo;

public interface UserInfoMapper {
	UserInfo selectOne(Integer id);

	int save(UserInfo userInfo);

	int update(UserInfo userInfo);

	int delete(Integer id);
}
