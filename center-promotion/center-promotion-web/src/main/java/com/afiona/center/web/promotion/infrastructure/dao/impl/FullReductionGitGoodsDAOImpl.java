package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.FullReductionGitGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.FulleReductionGitGoodsMapper;
import com.afiona.center.web.promotion.infrastructure.model.FullReductionGitGoodsDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 满减促销活动关联赠品dao实现
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Component
public class FullReductionGitGoodsDAOImpl extends ServiceImpl<FulleReductionGitGoodsMapper, FullReductionGitGoodsDO> implements FullReductionGitGoodsDAO {
}
