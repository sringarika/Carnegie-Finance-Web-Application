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
    
    // return the latest price of each fund for processing pending transactions
    public Double latestFundPRice(int fundId, String executeDate) throws RollbackException {
    	FundPriceHistory[] fundPrice = match(MatchArg.and(MatchArg.equals("executeDate", executeDate),
    	        MatchArg.equals("fundId", fundId)));
    	if (fundPrice == null) {
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
    
    public void updatePrice (Map<Integer, Double> closingPrice, String transitionDate) throws RollbackException {
    	FundPriceHistory[] fundPrices = match();
    	for (FundPriceHistory fundPrice : fundPrices) {
    		if (closingPrice.get(fundPrice.getFundId()) == null) {
    			throw new RollbackException("Fund no longer exists!");
    		} else {
    			fundPrice.setExecuteDate(transitionDate);
    			fundPrice.setPrice(closingPrice.get(fundPrice.getFundId()));
    			try {
    	    		Transaction.begin();
    	    		update(fundPrice);
    	    		Transaction.commit();
        		} finally {
                    if (Transaction.isActive()) Transaction.rollback();
                }
    		}
    	}
    }
    
    // return the last closing date
    public String getLastClosingDate() throws RollbackException {
        FundPriceHistory[] history = match(MatchArg.max("executeDate"));
        if (history == null) {
            return "No transition day before!";
        }
        return history[0].getExecuteDate();
    }
}
