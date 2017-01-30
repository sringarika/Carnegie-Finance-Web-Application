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
            
            List<Map<String, Double>> mapList = new ArrayList<Map<String, Double>>();
            List<List<String>> dateList = new ArrayList<List<String>>();

            int[] fundIdList = new int[funds.length];
            for (int i = 0; i < funds.length; i++) {
                fundIdList[i] = funds[i].getFundId();
            }
            Arrays.sort(fundIdList);
            
            for (int i = 0; i < fundIdList.length; i++) {
                Map<String, Double> priceHistoryMap = new HashMap<String, Double>();
                List<String> dates = new ArrayList<String>();
                FundPriceHistory[] history = fundPriceHistoryDAO.priceTrend(fundIdList[i]);
                if (history == null || history.length == 0) {
                    priceHistoryMap.put("N/A", null);
                    dates.add("N/A");
                    System.out.println(dates);
                }
                for (int j = 0; j < history.length; j++) {
                    priceHistoryMap.put(history[j].getExecuteDate(), history[j].getPrice());
                    dates.add(history[j].getExecuteDate());
                    System.out.println(dates);
                }
                mapList.add(priceHistoryMap);
                if (dates.size() > 1) {
                Collections.sort(dates);
                }
                dateList.add(dates);
            }
            
            ResearchFundView[] researchFundList = new ResearchFundView[funds.length];
            for (int i = 0; i < researchFundList.length; i++) {
                Fund fund = fundDAO.read(fundIdList[i]);
                List<String> datesOfFund = dateList.get(i);
            System.out.println(datesOfFund.size());
                String lastDate = datesOfFund.get(datesOfFund.size() -1);
                Map<String, Double> mapOfFund = mapList.get(i);
                Double price = mapOfFund.get(lastDate);
                ResearchFundView researchFund = merge(fund, lastDate, price);
                researchFundList[i] = researchFund;
            }
            
            request.setAttribute("researchFundList", researchFundList);
            //request.setAttribute("priceHistoryMap", priceHistoryMap);
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
