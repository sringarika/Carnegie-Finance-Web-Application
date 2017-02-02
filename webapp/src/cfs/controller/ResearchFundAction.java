package cfs.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
            List<List<String>> dateDispList = new ArrayList<List<String>>();

            int[] fundIdList = new int[funds.length];
            for (int i = 0; i < funds.length; i++) {
                fundIdList[i] = funds[i].getFundId();
            }
            Arrays.sort(fundIdList);

            for (int i = 0; i < fundIdList.length; i++) {
                Map<String, Double> priceHistoryMap = new HashMap<String, Double>();
                List<String> dates = new ArrayList<String>();
                List<String> dateDisps = new ArrayList<String>();
                FundPriceHistory[] history = fundPriceHistoryDAO.priceTrend(fundIdList[i]);
                if (history == null || history.length == 0) {
                    priceHistoryMap.put("N/A", null);
                    dates.add("N/A");
                    dateDisps.add("N/A");
                }
                for (int j = 0; j < history.length; j++) {
                    priceHistoryMap.put(history[j].getExecuteDate(), history[j].getPrice());
                    dates.add(history[j].getExecuteDate());
                    dateDisps.add(dateDisp(history[j].getExecuteDate()));
                }
                mapList.add(priceHistoryMap);
                request.setAttribute("priceHistoryMap", priceHistoryMap);
                if (dates.size() > 1) {
                Collections.sort(dates);
                }
                dateList.add(dates);
                dateDispList.add(dateDisps);
            }

            ResearchFundView[] researchFundList = new ResearchFundView[funds.length];
            for (int i = 0; i < researchFundList.length; i++) {
                Fund fund = fundDAO.read(fundIdList[i]);
                List<String> datesOfFund = dateList.get(i);
                String lastDate = datesOfFund.get(datesOfFund.size() -1);
                Map<String, Double> mapOfFund = mapList.get(i);
                Double price = mapOfFund.get(lastDate);
                ResearchFundView researchFund = merge(fund, dateDisp(lastDate), price);
                researchFundList[i] = researchFund;
            }

            request.setAttribute("researchFundList", researchFundList);
            request.setAttribute("mapList", mapList);
            request.setAttribute("dateList", dateList);
            request.setAttribute("dateDispList", dateDispList);
            return "research-fund.jsp";
        } catch (RollbackException e) {
            e.printStackTrace();
            request.setAttribute("error",e.toString());
            return "error.jsp";
        }
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
    private String dateDisp(String date) {
        if (date == null) return null;
        if (date.equals("N/A")) return "N/A";
        LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return (date1.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US"))));
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
