package cfs.model;

import java.math.BigDecimal;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.GenericViewDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import cfs.databean.Position;
import cfs.viewbean.PositionView;

public class CustomerPositionDAO extends GenericDAO<Position> {
    GenericViewDAO<PositionView> viewDAO;

    public CustomerPositionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Position.class, tableName, cp);
        viewDAO = new GenericViewDAO<PositionView>(PositionView.class, cp);
    }

    public Position[] findPositionsBoth(int customerId, int fundId) throws RollbackException {
        Position[] positions = match(MatchArg.and(MatchArg.equals("customerId", customerId), MatchArg.equals("fundId", fundId)));
        return positions;
    }

    public Position[] findPositions(int customerId) throws RollbackException {
        Position[] positions = match(MatchArg.equals("customerId", customerId));
        return positions;
    }

    public BigDecimal existingShare(int customerId, int fundId) throws RollbackException{
        Position[] positions = match(MatchArg.and(MatchArg.equals("customerId", customerId),MatchArg.equals("fundId", fundId)));
        BigDecimal existingShare = BigDecimal.ZERO;
        if (positions == null) {
            return existingShare;
        } else {
            for (Position pos : positions) {
                existingShare = existingShare.add(Position.sharesFromDouble(pos.getShares()));
            }
            return existingShare;
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
