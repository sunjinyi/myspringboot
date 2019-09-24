package com.hm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.mapper.ProductMapper;
import com.hm.mapper.ProductRobbingRecordMapper;
import com.hm.pojo.Product;
import com.hm.pojo.ProductRobbingRecord;

@Service
public class ConcurrencyService {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductRobbingRecordMapper productRobbingRecordMapper;

	private static final String PRODUCTNO = "product_10010";

	public void manageRobbing(String mobile) {
		try {
			Product product = productMapper.selectByProductNo(PRODUCTNO);
			if (product != null && product.getTotal() > 0) {
				int result = productMapper.updateTotal(product);
				if (result > 0) {
					ProductRobbingRecord entity = new ProductRobbingRecord();
					entity.setMobile(mobile);
					entity.setProductId(product.getId());
					productRobbingRecordMapper.insertSelective(entity);

					System.out.println("单手机号成功：" + mobile);
				}
			}
		} catch (Exception e) {
			System.out.println("处理抢单发生异常：mobile：" + mobile);
		}

	}

}
