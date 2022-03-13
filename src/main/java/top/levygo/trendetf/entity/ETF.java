package top.levygo.trendetf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description：ETF信息
 * @author：LevyXie
 * @create：2022-03-08 11:29
 */
@Data
@TableName("etfcodes")//指定表名
public class ETF {
    private long id;
    private String code;
    private String name;
}
