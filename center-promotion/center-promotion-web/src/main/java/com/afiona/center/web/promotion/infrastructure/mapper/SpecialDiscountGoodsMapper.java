package com.afiona.center.web.promotion.infrastructure.mapper;

import com.afiona.center.web.promotion.infrastructure.model.SpecialDiscountGoodsDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 限时特价促销活动关联商品Mapper
 *
 * @author LiJinXing
 * @date 2020/2/18
 */
@Mapper
public interface SpecialDiscountGoodsMapper extends BaseMapper<SpecialDiscountGoodsDO> {
}
