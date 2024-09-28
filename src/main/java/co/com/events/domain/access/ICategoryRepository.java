package co.com.events.domain.access;

import co.com.events.domain.entities.Category;
import java.util.List;

public interface ICategoryRepository {

  boolean save(Category category);
    List<Category> findAll();
    Category findById(Long id);
    Category findByName(String name);
    //boolean update(Long id, Category category);
    boolean delete(Long id);
    boolean edit(Long id, Category editCategory);

}