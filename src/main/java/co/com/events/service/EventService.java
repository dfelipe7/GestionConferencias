


package co.com.events.service;

import co.com.events.domain.access.CategoryRepository;
import co.com.events.domain.entities.Evento;
import co.com.events.domain.access.EventoRepository;
import co.com.events.domain.entities.Category;
import java.util.List;

public class EventService {
    private EventoRepository productRepository;
    private CategoryRepository categoryRepository;

    public EventService(EventoRepository productRepository) {
        this.productRepository = productRepository;
   
    }
     public EventService(EventoRepository productRepository,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository=categoryRepository;

    }

    // Métodos de producto

public boolean saveEvent(String name, String description, Long categoryId, Double precio, int aforo) {
    Evento newEvent = new Evento();
    newEvent.setEventName(name);
    newEvent.setEventDescrip(description);
    newEvent.setCategoryId(categoryId); // Acepta null
    newEvent.setAforo(aforo);
    newEvent.setEventPrice(precio);

    // Validar producto
    if (newEvent.getEventName().isEmpty()) {
        return false;
    }

    return productRepository.save(newEvent);
}

    public List<Evento> findAllProducts() {
        return productRepository.findAll();
    }

    public Evento findProductById(Long id) {
        return productRepository.findById(id);
    }

    public Evento findProductByName(String name) {
        return productRepository.findByName(name);
    }

    public boolean deleteProduct(Long id) {
        return productRepository.delete(id);
    }

    public boolean editEvent(Long eventId, Evento event) {
        // Validate product
        if (event == null || event.getEventName().isEmpty()) {
            return false;
        }
        return productRepository.edit(eventId, event);
    }
    
    public List<Evento> findEventByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

}