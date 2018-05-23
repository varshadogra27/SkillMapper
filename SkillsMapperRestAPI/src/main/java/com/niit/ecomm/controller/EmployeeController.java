package com.niit.ecomm.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niit.skills.SkillMappingEngine.dao.EmployeeDAO;
import com.niit.skills.SkillMappingEngine.model.Employee;




@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@GetMapping("/employees")
	public ResponseEntity<?> getAllEmployees() {
		
		List<Employee> list = employeeDAO.findAll();
		if(list != null){
		return new ResponseEntity<List<Employee>>(list,HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("No Employee found",HttpStatus.NO_CONTENT);
		}
			
	}
	@PostMapping("/save")
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
		
		boolean status = employeeDAO.addEmployee(employee);
		return new ResponseEntity<String>("Employee Saved Successfully", HttpStatus.CREATED);
	}
	@PostMapping("/delete/{emailId}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("emailId") String email){
		
		boolean status= employeeDAO.deleteEmployee(email);
		return new ResponseEntity<String>("Employee Deleted Successfully", HttpStatus.OK);
	}
	@GetMapping("/employee/{emailId}")
	public ResponseEntity<?> employeeById(@PathVariable("emailId") String email){
		
		
		Employee employee = employeeDAO.getEmployeeById(emailId);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	@PostMapping("/update")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee){
		employeeDAO.updateEmployee(employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	@PostMapping("/login")
	public ResponseEntity<?> loginEmployee(@RequestBody Employee employee, HttpSession session){
		boolean status = employeeDAO.validateEmployee(employee.getEmail(), employee.getPassword());
		if(status){
			session.setAttribute("loggedInUser", employee.getEmail());
			return new ResponseEntity<String>("LoggedIn Successfully", HttpStatus.OK);
		}else
		{
			return new ResponseEntity<String>("Invalid Credentials", HttpStatus.NOT_FOUND);
		}
	}
}
