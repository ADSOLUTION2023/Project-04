package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import in.co.rays.proj4.bean.AccountBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.EmailBuilder;
import in.co.rays.proj4.util.EmailMessage;
import in.co.rays.proj4.util.EmailUtility;
import in.co.rays.proj4.util.JDBCDataSource;

public class AccountModel {
	
	public Integer nextPk() throws DataBaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from account ");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DataBaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}
	
	public long add(AccountBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		AccountBean existbean = findByMobileNo(bean.getMobileNo());

		if (existbean != null) {
			throw new DuplicateRecordException("Mobile Number already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into account values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getAccountId());
			pstmt.setString(3, bean.getPassword());
			pstmt.setString(4, bean.getAccountNo());
			pstmt.setString(5,bean.getName());
			pstmt.setDouble(6, bean.getBalance());
			pstmt.setString(7, bean.getStatus());
			pstmt.setString(8, bean.getMobileNo());
			pstmt.setDate(9, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(10, bean.getGender());
			pstmt.setString(11,bean.getCreatedBy());
			pstmt.setString(12, bean.getModifiedBy());
			pstmt.setTimestamp(13, bean.getCreatedDatetime());
			pstmt.setTimestamp(14, bean.getModifiedDatetime());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add account");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}
	
	public void update(AccountBean bean) throws DuplicateRecordException, ApplicationException {

		Connection conn = null;

		AccountBean beanExist = findByAccNo(bean.getAccountNo());

		if (beanExist != null && (beanExist.getId() != bean.getId())) {
			throw new DuplicateRecordException("Account already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update account set login = ?, password = ?, accountNo = ?, name = ?, balance = ?, status = ?, mobile_no = ?,dob = ?, gender = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getAccountId());
			pstmt.setString(2, bean.getPassword());
			pstmt.setString(3, bean.getAccountNo());
			pstmt.setString(4,bean.getName());
			pstmt.setDouble(5, bean.getBalance());
			pstmt.setString(6, bean.getStatus());
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setDate(8, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(9, bean.getGender());
			pstmt.setString(10,bean.getCreatedBy());
			pstmt.setString(11, bean.getModifiedBy());
			pstmt.setTimestamp(12, bean.getCreatedDatetime());
			pstmt.setTimestamp(13, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public void delete(AccountBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from account where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete account");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	private AccountBean findByMobileNo(String mobileNo) throws ApplicationException {
		
		AccountBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from account where mobileNo = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(7, bean.getMobileNo());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new AccountBean();
				bean.setId(rs.getLong(1));
				bean.setAccountId(rs.getString(2));
				bean.setPassword(rs.getString(3));
				bean.setAccountNo(rs.getString(4));
				bean.setName(rs.getString(5));
				bean.setBalance(rs.getDouble(6));
				bean.setStatus(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setDob(rs.getDate(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting account by mobileNo");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	private AccountBean findByAccNo(String login) throws ApplicationException {
		
		AccountBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from account where accountNo = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			bean.setAccountNo(bean.getAccountNo());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new AccountBean();
				bean.setId(rs.getLong(1));
				bean.setAccountId(rs.getString(2));
				bean.setPassword(rs.getString(3));
				bean.setAccountNo(rs.getString(4));
				bean.setName(rs.getString(5));
				bean.setBalance(rs.getDouble(6));
				bean.setStatus(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setDob(rs.getDate(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting account by accountNo");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	public List<AccountBean> search(AccountBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<AccountBean> list = new ArrayList<AccountBean>();

		StringBuffer sql = new StringBuffer("select * from account where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getAccountId() != null && bean.getAccountId().length() > 0) {
				sql.append(" and login like '" + bean.getAccountId() + "%'");
			}
		    if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" and password like '" + bean.getPassword() + "%'");
			}	
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getAccountNo() != null && bean.getAccountNo().length() > 0) {
				sql.append(" and accountNo like '" + bean.getAccountNo() + "%'");
			}
			if (bean.getBalance() >= 0) {
				sql.append(" and balace like '" + bean.getBalance() + "%'");
			}
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				sql.append(" and dob like '" + new java.sql.Date(bean.getDob().getTime()) + "%'");
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" and mobile_no = " + bean.getMobileNo());
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" and gender like '" + bean.getGender() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}
		System.out.println("sql ===== > " + sql.toString());
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new AccountBean();
				bean.setId(rs.getLong(1));
				bean.setAccountId(rs.getString(2));
				bean.setPassword(rs.getString(3));
				bean.setAccountNo(rs.getString(4));
				bean.setName(rs.getString(5));
				bean.setBalance(rs.getDouble(6));
				bean.setStatus(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setDob(rs.getDate(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search account");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	public long registerUser(AccountBean bean) throws DuplicateRecordException, ApplicationException {

		long pk = add(bean);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getAccountId());
		map.put("password", bean.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getAccountId());
		msg.setSubject("Registration is successful for ORSProject-04");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}

}
