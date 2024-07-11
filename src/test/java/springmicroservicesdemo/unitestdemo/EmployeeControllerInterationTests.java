package springmicroservicesdemo.unitestdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import springmicroservicesdemo.unitestdemo.model.Employee;
import springmicroservicesdemo.unitestdemo.model.Employees;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = UnitestDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerInterationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Sql({ "classpath:schema.sql", "classpath:data.sql" })
    @Test
    public void testAllEmployees()
    {
        assertTrue(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/employees", Employees.class)
                        .getEmployeeList().size() == 3);
    }

    @Test
    public void testAddEmployee() {
        Employee employee = new Employee("Lokesh", "Gupta", "howtodoinjava@gmail.com");
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/employees", employee, String.class);
        assertEquals(201, responseEntity.getStatusCode().value());
    }
}
