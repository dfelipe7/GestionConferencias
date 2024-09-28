package co.com.events.service;

import co.com.events.domain.access.ICategoryRepository;
import co.com.events.domain.entities.Category;
import java.util.List;

public class CategoryService {

    private ICategoryRepository repository;

    public CategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }

    public boolean saveCategory(String name) {
        Category newCategory = new Category();
        newCategory.setName(name);

        if (newCategory.getName().isEmpty()) {
            return false;
        }
        this.repository.save(newCategory);
        return true;
    }

    public List<Category> findAllCategories() {
        return this.repository.findAll();
    }

    public Category findCategoryById(Long id) {
        return this.repository.findById(id);
    }

    public Category findCategoryByName(String name) {
        return this.repository.findByName(name);
    }

    public boolean deleteCategory(Long id) {
        return this.repository.delete(id);
    }

    public boolean editCategory(Long categoryId, Category category) {
        if (category == null || category.getName().isEmpty()) {
            return false;
        }
        return this.repository.edit(categoryId, category);
    }
}
