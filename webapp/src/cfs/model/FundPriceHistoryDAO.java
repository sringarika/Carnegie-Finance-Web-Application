package cfs.model;

import java.util.Map;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.FundPrice;

public class FundPriceHistoryDAO extends GenericDAO <FundPrice> {
    public FundPriceHistoryDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(FundPrice.class, tableName, cp);
    }
    
    // return the latest price of each fund for the given last transition date
    public FundPrice[] researchFund(String lastTransitionDate) throws RollbackException {
    	FundPrice[] fundPrice = match(MatchArg.equals("executeDate", lastTransitionDate));
    	if (fundPrice == null) {
    		return null;
    	}
    	return fundPrice;
    }
    
    public void updatePrice (Map<Integer, Double> closingPrice, String transitionDate) throws RollbackException {
    	FundPrice[] fundPrices = match();
    	for (FundPrice fundPrice : fundPrices) {
    		if (closingPrice.get(fundPrice.getFundId()) == null) {
    			throw new RollbackException("Error!!!");
    		} else {
    			fundPrice.setExecuteDate(transitionDate);
    			fundPrice.setPrices(closingPrice.get(fundPrice.getFundId()));
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
