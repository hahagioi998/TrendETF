package top.levygo.trendetf.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.levygo.trendetf.entity.ETF;
import top.levygo.trendetf.mapper.ETFMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description：获取etf基本信息
 * @author：LevyXie
 * @create：2022-03-08 11:59
 */
@Service
public class ETFService {
    @Autowired
    private ETFMapper etfMapper;

    public Map<String,Object> getCodes(){
        QueryWrapper<ETF> wrapper = new QueryWrapper<>();
        wrapper.select("code","name");
        wrapper.orderByAsc("code");
        List<ETF> etfs = etfMapper.selectList(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("etfs", etfs);
        return map;
    }
}
