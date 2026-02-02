package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * FacultyModel provides CRUD and search operations for {@link FacultyBean}
 * against the database table {@code st_faculty}.
 * <p>
 * It uses {@link JDBCDataSource} to obtain and close connections and throws
 * application-specific checked exceptions to signal error conditions.
 * </p>
 * 
 * @author Chaitanya Bhatt
 * @version 1.0
 */
public class FacultyModel {

    /**
     * Returns the next primary key value for the st_faculty table.
     *
     * @return next primary key value
     * @throws DatabaseException if a database error occurs while retrieving the maximum id
     */
    public Integer nextPk() throws DatabaseException {
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_faculty");
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
     * Adds a new Faculty record to the database.
     * <p>
     * Before insertion it resolves and sets college/course/subject names and
     * checks for duplicate email and throws {@link DuplicateRecordException}
     * if a record with same email exists.
     * </p>
     *
     * @param bean {@link FacultyBean} containing faculty data to add
     * @return the primary key of the newly inserted faculty
     * @throws ApplicationException     if a general application/database error occurs
     * @throws DuplicateRecordException if a faculty with same email already exists
     */
    public long add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        int pk = 0;

        try {
            // resolve college name
            CollegeModel collegeModel = new CollegeModel();
            CollegeBean collegeBean = collegeModel.findByPk(bean.getCollegeId());
            bean.setCollegeName(collegeBean != null ? collegeBean.getName() : null);

            // resolve course name
            CourseModel courseModel = new CourseModel();
            CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
            bean.setCourseName(courseBean != null ? courseBean.getName() : null);

            // resolve subject name
            SubjectModel subjectModel = new SubjectModel();
            SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
            bean.setSubjectName(subjectBean != null ? subjectBean.getName() : null);
        } catch (ApplicationException e) {
            // If resolving names fails, rethrow as ApplicationException
            throw new ApplicationException("Exception : Exception while resolving related names: " + e.getMessage());
        }

        FacultyBean existbean = findByEmail(bean.getEmail());

        if (existbean != null) {
            throw new DuplicateRecordException("Email Id already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPk();
            conn.setAutoCommit(false); // Begin transaction
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
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in add Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing Faculty record.
     * <p>
     * It resolves college/course/subject names and checks for duplicate email
     * (other than current record) and throws {@link DuplicateRecordException}
     * if a different record with same email exists.
     * </p>
     *
     * @param bean {@link FacultyBean} containing updated faculty data
     * @throws ApplicationException     if a general application/database error occurs
     * @throws DuplicateRecordException if another faculty with same email exists
     */
    public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;

        try {
            // get College Name
            CollegeModel collegeModel = new CollegeModel();
            CollegeBean collegeBean = collegeModel.findByPk(bean.getCollegeId());
            bean.setCollegeName(collegeBean != null ? collegeBean.getName() : null);

            // get Course Name
            CourseModel courseModel = new CourseModel();
            CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
            bean.setCourseName(courseBean != null ? courseBean.getName() : null);

            // get Subject Name
            SubjectModel subjectModel = new SubjectModel();
            SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
            bean.setSubjectName(subjectBean != null ? subjectBean.getName() : null);
        } catch (ApplicationException e) {
            throw new ApplicationException("Exception : Exception while resolving related names: " + e.getMessage());
        }

        FacultyBean beanExist = findByEmail(bean.getEmail());
        if (beanExist != null && !(beanExist.getId() == bean.getId())) {
            throw new DuplicateRecordException("EmailId is already exist");
        }
        try {
            conn = JDBCDataSource.getConnection();

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_faculty set first_name = ?, last_name = ?, dob = ?, gender = ?, mobile_no = ?, email = ?, college_id = ?, college_name = ?, course_id = ?, course_name = ?, subject_id = ?, subject_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

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
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Faculty ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes a Faculty record.
     *
     * @param bean {@link FacultyBean} whose id identifies the faculty to delete
     * @throws ApplicationException if a general application/database error occurs
     */
    public void delete(FacultyBean bean) throws ApplicationException {
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_faculty where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in delete Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds a Faculty record by primary key.
     *
     * @param pk primary key (id) of the faculty
     * @return {@link FacultyBean} if found; {@code null} otherwise
     * @throws ApplicationException if a general application/database error occurs
     */
    public FacultyBean findByPk(long pk) throws ApplicationException {
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
            throw new ApplicationException("Exception : Exception in getting Faculty by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds a Faculty record by its email.
     *
     * @param email email of the faculty
     * @return {@link FacultyBean} if found; {@code null} otherwise
     * @throws ApplicationException if a general application/database error occurs
     */
    public FacultyBean findByEmail(String email) throws ApplicationException {
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
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in getting Faculty by Email");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns a list of all faculty. This is a convenience wrapper around
     * {@link #search(FacultyBean, int, int)} with no filter and no pagination.
     *
     * @return list of all {@link FacultyBean}
     * @throws ApplicationException if a general application/database error occurs
     */
    public List<FacultyBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches for faculty matching the criteria provided in {@code bean}.
     * If {@code pageSize} &gt; 0, results are paginated.
     *
     * @param bean     filter criteria; if {@code null} returns all records
     * @param pageNo   page number (1-based) when paginating; ignored if pageSize is 0
     * @param pageSize number of records per page; pass 0 to disable pagination
     * @return list of matching {@link FacultyBean}
     * @throws ApplicationException if a general application/database error occurs
     */
    public List<FacultyBean> search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }
            if (bean.getCollegeId() > 0) {
                sql.append(" and college_id = " + bean.getCollegeId());
            }
            if (bean.getSubjectId() > 0) {
                sql.append(" and subject_id = " + bean.getSubjectId());
            }
            if (bean.getCourseId() > 0) {
                sql.append(" and course_id = " + bean.getCourseId());
            }
            if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
                sql.append(" and first_name like '" + bean.getFirstName() + "%'");
            }
            if (bean.getLastName() != null && bean.getLastName().length() > 0) {
                sql.append(" and last_name like '" + bean.getLastName() + "%'");
            }
            if (bean.getGender() != null && bean.getGender().length() > 0) {
                sql.append(" and gender like '" + bean.getGender() + "%'");
            }
            if (bean.getDob() != null) {
                sql.append(" and dob = '" + new java.sql.Date(bean.getDob().getTime()) + "'");
            }
            if (bean.getEmail() != null && bean.getEmail().length() > 0) {
                sql.append(" and email like '" + bean.getEmail() + "%'");
            }
            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
                sql.append(" and mobile_no = " + bean.getMobileNo());
            }
            if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
                sql.append(" and course_name like '" + bean.getCourseName() + "%'");
            }
            if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
                sql.append(" and college_name like '" + bean.getCollegeName() + "%'");
            }
            if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
                sql.append(" and subject_name like '" + bean.getSubjectName() + "%'");
            }
        }
        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<FacultyBean> list = new ArrayList<FacultyBean>();
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
            throw new ApplicationException("Exception : Exception in search Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }
}
