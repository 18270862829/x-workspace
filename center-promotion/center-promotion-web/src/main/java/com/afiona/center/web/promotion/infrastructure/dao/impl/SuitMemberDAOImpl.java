package com.afiona.center.web.promotion.infrastructure.dao.impl;

import com.afiona.center.web.promotion.infrastructure.dao.SuitMemberDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.SuitMemberMapper;
import com.afiona.center.web.promotion.infrastructure.model.SuitMemberDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 适用对象dao实现
 *
 * @author LiJinXing
 * @date 2020/2/13
 */
@Component
public class SuitMemberDAOImpl extends ServiceImpl<SuitMemberMapper, SuitMemberDO> implements SuitMemberDAO {
}
