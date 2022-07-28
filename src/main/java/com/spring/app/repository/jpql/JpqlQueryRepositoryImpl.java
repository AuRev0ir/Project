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

    @Override
    public List<Employee> sortedEmployees(Long idOrganization) {

        String queryString = "SELECT s FROM Employee s " +
                "WHERE  s.organization.id = :idOrganization " +
                "ORDER BY s.hireDate ASC";

        TypedQuery<Employee> query = entityManager.createQuery(queryString, Employee.class);
        query.setParameter("idOrganization",idOrganization);
        return query.getResultList();
    }


    // Может быть подход не правильный, но для корректного удаления я создал 2 последовательные транзакции
    // В других случаях выбрасывала Exception

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void deleteEmployeeById(Long idEmployee) {

        String queryString = "DELETE FROM Employee s " +
                "WHERE s.id = :idEmployee ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("idEmployee", idEmployee);
        query.executeUpdate();

    }
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void deleteOrganizationById(Long idOrganization) {

        String queryString = "DELETE FROM Organization s " +
                "WHERE s.id = :idOrganization ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("idOrganization", idOrganization);
        query.executeUpdate();
    }
}
