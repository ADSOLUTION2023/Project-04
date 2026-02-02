package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.MarkSheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * MarkSheetModel provides CRUD and search operations for {@link MarkSheetBean}
 * against the database table {@code st_MarkSheet}.
 *
 * It uses {@link JDBCDataSource} to obtain and close connections and throws
 * application-specific checked exceptions to signal error conditions.
 *
 * Responsibilities: - generate next primary key - add / update / delete
 * MarkSheet records - find MarkSheet by PK or roll number - search and list
 * MarkSheets with optional pagination - get merit list ordered by total marks
 *
 * Note: SQL uses simple string-building for filters (consistent with the rest
 * of the project). Care should be taken if inputs can contain special
 * characters � ideally use parameterized queries for filters.
 *
 * @author Amit Chandsarkar
 * @version 1.0
 */
public class MarkSheetModel {

	private static Logger log = Logger.getLogger(MarkSheetModel.class);

	/**
	 * Returns the next primary key value for the st_MarkSheet table.
	 *
	 * @return next primary key value
	 * @throws DatabaseException if a database error occurs while retrieving the
	 *                           maximum id
	 */
	public Integer nextPk() throws DataBaseException {

		log.debug("MarkSheetModel nextPk started");
		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_MarkSheet");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();

			log.debug("Next PK generated : " + (pk + 1));

		} catch (Exception e) {
			throw new DataBaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	/**
	 * Adds a new MarkSheet record to the database. Resolves student name from
	 * StudentModel and checks duplicate roll number.
	 *
	 * @param bean MarkSheet data
	 * @return generated primary key
	 * @throws ApplicationException     for general DB errors
	 * @throws DuplicateRecordException if roll number already exists
	 */
	public long add(MarkSheetBean bean) throws ApplicationException, DuplicateRecordException {

		log.debug("MarkSheetModel add started");

		Connection conn = null;

		int pk = 0;

		StudentModel studentModel = new StudentModel();
		StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		MarkSheetBean duplicateMarkSheet = findByRollNo(bean.getRollNo());

		if (duplicateMarkSheet != null) {
			log.warn("Duplicate Roll No : " + bean.getRollNo());
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

			log.info("MarkSheet added successfully, PK = " + pk);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				log.error("Add rollback failed", ex);
				throw new ApplicationException("add rollback exception " + ex.getMessage());
			}
			log.error("Add rollback failed", e);
			throw new ApplicationException("Exception in add MarkSheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Updates an existing MarkSheet record. Ensures roll number uniqueness (except
	 * for current record) and resolves student name.
	 *
	 * @param bean MarkSheet with updated data
	 * @throws ApplicationException     for general DB errors
	 * @throws DuplicateRecordException if another record with same roll no exists
	 */
	public void update(MarkSheetBean bean) throws ApplicationException, DuplicateRecordException {

		log.debug("MarkSheetModel update started, ID = " + bean.getId());

		Connection conn = null;

		MarkSheetBean beanExist = findByRollNo(bean.getRollNo());

		if (beanExist != null && beanExist.getId() != bean.getId()) {
			log.warn("Duplicate Roll No on update : " + bean.getRollNo());
			throw new DuplicateRecordException("Roll No is already exist");
		}

		StudentModel studentModel = new StudentModel();
		StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_MarkSheet set roll_no = ?, student_id = ?, name = ?, physics = ?, chemistry = ?, maths = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getRollNo());
			pstmt.setLong(2, bean.getStudentId());
			pstmt.setString(3, bean.getName());
			pstmt.setInt(4, bean.getPhysics());
			pstmt.setInt(5, bean.getChemistry());
			pstmt.setInt(6, bean.getMaths());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.setLong(11, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

			log.info("MarkSheet updated successfully, ID = " + bean.getId());

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				log.error("Update rollback failed", ex);
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			log.error("Exception in updating MarkSheet", e);
			throw new ApplicationException("Exception in updating MarkSheet ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Deletes a MarkSheet record.
	 *
	 * @param bean MarkSheet bean with id to delete
	 * @throws ApplicationException for general DB errors
	 */
	public void delete(MarkSheetBean bean) throws ApplicationException {

		log.debug("MarkSheetModel delete started, ID = " + bean.getId());

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("delete from st_MarkSheet where id = ?");
			pstmt.setLong(1, bean.getId());
			System.out.println("Deleted MarkSheet");
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

			log.info("MarkSheet deleted successfully, ID = " + bean.getId());

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				log.error("Delete rollback failed", ex);
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			log.error("Exception in delete MarkSheet", e);
			throw new ApplicationException("Exception in delete MarkSheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Finds a MarkSheet by primary key.
	 *
	 * @param pk primary key id
	 * @return found MarkSheetBean or null if not found
	 * @throws ApplicationException for general DB errors
	 */
	public MarkSheetBean findByPk(long pk) throws ApplicationException {

		log.debug("MarkSheetModel findByPk started, PK = " + pk);

		StringBuffer sql = new StringBuffer("select * from st_MarkSheet where id = ?");
		MarkSheetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
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
			log.error("Exception in findByPk", e);
			throw new ApplicationException("Exception in getting MarkSheet by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Finds a MarkSheet by roll number.
	 *
	 * @param rollNo roll number to search
	 * @return found MarkSheetBean or null if not found
	 * @throws ApplicationException for general DB errors
	 */
	public MarkSheetBean findByRollNo(String rollNo) throws ApplicationException {

		log.debug("MarkSheetModel findByRollNo started, RollNo = " + rollNo);

		StringBuffer sql = new StringBuffer("select * from st_MarkSheet where roll_no = ?");
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

			log.error("Exception in findByRollNo", e);

			throw new ApplicationException("Exception in getting MarkSheet by roll no");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Searches MarkSheets by criteria in bean. Supports pagination when pageSize
	 * &gt; 0.
	 *
	 * @param bean     filter criteria (nullable)
	 * @param pageNo   page number (1-based) used only if pageSize &gt; 0
	 * @param pageSize number of records per page; 0 disables pagination
	 * @return list of matching MarkSheetBean
	 * @throws ApplicationException for general DB errors
	 */
	public List<MarkSheetBean> search(MarkSheetBean bean, int pageNo, int pageSize) throws ApplicationException {

		log.debug("MarkSheetModel search started");

		StringBuffer sql = new StringBuffer("select * from st_MarkSheet where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getRollNo() != null && bean.getRollNo().length() > 0) {
				sql.append(" and roll_no like '" + bean.getRollNo() + "%'");
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getPhysics() != 0 && bean.getPhysics() > 0) {
				sql.append(" and physics = " + bean.getPhysics());
			}
			if (bean.getChemistry() != 0 && bean.getChemistry() > 0) {
				sql.append(" and chemistry = " + bean.getChemistry());
			}
			if (bean.getMaths() != 0 && bean.getMaths() > 0) {
				sql.append(" and maths = '" + bean.getMaths());
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<MarkSheetBean> list = new ArrayList<MarkSheetBean>();
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
			log.error("Exception in search MarkSheet", e);
			throw new ApplicationException("Update rollback exception " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	/**
	 * Returns merit list: students who passed all subjects (marks &gt; 33) ordered
	 * by total marks desc. Supports pagination.
	 *
	 * @param pageNo   page number (1-based) if using pagination
	 * @param pageSize page size; pass 0 to disable pagination
	 * @return list of top MarkSheet records by total
	 * @throws ApplicationException for general DB errors
	 */
	public List<MarkSheetBean> getMeritList(int pageNo, int pageSize) throws ApplicationException {

		log.debug("MarkSheetModel getMeritList started");

		ArrayList<MarkSheetBean> list = new ArrayList<MarkSheetBean>();
		StringBuffer sql = new StringBuffer(
				"select id, roll_no, name, physics, chemistry, maths, (physics + chemistry + maths) as total from st_MarkSheet where physics > 33 and chemistry > 33 and maths > 33 order by total desc");

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
			log.error("Exception in getting merit list of MarkSheet", e);
			throw new ApplicationException("Exception in getting merit list of MarkSheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}