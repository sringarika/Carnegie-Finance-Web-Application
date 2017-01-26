package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.GenericViewDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.Position;
import cfs.viewbean.PositionView;

public class CustomerPositionDAO extends GenericDAO<Position> {
    GenericViewDAO<PositionView> viewDAO;

    public CustomerPositionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Position.class, tableName, cp);
        viewDAO = new GenericViewDAO<PositionView>(PositionView.class, cp);
    }

    public Position[] findPositionsBoth(int customerId, int fundId) throws RollbackException {
        Position[] positions = match(MatchArg.and(MatchArg.equals("customerId", customerId),MatchArg.equals("fundId", fundId)));
        return positions;
    }

    public Position[] findPositions(int customerId) throws RollbackException {
        Position[] positions = match(MatchArg.equals("customerId", customerId));
        return positions;
    }
    
    public double existingShare(int customerId, int fundId) throws RollbackException{
        Position[] positions = match(MatchArg.and(MatchArg.equals("customerId", customerId),MatchArg.equals("fundId", fundId)));	
        double exshare =  0.00;
        if (positions == null) {
            return exshare;
        } else {
            for (Position pos : positions) {
            	exshare += pos.getShares();
            }
            return exshare;
        }        
    }


    public void updatePosition(Position p) throws RollbackException{
		try{
    		Transaction.begin();
    		super.update(p);
    		Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}

	}

    public PositionView[] getPositionViews(int customerId) throws RollbackException {
        String sql = "SELECT position.fundId             AS fundId" +
                     "     , fund.name                   AS fundName" +
                     "     , fund.ticker                 AS ticker" +
                     "     , position.shares             AS shares" +
                     "     , IFNULL(fundprice.price, -1) AS price" +
                     "  FROM fund, position LEFT JOIN fundprice" +
                     "    ON position.fundId = fundprice.fundId" +
                     "   AND fundprice.executeDate = (SELECT MAX(executeDate) FROM fundprice)" +
                     " WHERE position.fundId = fund.fundId AND position.customerId = ?";
        return viewDAO.executeQuery(sql, customerId);
    }

}
