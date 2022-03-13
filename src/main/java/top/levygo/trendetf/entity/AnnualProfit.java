package top.levygo.trendetf.entity;

import lombok.Data;

/**
 * @description：封装每年收益的实体类
 * @author：LevyXie
 * @create：2022-03-08 12:42
 */
@Data
public class AnnualProfit {
    private int year;
    private float indexIncome;
    private float trendIncome;
}
