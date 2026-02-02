/**
 * TimetableModel handles all database operations related to the Timetable entity.
 * It provides CRUD operations, search functionality, and validation checks to 
 * prevent duplicate timetable entries. This model interacts with the st_timetable 
 * table in the database.
 * 
 * @author Chaitanya Bhatt
 * @version 1.0
 */

package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TimetableModel {

    /**
     * Returns the next primary key for st_timetable.
     *
     * @return next PK as Integer
     * @throws DatabaseException if database access fails
     */
    public Integer nextPk() throws DatabaseException {
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_timetable");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new DatabaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new timetable entry to the database.
     *
     * @param bean TimetableBean containing details to insert
     * @return generated primary key
     * @throws ApplicationException if unable to add record
     * @throws DuplicateRecordException if duplicate timetable exists
     */
    public long add(TimetableBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        int pk = 0;

        CourseModel courseModel = new CourseModel();
        CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
        bean.setCourseName(courseBean.getName());

        SubjectModel subjectModel = new SubjectModel();
        SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
        bean.setSubjectName(subjectBean.getName());

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPk();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_timetable values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getSemester());
            pstmt.setString(3, bean.getDescription());
            pstmt.setDate(4, new java.sql.Date(bean.getExamDate().getTime()));
            pstmt.setString(5, bean.getExamTime());
            pstmt.setLong(6, bean.getCourseId());
            pstmt.setString(7, bean.getCourseName());
            pstmt.setLong(8, bean.getSubjectId());
            pstmt.setString(9, bean.getSubjectName());
            pstmt.setString(10, bean.getCreatedBy());
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
                throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in add Timetable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing timetable entry.
     *
     * @param bean TimetableBean containing updated details
     * @throws ApplicationException if failed to update
     * @throws DuplicateRecordException if conflicting record exists
     */
    public void update(TimetableBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        CourseModel courseModel = new CourseModel();
        CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
        bean.setCourseName(courseBean.getName());

        SubjectModel subjectModel = new SubjectModel();
        SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
        bean.setSubjectName(subjectBean.getName());

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_timetable set semester = ?, description = ?, exam_date = ?, exam_time = ?, course_id = ?, course_name = ?, subject_id = ?, subject_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

            pstmt.setString(1, bean.getSemester());
            pstmt.setString(2, bean.getDescription());
            pstmt.setDate(3, new java.sql.Date(bean.getExamDate().getTime()));
            pstmt.setString(4, bean.getExamTime());
            pstmt.setLong(5, bean.getCourseId());
            pstmt.setString(6, bean.getCourseName());
            pstmt.setLong(7, bean.getSubjectId());
            pstmt.setString(8, bean.getSubjectName());
            pstmt.setString(9, bean.getCreatedBy());
            pstmt.setString(10, bean.getModifiedBy());
            pstmt.setTimestamp(11, bean.getCreatedDatetime());
            pstmt.setTimestamp(12, bean.getModifiedDatetime());
            pstmt.setLong(13, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Timetable ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes a timetable entry.
     *
     * @param bean TimetableBean with ID to delete
     * @throws ApplicationException if delete fails
     */
    public void delete(TimetableBean bean) throws ApplicationException {
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
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
            throw new ApplicationException("Exception : Exception in delete Timetable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds timetable by its primary key.
     *
     * @param pk primary key
     * @return TimetableBean
     * @throws ApplicationException if retrieval fails
     */
    public TimetableBean findByPk(long pk) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_timetable where id = ?");
        TimetableBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimetableBean();
                bean.setId(rs.getLong(1));
                bean.setSemester(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setExamDate(rs.getDate(4));
                bean.setExamTime(rs.getString(5));
                bean.setCourseId(rs.getLong(6));
                bean.setCourseName(rs.getString(7));
                bean.setSubjectId(rs.getLong(8));
                bean.setSubjectName(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in getting Timetable by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Checks if timetable exists for given course on a specific exam date.
     */
    public TimetableBean checkByCourseName(Long courseId, Date examDate) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_timetable where course_id = ? and exam_date = ?");
        TimetableBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setDate(2, new java.sql.Date(examDate.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimetableBean();
                bean.setId(rs.getLong(1));
                bean.setSemester(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setExamDate(rs.getDate(4));
                bean.setExamTime(rs.getString(5));
                bean.setCourseId(rs.getLong(6));
                bean.setCourseName(rs.getString(7));
                bean.setSubjectId(rs.getLong(8));
                bean.setSubjectName(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in get Timetable");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Checks if timetable exists for given subject, course, and exam date.
     */
    public TimetableBean checkBySubjectName(Long courseId, Long subjectId, Date examDate)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer(
                "select * from st_timetable where course_id = ? and subject_id = ? and exam_date = ?");
        TimetableBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, subjectId);
            pstmt.setDate(3, new java.sql.Date(examDate.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimetableBean();
                bean.setId(rs.getLong(1));
                bean.setSemester(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setExamDate(rs.getDate(4));
                bean.setExamTime(rs.getString(5));
                bean.setCourseId(rs.getLong(6));
                bean.setCourseName(rs.getString(7));
                bean.setSubjectId(rs.getLong(8));
                bean.setSubjectName(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in get Timetable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Checks timetable by semester, course, subject, and exam date.
     */
    public TimetableBean checkBySemester(Long courseId, Long subjectId, String semester, Date examDate)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer(
                "select * from st_timetable where course_id = ? and subject_id = ? and semester = ? and exam_date = ?");
        TimetableBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, subjectId);
            pstmt.setString(3, semester);
            pstmt.setDate(4, new java.sql.Date(examDate.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimetableBean();
                bean.setId(rs.getLong(1));
                bean.setSemester(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setExamDate(rs.getDate(4));
                bean.setExamTime(rs.getString(5));
                bean.setCourseId(rs.getLong(6));
                bean.setCourseName(rs.getString(7));
                bean.setSubjectId(rs.getLong(8));
               	bean.setSubjectName(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in get Timetable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Checks timetable by exam time and description.
     */
    public TimetableBean checkByExamTime(Long courseId, Long subjectId, String semester, Date examDate,
                                         String examTime, String description) throws ApplicationException {

        StringBuffer sql = new StringBuffer(
                "select * from st_timetable where course_id = ? and subject_id = ? and semester = ? and exam_date = ? and exam_time = ? and description = ?");
        TimetableBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, subjectId);
            pstmt.setString(3, semester);
            pstmt.setDate(4, new java.sql.Date(examDate.getTime()));
            pstmt.setString(5, examTime);
            pstmt.setString(6, description);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimetableBean();
                bean.setId(rs.getLong(1));
                bean.setSemester(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setExamDate(rs.getDate(4));
                bean.setExamTime(rs.getString(5));
               	bean.setCourseId(rs.getLong(6));
                bean.setCourseName(rs.getString(7));
                bean.setSubjectId(rs.getLong(8));
                bean.setSubjectName(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
           	throw new ApplicationException("Exception : Exception in get Timetable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns a list of all timetable entries or paginated list.
     *
     * @return List of TimetableBean
     * @throws ApplicationException if db access fails
     */
    public List<TimetableBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches timetable records based on given parameters.
     *
     * @param bean search criteria bean
     * @param pageNo page number
     * @param pageSize number of records per page
     * @return list of matching timetables
     * @throws ApplicationException if search fails
     */
    public List<TimetableBean> search(TimetableBean bean, int pageNo, int pageSize) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }
            if (bean.getCourseId() > 0) {
                sql.append(" and course_id = " + bean.getCourseId());
            }
            if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
                sql.append(" and course_name like '" + bean.getCourseName() + "%'");
            }
            if (bean.getSubjectId() > 0) {
                sql.append(" and subject_id = " + bean.getSubjectId());
            }
            if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
                sql.append(" and subject_name like '" + bean.getSubjectName() + "%'");
            }
            if (bean.getSemester() != null && bean.getSemester().length() > 0) {
                sql.append(" and semester like '" + bean.getSemester() + "%'");
            }
            if (bean.getDescription() != null && bean.getDescription().length() > 0) {
                sql.append(" and description like '" + bean.getDescription() + "%'");
            }
            if (bean.getExamDate() != null && bean.getExamDate().getDate() > 0) {
                sql.append(" and exam_date like '" + new java.sql.Date(bean.getExamDate().getTime()) + "%'");
            }
            if (bean.getExamTime() != null && bean.getExamTime().length() > 0) {
                sql.append(" and exam_time like '" + bean.getExamTime() + "%'");
            }
        }

        if (pageSize > 0) {
           	pageNo = (pageNo - 1) * pageSize;
           	sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<TimetableBean> list = new ArrayList<TimetableBean>();
        Connection conn = null;
        try {
           	conn = JDBCDataSource.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement(sql.toString());
           	ResultSet rs = pstmt.executeQuery();
           	while (rs.next()) {
               	bean = new TimetableBean();
               	bean.setId(rs.getLong(1));
               	bean.setSemester(rs.getString(2));
               	bean.setDescription(rs.getString(3));
               	bean.setExamDate(rs.getDate(4));
               	bean.setExamTime(rs.getString(5));
               	bean.setCourseId(rs.getLong(6));
               	bean.setCourseName(rs.getString(7));
               	bean.setSubjectId(rs.getLong(8));
               	bean.setSubjectName(rs.getString(9));
               	bean.setCreatedBy(rs.getString(10));
               	bean.setModifiedBy(rs.getString(11));
               	bean.setCreatedDatetime(rs.getTimestamp(12));
               	bean.setModifiedDatetime(rs.getTimestamp(13));
               	list.add(bean);
           	}
           	rs.close();
           	pstmt.close();
        } catch (Exception e) {
           	throw new ApplicationException("Exception : Exception in search Timetable");
        } finally {
           	JDBCDataSource.closeConnection(conn);
        }
       	return list;
    }
}
