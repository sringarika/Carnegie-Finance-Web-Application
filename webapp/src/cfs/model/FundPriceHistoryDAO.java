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
    
    // return the latest price of each fund for the given last transition date -- research fund
    public FundPriceHistory[] researchFund(String lastTransitionDate) throws RollbackException {
    	FundPriceHistory[] fundPrice = match(MatchArg.equals("executeDate", lastTransitionDate));
    	if (fundPrice == null) {
    		return null;
    	}
    	return fundPrice;
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
    			throw new RollbackException("Error!!!");
    		} else {
    			fundPrice.setExecuteDate(transitionDate);
    			fundPrice.setPrice(closingPrice.get(fundPrice.getFundId()));
    			try {
    	    		Transaction.begin();
    	    		super.update(fundPrice);
    	    		Transaction.commit();
        		} finally {
                    if (Transaction.isActive()) Transaction.rollback();
                }
    		}
    	}
    }
}
