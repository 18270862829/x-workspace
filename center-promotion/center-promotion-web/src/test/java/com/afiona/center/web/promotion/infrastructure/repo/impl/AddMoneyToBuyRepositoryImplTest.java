package com.afiona.center.web.promotion.infrastructure.repo.impl;

import com.afiona.center.web.promotion.Application;
import com.afiona.center.web.promotion.domain.repo.AddMoneyToBuyRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AddMoneyToBuyRepositoryImplTest {
    @Resource
    private AddMoneyToBuyRepository addMoneyToBuyRepository;

    @Test
    public void removeBatch() {
        addMoneyToBuyRepository.removeBatch(Lists.newArrayList(379L));
    }
}