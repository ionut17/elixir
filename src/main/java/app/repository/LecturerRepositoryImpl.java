package app.repository;

import app.model.Course;
import app.model.Group;
import app.model.user.Lecturer;
import app.model.user.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * Created by Ionut on 31-Dec-16.
 */
public class LecturerRepositoryImpl implements LecturerRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Lecturer addCourseToLecturer(Course course, Lecturer lecturer) {
        Query query = entityManager.createNativeQuery("insert into COURSE_OWNERSHIPS (lecturer_id, course_id) values (?,?)");
        query.setParameter(1, lecturer.getId());
        query.setParameter(2, course.getId());
        query.executeUpdate();
        return lecturer;
    }

    @Override
    @Transactional
    public Lecturer removeCourseOfLecturer(Course course, Lecturer lecturer) {
        Query query = entityManager.createNativeQuery("delete from COURSE_OWNERSHIPS where lecturer_id=? and course_id=?");
        query.setParameter(1, lecturer.getId());
        query.setParameter(2, course.getId());
        query.executeUpdate();
        return lecturer;
    }

}
