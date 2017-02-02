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
    public boolean fundName(String fundName) throws RollbackException {
        Fund[] fundn = match(MatchArg.equals("name", fundName));
        if(fundn.length > 0) return true;
        return false;
    }

    /**
     * Fund price
     * @param fundTicker input param
     * @return fund price
     * @throws RollbackException
     */
    public boolean fundTicker(String fundTicker) throws RollbackException {
        Fund[] funds = match(MatchArg.equals("ticker", fundTicker));
        if(funds.length > 0) return true;
        return false;
    }

    public Integer findIdByName(String fundName) throws RollbackException {
        Fund[] fund = match(MatchArg.equals("name", fundName));
        if (fund.length == 0) {
            return null;
        }
           return fund[0].getFundId();
    }
}
