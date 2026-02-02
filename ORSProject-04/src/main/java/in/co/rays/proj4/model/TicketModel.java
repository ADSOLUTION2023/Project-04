package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.co.rays.proj4.bean.TicketBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TicketModel {
	public long nextPk() throws DatabaseException {
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from ticket_booking");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public long add(TicketBean bean) throws DatabaseException, ApplicationException {
		long pk = nextPk();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into ticket_booking values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getUserId());
			pstmt.setDate(3, new java.sql.Date(bean.getBookingDate().getTime()));
			pstmt.setString(4, bean.getSeatNo());
			pstmt.setInt(5, bean.getQuentity());
			pstmt.setInt(6, bean.getAmount());
			pstmt.setString(7, bean.getPaymentMode());
			pstmt.setString(8, bean.getBookingStatus());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new ApplicationException("Exception : add rollback exception " + e.getMessage());
			} catch (SQLException e1) {
				throw new ApplicationException("Exception : Exception in add User");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		}
		return pk;
	}

	public void update(TicketBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ticket_booking SET user_id=?, booking_date=?, seat_no=?, quentity=?, amount=?, payment_mode=?, booking_status=?, modified_by=?, modified_datetime=? WHERE id=?");
			pstmt.setString(1, bean.getUserId());
			pstmt.setDate(2, new java.sql.Date(bean.getBookingDate().getTime()));
			pstmt.setString(3, bean.getSeatNo());
			pstmt.setInt(4, bean.getQuentity());
			pstmt.setInt(5, bean.getAmount());
			pstmt.setString(6, bean.getPaymentMode());
			pstmt.setString(7, bean.getBookingStatus());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.setLong(12, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new ApplicationException("Exception : add rollback exception " + e.getMessage());
			} catch (SQLException e1) {
				throw new ApplicationException("Exception : Exception in update User");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		}
	}

	public void delete(TicketBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ticket_booking WHERE id=?");
			pstmt.setString(1, bean.getUserId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new ApplicationException("Exception : add rollback exception " + e.getMessage());
			} catch (SQLException e1) {
				throw new ApplicationException("Exception : Exception in delete User");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		}
	}
	
	public TicketBean findByUserId(String userId) {
		TicketBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from ticket_booking where user_id = ?");
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setUserId(rs.getString(2));
				bean.setBookingDate(rs.getDate(3));
				bean.setSeatNo(rs.getString(4));
				bean.setQuentity(rs.getInt(5));
				bean.setAmount(rs.getInt(6));
				bean.setPaymentMode(rs.getString(7));
				bean.setBookingStatus(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public TicketBean findByPk(long id) {
		TicketBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from ticket_booking where id = ?");
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setUserId(rs.getString(2));
				bean.setBookingDate(rs.getDate(3));
				bean.setSeatNo(rs.getString(4));
				bean.setQuentity(rs.getInt(5));
				bean.setAmount(rs.getInt(6));
				bean.setPaymentMode(rs.getString(7));
				bean.setBookingStatus(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
//	public List<TicketBean> search() {
//		
//	}
}
