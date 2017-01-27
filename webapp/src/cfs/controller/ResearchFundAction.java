package cfs.controller;


import java.util.ArrayList;
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
            FundPriceHistory[] history = fundPriceHistoryDAO.match();
            ResearchFundView[] fundList = merge(funds, history, request);
            request.setAttribute("fundList", fundList);
            //TODO
            // add a command that when mouse click on price, it shows a pop out graph.
            Map<String, Double> priceHistoryMap = new HashMap<String, Double>();
            List<Map<String, Double>> mapList = new ArrayList<Map<String, Double>>();
            if (history.length == 0 || history == null) {
                request.setAttribute("message", "The fund has no history");
                return "research-fund.jsp";
            }
            for (int i = 1; i <= history.length; i++) {
                int id = i;
                for (int j = 0; j < history.length; j++) {
                    if (history[j].getFundId() == id && history[j].getExecuteDate() != null) {
                        priceHistoryMap.put(history[j].getExecuteDate(), history[j].getPrice());
                    }
                }
                mapList.add(priceHistoryMap);
            }
            request.setAttribute("priceHistoryMap", priceHistoryMap);
            request.setAttribute("mapList", mapList);
            
            return "research-fund.jsp";
        } catch (RollbackException e) {
            e.printStackTrace();
            request.setAttribute("error",e.toString());
        }
        return "research-fund.jsp";
    }
    
    private ResearchFundView[] merge(Fund[] funds, FundPriceHistory[] history, HttpServletRequest request) {
        ResearchFundView[] fundList = new ResearchFundView[funds.length];
        if (fundList.length == 0 || fundList == null) {
            request.setAttribute("message", "Currently we have no fund.");
            return fundList;
        }
        for (int i = 0; i < fundList.length; i++) {
            ResearchFundView fund = new ResearchFundView();
            fund.setFundId(funds[i].getFundId());
            fund.setFundName(funds[i].getName());
            fund.setTicker(funds[i].getTicker());
            if (history == null || history.length == 0) {
                fund.setLastClosingDate("0/0/0");
                fund.setPrice(0.00);
                fundList[i] = fund;
                return fundList;
            } else if (fund.getFundId() == history[i].getFundId()){
                fund.setLastClosingDate(history[i].getExecuteDate());
                fund.setPrice(history[i].getPrice());
                fundList[i] = fund;
            }
        }

        return fundList;
    }
    
    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
