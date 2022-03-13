package top.levygo.trendetf.utils;

import top.levygo.trendetf.entity.AnnualProfit;
import top.levygo.trendetf.entity.ETFData;
import top.levygo.trendetf.entity.Profit;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description：计算ETF买卖逻辑参数
 * @author：LevyXie
 * @create：2022-03-08 14:43
 */
public class TrendUtil {
    //获取日线值
    public static float getMA(int i, int ma, List<ETFData> data) {
        int start = i - 1 - ma;
        int end = i - 1;
        if (start < 0) {
            return 0;
        }
        float sum = 0;
        float avg = 0;
        for (int j = start; j < end; j++) {
            ETFData tick = data.get(j);
            sum += tick.getClosePoint();
        }
        avg = sum / (end - start);
        return avg;
    }

    //获取买入期内的最高点位或ma内的最高点位，用于计算回撤
    public static float getMax(int i, int ma, int lastBuyPoint, List<ETFData> data) {
        int start = i - 1 - ma;
        if(start < lastBuyPoint){
            start = lastBuyPoint;
        }
        int end = i - 1;
        if (start < 0) {
            return 0;
        }
        float max = 0;
        for (int j = start; j < end; j++) {
            ETFData tick = data.get(j);
            float closePoint = tick.getClosePoint();
            if(closePoint > max){
                max = closePoint;
            }
        }
        return max;
    }

    //向上突破均线，返回true,提示买入
    public static boolean buyFlag(float buyRate, float closePoint, float avg) {
        float increaseV = closePoint - avg;
        float increaseR = increaseV / avg;
        if (increaseR > buyRate) {
            return true;
        }
        return false;
    }

    //向下扩大回撤，返回true,提示卖出
    public static boolean sellFlag(float sellRate, float closePoint, float max) {
        float decreaseV = max - closePoint;
        float decreaseR = decreaseV / max;
        if (decreaseR > sellRate) {
            return true;
        }
        return false;
    }
    //计算每年的年化收益
    public static List<AnnualProfit> calculateAnnualProfits(List<ETFData> data, List<Profit> profits) throws ParseException {
        List<AnnualProfit> result = new ArrayList<>();
        String startDate = data.get(0).getDate();
        String endDate = data.get(data.size()-1).getDate();

        int startYear = DateUtil.getYear(DateUtil.StringToDate(startDate));
        int endYear = DateUtil.getYear(DateUtil.StringToDate(endDate));

        for (int year = startYear; year <= endYear; year++) {
            AnnualProfit annualProfit = new AnnualProfit();
            annualProfit.setYear(year);

            float indexIncome = getIndexIncome(year,data);
            float trendIncome = getTrendIncome(year,profits);
            annualProfit.setIndexIncome(indexIncome);
            annualProfit.setTrendIncome(trendIncome);
            result.add(annualProfit);
        }
        return result;
    }
    //获取指定年份的Trend收益
    private static float getTrendIncome(int year, List<Profit> profits) throws ParseException {
        Profit first = null;
        Profit last = null;

        for (Profit profit : profits) {
            String date = profit.getDate();
            int currentYear = DateUtil.getYear(date);
            if (currentYear == year) {
                if (null == first) first = profit;
                last = profit;
            }
            if (currentYear > year) break;
        }
        return (last.getValue() - first.getValue()) / first.getValue();
    }
    //获取指定年份的指数收益
    private static float getIndexIncome(int year, List<ETFData> datas) throws ParseException {
        ETFData first = null;
        ETFData last = null;

        for (ETFData data : datas) {
            String date = data.getDate();
            int currentYear = DateUtil.getYear(date);
            if (currentYear == year) {
                if (null == first) first = data;
                last = data;
            }
            if (currentYear > year) break;
        }
        return (last.getClosePoint() - first.getClosePoint()) / first.getClosePoint();
    }
}
