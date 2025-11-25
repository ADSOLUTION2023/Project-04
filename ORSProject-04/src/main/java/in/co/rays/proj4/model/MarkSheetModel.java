package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.MarkSheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class MarkSheetModel {

	public Integer nextPk() throws DataBaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_MarkSheet");
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

	public long add(MarkSheetBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		int pk = 0;

		StudentModel studentModel = new StudentModel();
		StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		MarkSheetBean duplicateMarkSheet = findByRollNo(bean.getRollNo());

		if (duplicateMarkSheet != null) {
			throw new DuplicateRecordException("Roll Number already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_MarkSheet values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getRollNo());
			pstmt.setLong(3, bean.getStudentId());
			pstmt.setString(4, bean.getName());
			pstmt.setInt(5, bean.getPhysics());
			pstmt.setInt(6, bean.getChemistry());
			pstmt.setInt(7, bean.getMaths());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add MarkSheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void update(MarkSheetBean bean) throws DuplicateRecordException, ApplicationException {

		Connection conn = null;
		int pk = 0;

		StudentModel studentModel = new StudentModel();
		StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		MarkSheetBean beanExist = findByRollNo(bean.getRollNo());

		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("RollNo already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_marksheet values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getRollNo());
			pstmt.setLong(3, bean.getStudentId());
			pstmt.setString(4, bean.getName());
			pstmt.setInt(5, bean.getPhysics());
			pstmt.setInt(6, bean.getChemistry());
			pstmt.setInt(7, bean.getMaths());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
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

	public void delete(MarkSheetBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_markSheet where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public MarkSheetBean findByPk(long pk) throws ApplicationException {

		MarkSheetBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_markSheet where id = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarkSheetBean();
				bean.setId(1);
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(3);
				bean.setName(rs.getString(4));
				bean.setPhysics(5);
				bean.setChemistry(6);
				bean.setMaths(7);
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting MarkSheet by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public MarkSheetBean findByRollNo(String rollNo) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_markSheet where roll_no = ?");
		MarkSheetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, rollNo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarkSheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in getting MarkSheet by roll no");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<MarkSheetBean> search(MarkSheetBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuilder sql = new StringBuilder("SELECT * FROM st_marksheet WHERE 1=1");

		List<Object> params = new ArrayList<>();

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		List<MarkSheetBean> list = new ArrayList<MarkSheetBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarkSheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Update rollback exception " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	public List<MarkSheetBean> getMeritList(int pageNo, int pageSize) throws ApplicationException {

		List<MarkSheetBean> list = new ArrayList<MarkSheetBean>();
		StringBuffer sql = new StringBuffer(
				"select id, roll_no, name, physics, chemistry, maths, (physics + chemistry + maths) as total from st_markSheet where physics > 33 and chemistry > 33 and maths > 33 order by total desc");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MarkSheetBean bean = new MarkSheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in getting merit list of MarkSheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
