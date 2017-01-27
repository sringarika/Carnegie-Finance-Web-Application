package cfs.model;

import java.util.Map;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.FundPriceHistory;

public class FundPriceHistoryDAO extends GenericDAO <FundPriceHistory> {
    public FundPriceHistoryDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(FundPriceHistory.class, tableName, cp);
    }

    public Double latestFundPRice(int fundId, String executeDate) throws RollbackException {
    	FundPriceHistory[] fundPrice = match(MatchArg.and(MatchArg.equals("executeDate", executeDate),
    	        MatchArg.equals("fundId", fundId)));
    	if (fundPrice.length == 0) {
    		return null;
    	}
    	return fundPrice[0].getPrice();
    }

    // return the  price history of input fund -- display trend
    public FundPriceHistory[] priceTrend(int fundId) throws RollbackException {
    	FundPriceHistory[] prices= match(MatchArg.equals("fundId", fundId));
    	if (prices == null) {
    		return null;
    	}
    	return prices;
    }

    public void updatePrice(Map<Integer, Double> closingPrice, String transitionDate, String lastTransitionDate) throws RollbackException {
        try {
            Transaction.begin();
            String realLastTransitionDate = getLastClosingDate();
            if (realLastTransitionDate == null) {
                if (lastTransitionDate != null) {
                    throw new RollbackException("Another employee just finished a transition day while you were editing. Please try again.");
                }
            } else {
                if (!realLastTransitionDate.equals(lastTransitionDate)) {
                    throw new RollbackException("Another employee just finished a transition day while you were editing. Please try again.");

                }
            }
            for (int fundId : closingPrice.keySet()) {
    	        FundPriceHistory fundPrice = new FundPriceHistory(transitionDate, fundId, closingPrice.get(fundId));
                create(fundPrice);
            }
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

    // return the last closing date
    public String getLastClosingDate() throws RollbackException {
        FundPriceHistory[] history = match(MatchArg.max("executeDate"));
        if (history.length == 0) {
            return null;
        } else {
            return history[0].getExecuteDate();
        }
    }
}
