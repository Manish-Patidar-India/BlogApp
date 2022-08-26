package com.manishblogapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manishblogapp.entities.Category;
import com.manishblogapp.exceptions.ResourceNotFoundException;
import com.manishblogapp.payloads.CategoryDto;
import com.manishblogapp.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository catrepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto cat) {

		Category category = catrepo.save(modelMapper.map(cat, Category.class));

		return modelMapper.map(category, CategoryDto.class);

	}

	@Override
	public CategoryDto findCategory(Integer id) {

		Category cat = catrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));

		return modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto cat, Integer id) {

		Category category = catrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));

		category.setTitle(cat.getTitle());

		catrepo.save(category);
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> findAllCategory() {

		List<Category> categories = catrepo.findAll();

		return categories.stream().map(e -> modelMapper.map(e, CategoryDto.class)).collect(Collectors.toList());

	}

	@Override
	public void deleteCategory(Integer id) {

		catrepo.findById(id).ifPresentOrElse(e -> {
			catrepo.deleteById(id);
		}, () -> {
			throw new ResourceNotFoundException("Category", "ID", id);
		});

	}

}
