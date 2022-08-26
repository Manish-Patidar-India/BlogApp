package com.manishblogapp.services;

import java.util.List;

import com.manishblogapp.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto cat);

	CategoryDto findCategory(Integer id);

	CategoryDto updateCategory(CategoryDto cat, Integer id);

	List<CategoryDto> findAllCategory();

	void deleteCategory(Integer id);

}
