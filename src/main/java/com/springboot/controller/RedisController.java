package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Student;
import com.springboot.repository.StudentService;

@RestController
@RequestMapping("/redis")
public class RedisController {

	@Autowired
	private StudentService studentService;

	@PostMapping
	public void saveStudentInformation(@RequestBody Student student) {
		studentService.save(student);
	}

	@Cacheable(key="#id", value="students", unless = "#result.id < 1200")
	@GetMapping(path = "{id}")
	public Student fetchStudent(@PathVariable("id") long id) {
		return studentService.find(id);
	}
	
	//@CacheEvict - Delete from Cache. Use it with DeleteMapping
	//@CachePut - Update a Cache. Use it with PutMapping

	@CachePut(key="#student.id", value="students")
	@PutMapping(path = "{id}")
	public Student updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
		student.setId(id);
		studentService.save(student);
		return student;
	}

	@CacheEvict(key="#id", value="students")
	@DeleteMapping(path = "{id}")
	public void deleteStudent(@PathVariable("id") long id) {
		studentService.delete(id);
	}
}