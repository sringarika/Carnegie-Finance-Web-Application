package cfs.controller;

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
            if (funds.length == 0) {
                request.setAttribute("message", "Currently we have no fund.");
                return "research-fund.jsp";
            }
            FundPriceHistory[] history = fundPriceHistoryDAO.match();
            ResearchFundView[] fundList = merge(funds, history);
            request.setAttribute("fundList", fundList);
            return "research-fund.jsp";
        } catch (RollbackException e) {
            e.printStackTrace();
            request.setAttribute("error",e.toString());
        }
        return "research-fund.jsp";
    }
    private ResearchFundView[] merge(Fund[] funds, FundPriceHistory[] history) {
        ResearchFundView[] fundList = new ResearchFundView[funds.length];

        for (int i = 0; i < fundList.length; i++) {
            fundList[i].setFundId(funds[i].getFundId());
            fundList[i].setFundName(funds[i].getName());
            fundList[i].setTicker(funds[i].getTicker());
        }
        for (int j = 0; j< fundList.length; j++) {
            if (history[j].getFundId() == fundList[j].getFundId()) {
                fundList[j].setLastClosingDate(history[j].getExecuteDate());
                fundList[j].setPrice(history[j].getPrice());
            }
        }
        return fundList;
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
