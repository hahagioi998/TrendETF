package top.levygo.trendetf.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * @description：单个tick(单日)的ETF数据
 * @author：LevyXie
 * @create：2022-03-08 11:31
 */
@Data
@TableName("etfdata")//指定表名
public class ETFData {
    private Long id;
    @TableId("datacode")//指定表中字段名
    private String dataCode;
    private String date;
    private float closePoint;
}
