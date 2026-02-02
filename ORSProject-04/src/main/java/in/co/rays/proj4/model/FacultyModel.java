package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * FacultyModel manages all CRUD operations for the st_faculty table.
 * It handles faculty creation, updation, deletion, searching and lookup
 * by primary key or email. It also sets related college, course and subject names.
 *
 * author  Amit Chandsarkar
 * version 1.0
 */
public class FacultyModel {

    private static Logger log = Logger.getLogger(FacultyModel.class);

    /**
     * Generates next primary key.
     *
     * @return next primary key
     * @throws DatabaseException when database error occurs
     */
    public Integer nextPk() throws DataBaseException {

        log.debug("FacultyModel nextPk started");

        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_faculty");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) pk = rs.getInt(1);
            rs.close();
            pstmt.close();

            log.debug("Next PK generated : " + (pk + 1));

        } catch (Exception e) {
            log.error("Exception in getting PK", e);
            throw new DataBaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new faculty record.
     *
     * @param bean FacultyBean
     * @return generated primary key
     * @throws ApplicationException on database error
     * @throws DuplicateRecordException when email already exists
     */
    public long add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {

        log.debug("FacultyModel add started");

        Connection conn = null;
        int pk = 0;

        CollegeModel collegeModel = new CollegeModel();
        bean.setCollegeName(collegeModel.findByPk(bean.getCollegeId()).getName());

        CourseModel courseModel = new CourseModel();
        bean.setCourseName(courseModel.findByPk(bean.getCourseId()).getName());

        SubjectModel subjectModel = new SubjectModel();
        bean.setSubjectName(subjectModel.findByPk(bean.getSubjectId()).getSubjectName());

        FacultyBean exist = findByEmail(bean.getEmail());
        if (exist != null) {
            log.warn("Duplicate Email : " + bean.getEmail());
            throw new DuplicateRecordException("Email Id already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPk();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                "insert into st_faculty values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getFirstName());
            pstmt.setString(3, bean.getLastName());
            pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(5, bean.getGender());
            pstmt.setString(6, bean.getMobileNo());
            pstmt.setString(7, bean.getEmail());
            pstmt.setLong(8, bean.getCollegeId());
            pstmt.setString(9, bean.getCollegeName());
            pstmt.setLong(10, bean.getCourseId());
            pstmt.setString(11, bean.getCourseName());
            pstmt.setLong(12, bean.getSubjectId());
            pstmt.setString(13, bean.getSubjectName());
            pstmt.setString(14, bean.getCreatedBy());
            pstmt.setString(15, bean.getModifiedBy());
            pstmt.setTimestamp(16, bean.getCreatedDatetime());
            pstmt.setTimestamp(17, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            log.info("Faculty added successfully, PK = " + pk);

        } catch (Exception e) {
            try { conn.rollback(); }
            catch (Exception ex) {
                log.error("Add rollback failed", ex);
                throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
            }
            log.error("Exception in add Faculty", e);
            throw new ApplicationException("Exception : Exception in add Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates existing faculty record.
     *
     * @param bean FacultyBean
     * @throws ApplicationException on database error
     * @throws DuplicateRecordException when email already exists for another record
     */
    public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {

        log.debug("FacultyModel update started for ID : " + bean.getId());

        Connection conn = null;

        bean.setCollegeName(new CollegeModel().findByPk(bean.getCollegeId()).getName());
        bean.setCourseName(new CourseModel().findByPk(bean.getCourseId()).getName());
        bean.setSubjectName(new SubjectModel().findByPk(bean.getSubjectId()).getSubjectName());

        FacultyBean exist = findByEmail(bean.getEmail());
        if (exist != null && exist.getId() != bean.getId()) {
            log.warn("Duplicate Email on update : " + bean.getEmail());
            throw new DuplicateRecordException("EmailId is already exist");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                "update st_faculty set first_name = ?, last_name = ?, dob = ?, gender = ?, mobile_no = ?, email = ?, "
                + "college_id = ?, college_name = ?, course_id = ?, course_name = ?, subject_id = ?, subject_name = ?, "
                + "created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

            pstmt.setString(1, bean.getFirstName());
            pstmt.setString(2, bean.getLastName());
            pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(4, bean.getGender());
            pstmt.setString(5, bean.getMobileNo());
            pstmt.setString(6, bean.getEmail());
            pstmt.setLong(7, bean.getCollegeId());
            pstmt.setString(8, bean.getCollegeName());
            pstmt.setLong(9, bean.getCourseId());
            pstmt.setString(10, bean.getCourseName());
            pstmt.setLong(11, bean.getSubjectId());
            pstmt.setString(12, bean.getSubjectName());
            pstmt.setString(13, bean.getCreatedBy());
            pstmt.setString(14, bean.getModifiedBy());
            pstmt.setTimestamp(15, bean.getCreatedDatetime());
            pstmt.setTimestamp(16, bean.getModifiedDatetime());
            pstmt.setLong(17, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            log.info("Faculty updated successfully, ID = " + bean.getId());

        } catch (Exception e) {
            try { conn.rollback(); }
            catch (Exception ex) {
                log.error("Update rollback failed", ex);
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            log.error("Exception in updating Faculty", e);
            throw new ApplicationException("Exception in updating Faculty ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes faculty record.
     *
     * @param bean FacultyBean containing id
     * @throws ApplicationException on database error
     */
    public void delete(FacultyBean bean) throws ApplicationException {

        log.debug("FacultyModel delete started for ID : " + bean.getId());

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_faculty where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

            log.info("Faculty deleted successfully, ID = " + bean.getId());

        } catch (Exception e) {
            try { conn.rollback(); }
            catch (Exception ex) {
                log.error("Delete rollback failed", ex);
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            log.error("Exception in delete Faculty", e);
            throw new ApplicationException("Exception : Exception in delete Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Fetches Faculty by primary key.
     *
     * @param pk primary key
     * @return FacultyBean
     * @throws ApplicationException if retrieval fails
     */
    public FacultyBean findByPk(long pk) throws ApplicationException {

        log.debug("FacultyModel findByPk started, PK = " + pk);

        StringBuffer sql = new StringBuffer("select * from st_faculty where id = ?");
        FacultyBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new FacultyBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setDob(rs.getDate(4));
                bean.setGender(rs.getString(5));
                bean.setMobileNo(rs.getString(6));
                bean.setEmail(rs.getString(7));
                bean.setCollegeId(rs.getLong(8));
                bean.setCollegeName(rs.getString(9));
                bean.setCourseId(rs.getLong(10));
                bean.setCourseName(rs.getString(11));
                bean.setSubjectId(rs.getLong(12));
                bean.setSubjectName(rs.getString(13));
                bean.setCreatedBy(rs.getString(14));
                bean.setModifiedBy(rs.getString(15));
                bean.setCreatedDatetime(rs.getTimestamp(16));
                bean.setModifiedDatetime(rs.getTimestamp(17));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            log.error("Exception in findByPk", e);
            throw new ApplicationException("Exception : Exception in getting Faculty by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds faculty by email.
     *
     * @param email faculty email
     * @return FacultyBean if found
     * @throws ApplicationException if retrieval fails
     */
    public FacultyBean findByEmail(String email) throws ApplicationException {

        log.debug("FacultyModel findByEmail started, Email = " + email);

        StringBuffer sql = new StringBuffer("select * from st_faculty where email = ?");
        FacultyBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new FacultyBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setDob(rs.getDate(4));
                bean.setGender(rs.getString(5));
                bean.setMobileNo(rs.getString(6));
                bean.setEmail(rs.getString(7));
                bean.setCollegeId(rs.getLong(8));
                bean.setCollegeName(rs.getString(9));
                bean.setCourseId(rs.getLong(10));
                bean.setCourseName(rs.getString(11));
                bean.setSubjectId(rs.getLong(12));
                bean.setSubjectName(rs.getString(13));
                bean.setCreatedBy(rs.getString(14));
                bean.setModifiedBy(rs.getString(15));
                bean.setCreatedDatetime(rs.getTimestamp(16));
                bean.setModifiedDatetime(rs.getTimestamp(17));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            log.error("Exception in findByEmail", e);
            throw new ApplicationException("Exception : Exception in getting Faculty by Email");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns list of all Faculty.
     *
     * @return list of FacultyBean
     * @throws ApplicationException if retrieval fails
     */
    public List<FacultyBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches faculty based on various filters.
     *
     * @param bean filter criteria
     * @param pageNo page number
     * @param pageSize number of records per page
     * @return list of FacultyBean
     * @throws ApplicationException if search fails
     */
    public List<FacultyBean> search(FacultyBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        log.debug("FacultyModel search started");

        StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) sql.append(" and id = " + bean.getId());
            if (bean.getCollegeId() > 0) sql.append(" and college_id = " + bean.getCollegeId());
            if (bean.getSubjectId() > 0) sql.append(" and subject_id = " + bean.getSubjectId());
            if (bean.getCourseId() > 0) sql.append(" and course_id = " + bean.getCourseId());

            if (bean.getFirstName() != null && bean.getFirstName().length() > 0)
                sql.append(" and first_name like '" + bean.getFirstName() + "%'");

            if (bean.getLastName() != null && bean.getLastName().length() > 0)
                sql.append(" and last_name like '" + bean.getLastName() + "%'");

            if (bean.getGender() != null && bean.getGender().length() > 0)
                sql.append(" and gender like '" + bean.getGender() + "%'");

            if (bean.getDob() != null && bean.getDob().getDate() > 0)
                sql.append(" and dob = " + bean.getDob());

            if (bean.getEmail() != null && bean.getEmail().length() > 0)
                sql.append(" and email like '" + bean.getEmail() + "%'");

            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0)
                sql.append(" and mobile_no = " + bean.getMobileNo());

            if (bean.getCourseName() != null && bean.getCourseName().length() > 0)
                sql.append(" and course_name like '" + bean.getCourseName() + "%'");

            if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0)
                sql.append(" and college_name like '" + bean.getCollegeName() + "%'");

            if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0)
                sql.append(" and subject_name like '" + bean.getSubjectName() + "%'");
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<FacultyBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new FacultyBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setDob(rs.getDate(4));
                bean.setGender(rs.getString(5));
                bean.setMobileNo(rs.getString(6));
                bean.setEmail(rs.getString(7));
                bean.setCollegeId(rs.getLong(8));
                bean.setCollegeName(rs.getString(9));
                bean.setCourseId(rs.getLong(10));
                bean.setCourseName(rs.getString(11));
                bean.setSubjectId(rs.getLong(12));
                bean.setSubjectName(rs.getString(13));
                bean.setCreatedBy(rs.getString(14));
                bean.setModifiedBy(rs.getString(15));
                bean.setCreatedDatetime(rs.getTimestamp(16));
                bean.setModifiedDatetime(rs.getTimestamp(17));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            log.error("Exception in search Faculty", e);
            throw new ApplicationException("Exception : Exception in search Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }
}