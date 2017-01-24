package cfs.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("customerId,fundId")
public class Position {
	private int customerId;
	private int fundId;
	private double shares;
	
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
