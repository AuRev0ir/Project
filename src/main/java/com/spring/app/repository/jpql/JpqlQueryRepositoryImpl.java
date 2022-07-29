package com.spring.app.repository.jpql;

import com.spring.app.domain.Employee;
import com.spring.app.exception.ServiceException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class JpqlQueryRepositoryImpl implements JpqlQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;



    // Может быть подход не правильный, но для корректного удаления я создал 2 последовательные транзакции
    // В других случаях выбрасывала Exception


    @Override
    public List<Employee> newMethodSorted(String name) {

        String queryString = "SELECT s FROM Employee s " +
                "WHERE  s.organization.name = :name " +
                "ORDER BY s.hireDate ASC";

        TypedQuery<Employee> query = entityManager.createQuery(queryString, Employee.class);
        query.setParameter("name", name);
        return query.getResultList();

    }

    @Transactional()
    @Override
    public void newMethodDeleteEmployeeById(Long idEmployee) {

        String queryString = "DELETE FROM Employee s " +
                "WHERE s.id = :idEmployee ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("idEmployee", idEmployee);
        query.executeUpdate();

    }
    @Transactional()
    @Override
    public void newMethodDeleteOrganizationById(String name) {

        String queryString = "DELETE FROM Organization s " +
                "WHERE s.name = :name ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("name", name);
        query.executeUpdate();
    }
}
