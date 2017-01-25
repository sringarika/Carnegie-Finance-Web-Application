package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.Customer;
import cfs.databean.Position;

public class CustomerPositionDAO extends GenericDAO<Position> {
    public CustomerPositionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Position.class, tableName, cp);
    }
    
    public Position[] findPositionsBoth(int customerId, int fundId) throws RollbackException {
        Position[] positions = match(MatchArg.and(MatchArg.equals("customerId", customerId),MatchArg.equals("fudnId", fundId)));
        return positions;
    }
    
    public Position[] findPositions(int customerId) throws RollbackException {
        Position[] positions = match(MatchArg.equals("customerId", customerId));
        return positions;
    }
    
    public void updatePos(Position p) throws RollbackException{
		try{
		Transaction.begin();
		super.update(p);
		Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		
	}
}
