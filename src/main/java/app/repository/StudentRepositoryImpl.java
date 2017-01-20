package app.repository;

import app.model.Group;
import app.model.user.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * Created by Ionut on 31-Dec-16.
 */
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Student addGroupToStudent(Group group, Student student) {
        Query query = entityManager.createNativeQuery("insert into groups_students (student_id, group_id) values (?,?)");
        query.setParameter(1, student.getId());
        query.setParameter(2, group.getId());
        query.executeUpdate();
        return student;
    }

    @Override
    @Transactional
    public Student removeGroupOfStudent(Group group, Student student) {
        Query query = entityManager.createNativeQuery("delete from groups_students where student_id=? and group_id=?");
        query.setParameter(1, student.getId());
        query.setParameter(2, group.getId());
        query.executeUpdate();
        return student;
    }

}
