package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import cfs.databean.Position;

public class CustomerPositionDAO extends GenericDAO<Position> {
    public CustomerPositionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Position.class, tableName, cp);
    }
    
    public Position[] findPositionsBoth(int customerId, int fundId) throws RollbackException {
        Position[] positions = match(MatchArg.and(MatchArg.equals("customerId", customerId),MatchArg.equals("fundId", fundId)));
        return positions;
    }
    
    public Position[] findPositions(int customerId) throws RollbackException {
        Position[] positions = match(MatchArg.equals("customerId", customerId));
        return positions;
    }
    
    public void updateShare(int customerId, int fundId) throws RollbackException{
    	//get number of existing shares first from position DAO-ok
        Position[] positions = match(MatchArg.and(MatchArg.equals("customerId", customerId),MatchArg.equals("fundId", fundId)));	
        //get available shares
        /*
        double shares = calltransactionDAOmethod(customerId, fundId);
        double ans = shares - positions[0];
        */
    }
}