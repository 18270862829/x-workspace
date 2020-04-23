package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.MutualExclusionPromotionDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.MutualExclusionPromotionMapper;
import com.afiona.center.web.promotion.infrastructure.model.MutualExclusionPromotionDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 促销活动互斥dao实现
 *
 * @author LiJinXing
 * @date 2020/2/27
 */
@Component
public class MutualExclusionPromotionDAOImpl extends ServiceImpl<MutualExclusionPromotionMapper, MutualExclusionPromotionDO> implements MutualExclusionPromotionDAO {
}
