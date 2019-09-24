package com.hm.mapper;

import java.util.List;

import com.hm.pojo.OrderRecord;

public interface OrderRecordMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(OrderRecord record);

	int insertSelective(OrderRecord record);

	OrderRecord selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(OrderRecord record);

	int updateByPrimaryKey(OrderRecord record);

	List<OrderRecord> selectAll();
}