package com.manishblogapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manishblogapp.payloads.ApiResponse;
import com.manishblogapp.payloads.CategoryDto;
import com.manishblogapp.services.CategoryServiceImpl;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl catservice;
    
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catdto) {

		CategoryDto cat = catservice.createCategory(catdto);

		return new ResponseEntity<>(cat, HttpStatus.CREATED);

	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> findCategory(@PathVariable Integer id) {

		CategoryDto cat = catservice.findCategory(id);

		return new ResponseEntity<>(cat, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> findAllCategories() {

		List<CategoryDto> cat = catservice.findAllCategory();

		return new ResponseEntity<>(cat, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catdto,
			@PathVariable Integer id) {

		CategoryDto category = catservice.updateCategory(catdto, id);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deletecategory(@PathVariable Integer id) {

		catservice.deleteCategory(id);

		return new ResponseEntity<>(new ApiResponse("deleted successfully", "Success"), HttpStatus.OK);
	}
}
