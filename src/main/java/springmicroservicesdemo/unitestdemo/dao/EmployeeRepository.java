package springmicroservicesdemo.unitestdemo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springmicroservicesdemo.unitestdemo.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
