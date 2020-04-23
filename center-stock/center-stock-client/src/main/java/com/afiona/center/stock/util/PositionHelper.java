package com.afiona.center.stock.util;

import com.afiona.center.stock.domain.model.Warehouse;

import static com.afiona.center.stock.constants.PositionConstants.*;

/**
 * 位置辅助类
 *
 * @author dengweiyi
 * @date 2020-02-25
 */
public class PositionHelper {
    public static boolean isPositionCorrect(Warehouse.Position position){
        if(position == null){
            return false;
        }
        return position.getLongitude() >= MIN_LONGITUDE && position.getLongitude() <= MAX_LONGITUDE
                && position.getLatitude() >= MIN_LATITUDE && position.getLatitude() <= MAX_LATITUDE;
    }
}
