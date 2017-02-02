package cfs.databean;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.genericdao.PrimaryKey;

@PrimaryKey("customerId,fundId")
public class Position {
    private int customerId;
    private int fundId;
    private double shares;

    public static BigDecimal sharesFromStr(String shares) {
        // Use UNNECESSARY here to assert that the input string must not have more than 3 decimals.
        return new BigDecimal(shares).setScale(3, RoundingMode.UNNECESSARY);
    }

    public static BigDecimal sharesFromDouble(double shares) {
        // Round the double value half because floating point error can be either positive or negative.
        // For example, 2.33300000000007 and 2.33299999999999 should both be considered 2.333.
        return BigDecimal.valueOf(shares).setScale(3, RoundingMode.HALF_EVEN);
    }

    public Position() {
    }

    public Position(int customerId, int fundId, double shares) {
        this.customerId = customerId;
        this.fundId = fundId;
        this.shares = shares;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getFundId() {
        return fundId;
    }
    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public double getShares() {
        return shares;
    }
    public void setShares(double shares) {
        this.shares = shares;
    }
}
