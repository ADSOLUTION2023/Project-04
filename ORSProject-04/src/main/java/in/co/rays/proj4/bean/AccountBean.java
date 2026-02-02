package in.co.rays.proj4.bean;

import java.util.Date;

public class AccountBean extends BaseBean{
	
	    private String accountNo;
	    private String name;
	    private String accountId;
		private String password;
	    private double balance;
	    private String status;
	    private String mobileNo;
	    private Date dob;
		private String gender;
	   
		public String getAccountId() {
			return accountId;
		}
		public void setAccountId(String login) {
			this.accountId = accountId;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public Date getDob() {
			return dob;
		}
		public void setDob(Date dob) {
			this.dob = dob;
		}
		public String getMobileNo() {
			return mobileNo;
		}
		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getAccountNo() {
			return accountNo;
		}
		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getBalance() {
			return balance;
		}
		public void setBalance(double balance) {
			this.balance = balance;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		@Override
		public String getKey() {
			return id + "";
		}
		@Override
		public String getValue() {
			return name + " " + accountNo;
		}
	    
	    
	}



