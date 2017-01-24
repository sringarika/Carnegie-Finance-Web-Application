package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import cfs.databean.Customer;
import cfs.databean.Position;

public class CustomerPositionDAO extends GenericDAO<Position> {
    public CustomerPositionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Position.class, tableName, cp);
    }
    
    public Position[] findPositions(int customerId) throws RollbackException {
        Position[] positions = match(MatchArg.equals("customerId", customerId));
        return positions;
    }
}
