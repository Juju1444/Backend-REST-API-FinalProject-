package com.example.employeeManagement;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.employeeManagement.DTO.CreateDepartmentDto;
import com.example.employeeManagement.model.Department;
import com.example.employeeManagement.service.impl.DepaertmentImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
// @AutoConfigureTestDatabase()
// @WebMvcTest(controllers = DepartmentController.class)
class EmployeeManagementApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepaertmentImpl deptService;

	@Test
	void contextLoads() {
	}

	@Test
	public void getAllDepartments_shouldReturnListOfDepartments() throws Exception {
		List<Department> departments = Arrays.asList(
				new Department(1L, "Department1", "12345678", null),
				new Department(2L, "Department2", "12345678", null));

		Mockito.when(deptService.getAllDepartments()).thenReturn(departments);

		mockMvc.perform(MockMvcRequestBuilders.get("/depts"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)));
	}

	@Test
	public void getDepartmentById_shouldReturnDepartment() throws Exception {
		Long departmentId = 1L;
		Department department = new Department(departmentId, "Department1", "12345678", null);

		Mockito.when(deptService.getDepartment(departmentId)).thenReturn(department);

		mockMvc.perform(MockMvcRequestBuilders.get("/depts/{id}", departmentId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(departmentId.intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.deptName", Matchers.is("Department1")));
	}

	@Test
	public void saveDepartment_shouldCreateDepartment() throws Exception {
		CreateDepartmentDto createDepartmentDto = CreateDepartmentDto.builder().name("HR")
				.phone("00678754644").build();

		Department createdDepartment = new Department(1L, "New Department", "12345678", null);
		Mockito.when(deptService.createDepartment(Mockito.any())).thenReturn(createdDepartment);

		mockMvc.perform(MockMvcRequestBuilders.post("/depts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(createDepartmentDto)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.deptName", Matchers.is("New Department")));
	}

	@Test
	public void updateDepartment_shouldReturnUpdatedDepartment() throws Exception {
		Long departmentId = 1L;
		CreateDepartmentDto createDepartmentDto = CreateDepartmentDto.builder().name("HR updated")
				.phone("00678754644768").build();

		// CreateDepartmentDto updateDepartmentDto = new CreateDepartmentDto("Updated
		// Department");

		Department updatedDepartment = new Department(departmentId, "Updated Department", "12345678", null);
		Mockito.when(deptService.updateDepartment(departmentId, createDepartmentDto.toDepartment()))
				.thenReturn(updatedDepartment);

		mockMvc.perform(MockMvcRequestBuilders.put("/depts/{id}", departmentId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(createDepartmentDto)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(departmentId.intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.deptName", Matchers.is("Updated Department")));
	}

	@Test
	public void deleteDepartment_shouldReturnNoContent() throws Exception {
		Long departmentId = 1L;

		mockMvc.perform(MockMvcRequestBuilders.delete("/depts/{id}", departmentId))
				.andExpect(MockMvcResultMatchers.status().isNoContent());

		Mockito.verify(deptService, Mockito.times(1)).deleteDepartment(departmentId);
	}

}
