package in.co.rays.proj4.bean;

public class TransactionBean extends BaseBean{
	

    private long accountId;
    private String type; // Deposit / Withdraw
    private double amount;
    
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public String getKey() {
		return id + "";
	}
	@Override
	public String getValue() {
		return accountId + " " + type;
	}
    
    

}
