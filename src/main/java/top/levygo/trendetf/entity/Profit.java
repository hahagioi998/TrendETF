package top.levygo.trendetf.entity;

import lombok.Data;


/**
 * @description：单个tick(单日)的策略收益
 * @author：LevyXie
 * @create：2022-03-08 12:40
 */
@Data
public class Profit {
    private String date;
    private float value;
}
