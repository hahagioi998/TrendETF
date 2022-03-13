package top.levygo.trendetf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.levygo.trendetf.entity.ETFData;
import top.levygo.trendetf.service.ETFDataService;
import top.levygo.trendetf.service.ETFService;
import top.levygo.trendetf.service.SimulateService;

import java.util.List;
import java.util.Map;

/**
 * @description：
 * @author：LevyXie
 * @create：2022-03-08 11:57
 */
@RestController
@CrossOrigin
@RequestMapping("/trend")
public class SimulateController {
    @Autowired
    private ETFService etfService;

    @Autowired
    private ETFDataService etfDataService;

    @Autowired
    private SimulateService simulateService;

    @RequestMapping("/codes")
    public Map<String, Object> viewCode(){
        return etfService.getCodes();
    }
    //查询所有的ETF代码
    @RequestMapping("/getAllData/{code}")
    public Map<String,Object> getAll(@PathVariable String code){
        return etfDataService.getAllData(code);
    }

    //根据起始日期查询指定的ETF数据
    @RequestMapping("/getDataByDate/{code}/{startDate}/{endDate}")
    public Map<String,Object> getDataByDate(
            @PathVariable String code,
            @PathVariable String startDate,
            @PathVariable String endDate){
        return etfDataService.getDataByDate(code,startDate,endDate);
    }

    //获取模拟交易数据
    @GetMapping("/simulate/{code}/{ma}/{buyThreshold}/{sellThreshold}/{serviceCharge}/{startDate}/{endDate}")
    public Map<String,Object> simulate(
            @PathVariable("code") String code,
            @PathVariable("ma") int ma,
            @PathVariable("buyThreshold") float buyThreshold,
            @PathVariable("sellThreshold") float sellThreshold,
            @PathVariable("serviceCharge") float serviceCharge,
            @PathVariable("startDate") String startDate,
            @PathVariable("endDate") String endDate){

        Map<String, Object> data = simulateService.getData(ma, sellThreshold, buyThreshold, serviceCharge, code, startDate, endDate);
        return data;

    }

}
