package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import cfs.databean.FundPriceHistory;
import cfs.model.FundPriceHistoryDAO;
import cfs.model.Model;

public class ResearchFundAction extends Action {

	private FundPriceHistoryDAO fundPriceHistoryDAO;
	
    public ResearchFundAction(Model model) {
    	fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
    }

    @Override
    public String getName() {
        return "research-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
    	if (request.getMethod().equals("GET")) {
    		// can we have a Date Databean/table to store all the transition day.
    		String lastTransitionDate = (String) request.getAttribute("date");
    		try {
    			FundPriceHistory[] prices = fundPriceHistoryDAO.researchFund(lastTransitionDate);
    			request.setAttribute("prices", prices);
			} catch (RollbackException e) {
				request.setAttribute("error", e.getMessage());
                return "research-fund.jsp";
			}
            return "research-fund.jsp";
        } else if (request.getMethod().equals("POST")) {
        	// add "displayTrend.do"
        	return "displayTrend.do";
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
