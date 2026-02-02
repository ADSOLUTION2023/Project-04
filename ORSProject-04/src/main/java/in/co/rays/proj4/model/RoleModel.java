package in.co.rays.proj4.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * RoleModel provides CRUD operations and search/list utilities for RoleBean.
 *
 * <p>
 * This class uses JDBC to interact with the {@code st_role} table and throws
 * application-specific exceptions defined in the project.
 * </p>
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
public class RoleModel {

	/**
	 * Returns next primary key value for st_role table.
	 *
	 * @return next primary key (Integer)
	 * @throws DatabaseException if a database access error occurs
	 */
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_role");
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

	/**
	 * Adds a new role record into database.
	 *
	 * @param bean RoleBean containing role data to add
	 * @return primary key of newly inserted role
	 * @throws DatabaseException         if a database access error occurs while getting pk
	 * @throws ApplicationException      if any SQL exception occurs while adding role
	 * @throws DuplicateRecordException  if a role with same name already exists
	 */
	public long add(RoleBean bean) throws DatabaseException, ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;
		
		RoleBean existBRole = findByName(bean.getName());
		if (existBRole != null) {
			throw new DuplicateRecordException("Role already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_role values (?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				throw new ApplicationException("Exception: add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception: Exception in add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Deletes a role record from database.
	 *
	 * @param bean RoleBean containing id of role to delete
	 * @throws ApplicationException if a SQL error occurs during delete
	 */
	public void delete(RoleBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_role where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Updates an existing role record.
	 *
	 * @param bean RoleBean containing updated values (must include id)
	 * @throws ApplicationException     if a SQL error occurs while updating
	 * @throws DuplicateRecordException if another role with same name exists
	 */
	public void update(RoleBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		RoleBean existBRole = findByName(bean.getName());

		if (existBRole != null && existBRole.getId() != bean.getId()) {
			throw new DuplicateRecordException("Role already exists");
		}

		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_role set name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreatedDatetime());
			pstmt.setTimestamp(6, bean.getModifiedDatetime());
			pstmt.setLong(7, bean.getId());
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
			throw new ApplicationException("Exception in updating Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Finds a role by primary key.
	 *
	 * @param pk primary key of role
	 * @return RoleBean if found, otherwise null
	 * @throws ApplicationException if a SQL error occurs while fetching data
	 */
	public RoleBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		RoleBean bean = null;
		StringBuffer sql = new StringBuffer("select * from st_role where id = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	/**
	 * Finds a role by name.
	 *
	 * @param name role name to find
	 * @return RoleBean if found, otherwise null
	 * @throws ApplicationException if a SQL error occurs while fetching data
	 */
	public RoleBean findByName(String name) throws ApplicationException {
		Connection conn = null;
		RoleBean bean = null;
		StringBuffer sql = new StringBuffer("select * from st_role where name = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting User by name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	/**
	 * Returns all roles.
	 *
	 * @return List of RoleBean
	 * @throws ApplicationException if a SQL error occurs during retrieval
	 */
	public List<RoleBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}
	
	/**
	 * Searches roles based on provided filter bean and supports pagination.
	 *
	 * @param bean     RoleBean filter (null means no filter)
	 * @param pageNo   page number (1-based). If pageSize &gt; 0, pageNo is used to compute offset.
	 * @param pageSize number of records per page. If 0, returns all matching rows.
	 * @return List of RoleBean matching criteria
	 * @throws ApplicationException if a SQL error occurs during search
	 */
	public List<RoleBean> search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_role where 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '%" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '%" + bean.getDescription() + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}
		Connection conn = null;
		ArrayList<RoleBean> list = new ArrayList<RoleBean>();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Role");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return list;
	}
}
