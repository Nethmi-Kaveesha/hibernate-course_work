package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.EnrollmentDetailDto;
import lk.ijse.dto.EnrollmentDto;
import lk.ijse.dto.StudentDto;

import java.sql.SQLException;
import java.util.List;

public interface EnrollmentBO extends SuperBO {

    //public boolean save(EnrollmentDto enrollmentDto);
    boolean update(EnrollmentDto enrollmentDto);
     boolean delete(EnrollmentDto enrollmentDto);
     boolean search(EnrollmentDto enrollmentDto);
   // StudentDto searchStudent(String enrollmentId);

    public List<StudentDto> getAllStudents();

    String generateNewId();

    Object currentId();

    void registerEnrollment(EnrollmentDto enrollmentDTO, List<EnrollmentDetailDto> enrollmentDetailList) throws SQLException, ClassNotFoundException;
}
