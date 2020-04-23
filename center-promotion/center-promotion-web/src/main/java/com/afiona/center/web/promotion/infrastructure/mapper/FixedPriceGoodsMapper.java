package com.afiona.center.web.promotion.infrastructure.mapper;

import com.afiona.center.web.promotion.infrastructure.model.FixedPriceGoodsDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 一口价关联折扣商品列表mapper
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Mapper
public interface FixedPriceGoodsMapper extends BaseMapper<FixedPriceGoodsDO> {
}
