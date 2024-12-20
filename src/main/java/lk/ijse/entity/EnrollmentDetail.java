package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "enrollment_details")
public class EnrollmentDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", referencedColumnName = "enrollmentId", nullable = false)
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "program_code", referencedColumnName = "program_code", nullable = false)
    private Program program;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;


    public EnrollmentDetail() {}


    public EnrollmentDetail(Enrollment enrollment, Program program, Student student, LocalDate enrollmentDate) {
        this.enrollment = enrollment;
        this.program = program;
        this.student = student;
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "EnrollmentDetail{" +
                "id=" + id +
                ", enrollment=" + enrollment +
                ", program=" + program +
                ", student=" + student +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
