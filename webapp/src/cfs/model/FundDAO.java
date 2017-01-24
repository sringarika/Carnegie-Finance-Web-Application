package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import cfs.databean.Fund;

public class FundDAO extends GenericDAO<Fund> {
    public FundDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Fund.class, tableName, cp);
    }
    
    /**
     * Fund name
     * @param fundName input param
     * @return fund name
     * @throws RollbackException
     */
    public Fund[] fundName(String fundName) throws RollbackException {
    	Fund[] fundn = match(MatchArg.equals("name", fundName));
    	if (fundn == null) {
    		return null;
    	}
    	return fundn;
    }
    
    /**
     * Fund price
     * @param fundSymbol input param
     * @return fund price
     * @throws RollbackException
     */
    public Fund[] fundSym(String fundSymbol) throws RollbackException {
    	Fund[] funds = match(MatchArg.equals("symbol", fundSymbol));
    	if (funds == null) {
    		return null;
    	}
    	return funds;
    }
}
