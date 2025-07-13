package com.syscho.multi.datapopulator;

import com.syscho.multi.web.Employee;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/employees/records")
public class EmployeeBulkInsertController {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @PostMapping("/{totalRecords}")
    public void insertEmployeesInBatch(@PathVariable("totalRecords") int totalRecords) {
        int batchSize = 1000;


        Long singleResult = entityManager.createQuery("SELECT COUNT(e) FROM Employee e", Long.class)
                .getSingleResult();
        for (int i = 1; i <= totalRecords; i++) {
            Employee emp = new Employee();
            emp.setEmployeeId(singleResult + i);
            emp.setName("Employee " + i);
            emp.setAge(21);

            entityManager.persist(emp);

            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
                System.out.println("Inserted " + i + " records...");
            }
        }

        entityManager.flush();
        entityManager.clear();
    }


}
