package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;

import cfs.databean.Position;

public class CustomerPositionDAO extends GenericDAO<Position> {
    public CustomerPositionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Position.class, tableName, cp);
    }
}
