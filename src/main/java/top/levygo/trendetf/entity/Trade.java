package top.levygo.trendetf.entity;

import lombok.Data;


/**
 * @description：封装单次交易记录的实体类
 * @author：LevyXie
 * @create：2022-03-08 12:40
 */
@Data
public class Trade {
    private String buyDate;
    private String sellDate;
    private float buyClosePoint;
    private float sellClosePoint;
    private float rate;
}
