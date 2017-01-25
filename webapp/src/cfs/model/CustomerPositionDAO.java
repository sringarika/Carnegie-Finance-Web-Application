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
    
    public Position[] findPositionscid(int customerId) throws RollbackException {
        Position[] positions = match(MatchArg.equals("customerId", customerId));
        return positions;
    }
    
    public Position[] findPositionsfid(int fundId) throws RollbackException {
        Position[] positions = match(MatchArg.equals("fundId", fundId));
        return positions;
    }
    
    public void updatepos(Position p) throws RollbackException{
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
