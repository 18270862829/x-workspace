package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.SecondsKillGoodsDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.SecondsKillGoodsMapper;
import com.afiona.center.web.promotion.infrastructure.model.SecondsKillGoodsDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 秒杀-参与商品dao实现
 *
 * @author LiJinXing
 * @date 2020/4/7
 */
@Component
public class SecondsKillGoodsDAOImpl extends ServiceImpl<SecondsKillGoodsMapper, SecondsKillGoodsDO> implements SecondsKillGoodsDAO {
}
