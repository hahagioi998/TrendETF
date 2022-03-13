package top.levygo.trendetf.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.levygo.trendetf.entity.ETFData;
import top.levygo.trendetf.mapper.ETFDataMapper;

import java.util.*;

/**
 * @description：查询ETF原始数据
 * @author：LevyXie
 * @create：2022-03-08 12:45
 */
@Service
public class ETFDataService {
    @Autowired
    private ETFDataMapper etfDataMapper;
    //获取所有日期的数据
    public Map<String,Object> getAllData(String code){
        QueryWrapper<ETFData> wrapper = new QueryWrapper<>();
        wrapper.eq("datacode", code);
        wrapper.select("date","close_point");
        wrapper.orderByAsc("date");
        List<ETFData> data = etfDataMapper.selectList(wrapper);

        List<String> dateList = new ArrayList<>();
        List<Float> closePointList = new ArrayList<>();
        for (ETFData dataItem : data) {
            dateList.add(dataItem.getDate());
            closePointList.add(dataItem.getClosePoint());
        }
        //提供指定指数数据的起始日期和结束日期
        String dataStartDate = dateList.get(0);
        String dataEndDate = dateList.get(data.size() - 1);

        HashMap<String, Object> map = new HashMap<>();
        map.put("date", dateList);
        map.put("closePoint", closePointList);
        map.put("dataStartDate", dataStartDate);
        map.put("dataEndDate", dataEndDate);
        return map;
    }

    //获取指定日期的数据
    public Map<String,Object> getDataByDate(String code, String dataStartDate, String dataEndDate){
        QueryWrapper<ETFData> wrapper = new QueryWrapper<>();
        wrapper.eq("datacode", code);
        wrapper.select("date","close_point");
        wrapper.orderByAsc("date");
        wrapper.between("date", dataStartDate, dataEndDate);
        List<ETFData> data = etfDataMapper.selectList(wrapper);

        List<String> dateList = new ArrayList<>();
        List<Float> closePointList = new ArrayList<>();
        for (ETFData dataItem : data) {
            dateList.add(dataItem.getDate());
            closePointList.add(dataItem.getClosePoint());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("date", dateList);
        map.put("closePoint", closePointList);
        return map;
    }
}
