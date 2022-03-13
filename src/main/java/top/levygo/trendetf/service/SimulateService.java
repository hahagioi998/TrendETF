package top.levygo.trendetf.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.levygo.trendetf.entity.AnnualProfit;
import top.levygo.trendetf.entity.ETFData;
import top.levygo.trendetf.entity.Profit;
import top.levygo.trendetf.entity.Trade;
import top.levygo.trendetf.mapper.ETFDataMapper;
import top.levygo.trendetf.utils.TrendUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description：按照输入的指定参数模拟交易，返回数据
 * @author：LevyXie
 * @create：2022-03-08 12:49
 */
@Service
public class SimulateService {
    @Autowired
    private ETFDataMapper etfDataMapper;
    public Map<String,Object> getData(int ma, float sellRate, float buyRate, float serviceCharge,
                                      String code, String startDate, String endDate) {
        //从数据库取得指数数据
        QueryWrapper<ETFData> wrapper = new QueryWrapper<>();
        wrapper.eq("datacode", code);
        wrapper.between("date", startDate, endDate);
        wrapper.orderByAsc("date");
        List<ETFData> data = etfDataMapper.selectList(wrapper);

        //收益及交易列表初始化
        List<Profit> profits =new ArrayList<>();
        List<Float> simulateData =new ArrayList<>();
        List<Trade> trades = new ArrayList<>();

        float initCash = 1000;
        float cash = initCash;//现金
        float share = 0;//持仓份额
        float value = 0;

        float init = data.get(0).getClosePoint();

        int winCount = 0;
        int lossCount = 0;
        float totalWinRate = 0;
        float totalLossRate = 0;
        float avgWinRate = 0;
        float avgLossRate = 0;

        int lastBuyPoint = -1;

        for (int i = 0; i < data.size(); i++) {
            ETFData tick = data.get(i);
            float closePoint = tick.getClosePoint();
            float avg = TrendUtil.getMA(i, ma, data);
            float max = TrendUtil.getMax(i, ma, lastBuyPoint, data);

            if(avg != 0){
                if( 0 == share && TrendUtil.buyFlag(buyRate, closePoint,avg)){
                    //买入点判断，满足突破均线条件且未持仓
                    share = cash / closePoint;
                    cash = 0;
                    //添加交易记录
                    Trade trade = new Trade();
                    trade.setBuyDate(tick.getDate());
                    trade.setBuyClosePoint(tick.getClosePoint());
                    trade.setSellDate("N/A");
                    trade.setSellClosePoint(0);
                    trades.add(trade);

                    lastBuyPoint = i;
                }else if(0 != share &&TrendUtil.sellFlag(sellRate, closePoint, max)){
                    //卖出点判断，满足突破回撤条件且持仓
                    cash = closePoint * share * (1 - serviceCharge);
                    share = 0;
                    //补全交易记录
                    Trade trade = trades.get(trades.size()-1);
                    trade.setSellDate(tick.getDate());
                    trade.setSellClosePoint(tick.getClosePoint());
                    trade.setRate(cash / initCash);
                    //胜率计算
                    if(trade.getSellClosePoint()-trade.getBuyClosePoint()>0) {
                        winCount++;
                        totalWinRate += (trade.getSellClosePoint() - trade.getBuyClosePoint()) / trade.getBuyClosePoint();
                    }else{
                        lossCount++;
                        totalLossRate += (trade.getBuyClosePoint() - trade.getSellClosePoint()) / trade.getBuyClosePoint();
                    }
                }
            }
            //计算value值
            if(share != 0) {
                value = closePoint * share;
            }
            else {
                value = cash;
            }
            //计算收益率
            float rate = value / initCash;

            avgWinRate = totalWinRate / winCount;
            avgLossRate = totalLossRate / lossCount;

            //获取每个tick的收益
            simulateData.add(rate * init);
            Profit profit = new Profit();
            profit.setDate(tick.getDate());
            profit.setValue(rate * init);
            profits.add(profit);
        }
        float lastClosePoint = data.get(data.size()-1).getClosePoint();
        if(share != 0) {
            cash = lastClosePoint * share;
            share = 0;
        }
        Map<String,Object> map = new HashMap<>();
        List<AnnualProfit> annualProfits = null;
        try {
            annualProfits = TrendUtil.calculateAnnualProfits(data, profits);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //每个交易日的模拟交易数据
        map.put("simulateData", simulateData);
        //每个年份的收益情况
        map.put("annualProfits", annualProfits);
        //统计期间所有交易的成功次数
        map.put("winCount", winCount);
        //统计期间所有交易的失败次数
        map.put("lossCount", lossCount);
        //所有成功交易的平均收益(0+)
        map.put("avgWinRate", avgWinRate);
        //所有成功交易的平均收益(0+)
        map.put("avgLossRate", avgLossRate);
        //统计期间的所有交易情况
        map.put("trades", trades);
        return map;
    }
}
