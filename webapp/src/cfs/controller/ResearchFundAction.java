package cfs.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import cfs.databean.Fund;
import cfs.databean.FundPriceHistory;
import cfs.model.FundDAO;
import cfs.model.FundPriceHistoryDAO;
import cfs.model.Model;
import cfs.viewbean.ResearchFundView;

public class ResearchFundAction extends Action {
    private FundDAO fundDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    public ResearchFundAction(Model model) {
        fundDAO = model.getFundDAO();
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
    }

    @Override
    public String getName() {
        return "research-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        try {
            Fund[] funds = fundDAO.match();
            if (funds.length == 0 || funds == null) {
                request.setAttribute("message", "Currently we have no fund.");
                return "research-fund.jsp";
            }
            FundPriceHistory[] historyList = fundPriceHistoryDAO.match();

            //TODO
            // add a command that when mouse click on price, it shows a pop out graph.
            Map<String, Double> priceHistoryMap = new HashMap<String, Double>();
            List<Map<String, Double>> mapList = new ArrayList<Map<String, Double>>();
            
            List<String> dates = new ArrayList<String>();
            List<List<String>> dateList = new ArrayList<List<String>>();
            
            // 先遍历一边找出所有的fundId，将fundId sort 为ascending order，然后根据fundId get所有的date和price的map，
            //将date排序，用array存起来，输出时用date去get map里的price
            if (historyList.length == 0 || historyList == null) {
                request.setAttribute("message", "No fund transaction history");
                return "research-fund.jsp";
            }
            int[] fundIdList = new int[funds.length];
            for (int i = 0; i < funds.length; i++) {
                fundIdList[i] = funds[i].getFundId();
            }
            Arrays.sort(fundIdList);
            
            for (int i = 0; i < fundIdList.length; i++) {
                if (historyList[i].getFundId() == fundIdList[i]) {
                    priceHistoryMap.put(historyList[i].getExecuteDate(), historyList[i].getPrice());
                    dates.add(historyList[i].getExecuteDate());
                }
                mapList.add(priceHistoryMap);
                Collections.sort(dates);
                dateList.add(dates);
            }
            
            ResearchFundView[] researchFundList = new ResearchFundView[funds.length];
            for (int i = 0; i < researchFundList.length; i++) {
                Fund fund = fundDAO.read(fundIdList[i]);
                FundPriceHistory[] history = fundPriceHistoryDAO.priceTrend(fundIdList[i]);
                if (history == null || history.length == 0) {
                    request.setAttribute("message", "The fund has no history");
                    return "research-fund.jsp";
                }
                List<String> datesOfFund = dateList.get(i);
                String lastDate = datesOfFund.get(datesOfFund.size() - 1);
                Map<String, Double> mapOfFund = mapList.get(i);
                Double price = mapOfFund.get(lastDate);
                ResearchFundView researchFund = merge(fund, lastDate, price);
                researchFundList[i] = researchFund;
            }
            
            request.setAttribute("researchFundList", researchFundList);
            request.setAttribute("priceHistoryMap", priceHistoryMap);
            request.setAttribute("mapList", mapList);
            
            return "research-fund.jsp";
        } catch (RollbackException e) {
            e.printStackTrace();
            request.setAttribute("error",e.toString());
        }
        return "research-fund.jsp";
    }
    private ResearchFundView merge (Fund fund, String lastDate, Double price) {
        ResearchFundView researchFund = new ResearchFundView();
        researchFund.setFundId(fund.getFundId());
        researchFund.setFundName(fund.getName());
        researchFund.setTicker(fund.getTicker());
        researchFund.setLastClosingDate(lastDate);
        researchFund.setPrice(price);
        return researchFund;      
    }
 
    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
