package springmicroservicesdemo.unitestdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springmicroservicesdemo.unitestdemo.dao.EmployeeRepository;
import springmicroservicesdemo.unitestdemo.model.Employee;
import springmicroservicesdemo.unitestdemo.model.Employees;

import java.net.URI;
import java.util.ArrayList;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(path = "/employees", produces = "application/json")
    public Employees getEmployees() {
        Employees response = new Employees();
        ArrayList<Employee> list = new ArrayList<>();
        employeeRepository.findAll().forEach(e -> list.add(e));
        response.setEmployeeList(list);
        return response;
    }

    @PostMapping(path = "/employees", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {

        //add resource
        employee = employeeRepository.save(employee);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employee.getId())
                .toUri();

        //Send location in response
        return ResponseEntity.created(location).build();
    }
}
