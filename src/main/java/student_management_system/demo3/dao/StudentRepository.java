package student_management_system.demo3.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import student_management_system.demo3.model.Student;
import student_management_system.demo3.model.StudentCourse;

import java.util.List;
import java.util.UUID;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StudentMapper studentMapper;

    public StudentRepository(JdbcTemplate jdbcTemplate,StudentMapper studentMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentMapper=studentMapper;
    }

    public List<Student> selectAllStudents() {
        String sql = "" +
                "SELECT " +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email, " +
                " gender " +
                "FROM student";

        return jdbcTemplate.query(sql, studentMapper.mapStudentFomDb());
    }
    public Student selectStudentById(UUID studentId) {
        String sql = "" +
                "SELECT " +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email, " +
                " gender " +
                "FROM student WHERE student_id=?" ;


       return jdbcTemplate.queryForObject(sql,new Object[]{studentId},studentMapper.mapStudentFomDb());
    }

    public int updateEmail(UUID studentId, String email) {
        String sql = "" +
                "UPDATE student " +
                "SET email = ? " +
                "WHERE student_id = ?";
        return jdbcTemplate.update(sql, email, studentId);
    }

    public Student insertStudent(UUID studentId, Student student) {
        String sql = "" +
                "INSERT INTO student (" +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email, " +
                " gender) " +
                "VALUES (?, ?, ?, ?, ?::gender)";
         jdbcTemplate.update(
                sql,
                studentId,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getGender().name().toUpperCase()
        );
         return student;
    }

    @SuppressWarnings("ConstantConditions")
    public boolean isEmailTaken(String email) {
        String sql = "" +
                "SELECT EXISTS ( " +
                " SELECT 1 " +
                " FROM student " +
                " WHERE email = ?" +
                ")";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{email},
                (resultSet, i) -> resultSet.getBoolean(1)
        );
    }


    public List<StudentCourse> selectAllStudentCourses(UUID studentId) {
        String sql = "" +
                "SELECT " +
                " student.student_id, " +
                " course.course_id, " +
                " course.name, " +
                " course.description," +
                " course.department," +
                " course.teacher_name," +
                " student_course.start_date, " +
                " student_course.end_date, " +
                " student_course.grade " +
                "FROM student " +
                "JOIN student_course USING (student_id) " +
                "JOIN course         USING (course_id) " +
                "WHERE student.student_id = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{studentId},
                studentMapper.mapStudentCourseFromDb()
        );
    }



    public int updateFirstName(UUID studentId, String firstName) {
        String sql = "" +
                "UPDATE student " +
                "SET first_name = ? " +
                "WHERE student_id = ?";
        return jdbcTemplate.update(sql, firstName, studentId);
    }

    public int updateLastName(UUID studentId, String lastName) {
        String sql = "" +
                "UPDATE student " +
                "SET last_name = ? " +
                "WHERE student_id = ?";
        return jdbcTemplate.update(sql, lastName, studentId);
    }

    @SuppressWarnings("ConstantConditions")
    public boolean selectExistsEmail(UUID studentId, String email) {
        String sql = "" +
                "SELECT EXISTS ( " +
                "   SELECT 1 " +
                "   FROM student " +
                "   WHERE student_id <> ? " +
                "    AND email = ? " +
                ")";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{studentId, email},
                (resultSet, columnIndex) -> resultSet.getBoolean(1)
        );
    }

    public int deleteStudentById(UUID studentId) {
        String sql = "" +
                "DELETE FROM student " +
                "WHERE student_id = ?";
        return jdbcTemplate.update(sql, studentId);
    }

}