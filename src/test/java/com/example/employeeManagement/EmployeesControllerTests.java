package com.example.employeeManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.MockHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.employeeManagement.DTO.CreateEmployeeDto;
import com.example.employeeManagement.model.Department;
import com.example.employeeManagement.model.Employee;
import com.example.employeeManagement.model.Role;
import com.example.employeeManagement.repository.DepartmentRepository;
import com.example.employeeManagement.repository.EmployeeRepository;
import com.example.employeeManagement.service.impl.EmployeeServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

// @RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
// @WebMvcTest(controllers = EmployeesController.class)
public class EmployeesControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private EmployeeServiceImp empService;
        // @Autowired
        // private EmployeeRepository empRepo;
        @Autowired
        private DepartmentRepository deptRepo;

        @Test
        public void saveEmployee_shouldCreateEmployee() throws Exception {
                CreateEmployeeDto createEmployeeDto = CreateEmployeeDto.builder()
                                .email("email@email.com").lastName("Ali").salary(4000)
                                .firstName("Ahmed").password("123456")
                                .dept(1L)
                                .deptRepo(deptRepo)
                                .empNo(2L)
                                .dept(1L)
                                .build();

                Employee createdEmployee = Employee.builder().email("email@email.com").lastName("Ali").empNo(2L)
                                .firstName("Ahmed").password("123456").role(Role.ROLE_USER).salary(4000)
                                .department(Department.builder().deptName("ci").deptPhone("12345678").id(1L).build())
                                .id(1L)
                                .build();
                Mockito.when(empService.createEmp(Mockito.any())).thenReturn(createdEmployee);

                mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(createEmployeeDto)))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                                                Matchers.is("Ahmed")))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                                                Matchers.is("email@email.com")));
                // .andReturn().getResponse().getContentAsString();
                // assertEquals(createdEmployee, data, "does not match");
        }

        @Test
        public void getAllEmployees_shouldReturnListOfEmployees() throws Exception {
                List<Employee> employees = Arrays.asList(
                                Employee.builder().email("email@email.com").lastName("Ali")
                                                .firstName("Ahmed").password("123456").role(Role.ROLE_USER)
                                                .department(Department.builder().deptName("ci").deptPhone("12345678")
                                                                .id(1L).build())
                                                .build(),
                                Employee.builder().email("email2@email.com").lastName("Ahmed")
                                                .firstName("Ali").password("123456").role(Role.ROLE_USER)
                                                .department(Department.builder().deptName("ci").deptPhone("12345678")
                                                                .id(2L).build())
                                                .build());

                Mockito.when(empService.getAllEmployees()).thenReturn(employees);

                mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)));
        }

        @Test
        public void getEmployeeById_shouldReturnEmployee() throws Exception {
                Long employeeId = 1L;
                Employee employee = Employee.builder().id(1L).email("email@email.com").lastName("Ali")
                                .firstName("Ahmed").password("123456").role(Role.ROLE_USER)
                                .department(Department.builder().deptName("ci").deptPhone("12345678").id(1L).build())
                                .build();

                Mockito.when(empService.getEmployee(employeeId)).thenReturn(employee);

                mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employeeId))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(employeeId.intValue())))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Ahmed")))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                                                Matchers.is("email@email.com")));
        }

        @Test
        public void updateEmployee_shouldReturnUpdatedEmployee() throws Exception {
                Long employeeId = 1L;
                CreateEmployeeDto updateEmployeeDto = CreateEmployeeDto.builder()
                                .email("emailupdated@email.com").lastName("Ali").salary(4000)
                                .firstName("Ahmed").password("123456")
                                .dept(1L)
                                .deptRepo(deptRepo)
                                .empNo(2L)
                                .dept(1L)
                                .build();

                Employee updatedEmployee = Employee.builder().email("emailupdated@email.com").lastName("Ali")
                                .firstName("Ahmed").password("123456").role(Role.ROLE_USER).id(employeeId)
                                .department(new Department(1L, "HR", "00966535598878",
                                                null))
                                .empNo(1L)
                                .id(1L)
                                .build();
                String data = new ObjectMapper().writeValueAsString(updateEmployeeDto);

                Mockito.when(empService.updateEmployee(employeeId, updateEmployeeDto.toEmployee()))
                                .thenReturn(updatedEmployee);
                updateEmployeeDto = CreateEmployeeDto.builder().deptRepo(null).build();
                mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", employeeId.intValue())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id",
                                                Matchers.is(employeeId.intValue())))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Ahmed")))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                                                Matchers.is("emailupdated@email.com")));
                // .andDo(print());
        }

        @Test
        public void deleteEmployee_shouldReturnNoContent() throws Exception {
                Long employeeId = 1L;

                mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", employeeId))
                                .andExpect(MockMvcResultMatchers.status().isNoContent());

                Mockito.verify(empService, Mockito.times(1)).deleteEmployee(employeeId);
        }
}
