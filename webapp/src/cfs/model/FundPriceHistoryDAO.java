package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

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
    
    // return all prices for a given fund id for displaying fund trend
    public FundPrice[] fundTrend(int fundId) throws RollbackException {
    	FundPrice[] fundPrice = match(MatchArg.equals("fundId", fundId));
    	if (fundPrice == null) {
    		return null;
    	}
    	return fundPrice;
    }
}
