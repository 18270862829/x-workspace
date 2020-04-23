package com.afiona.center.stock.util;

import com.afiona.center.stock.model.IdAndCount;
import com.afiona.center.stock.model.command.StockCommand;
import org.assertj.core.util.Lists;

/**
 * 销售库存工厂
 *
 * @author dengweiyi
 * @date 2020-02-26
 */
public class StockFactory {
    public static StockCommand createCommand(){
        StockCommand command = new StockCommand();
        command.setStoreId(10000L);
        command.setIdAndCountList(Lists.newArrayList(new IdAndCount(10000L, 1)));
        return command;
    }
}
