
package co.com.events.presentation;

import co.com.events.domain.access.ArticuloRepositorio;
import co.com.events.domain.access.CategoryRepository;
import co.com.events.domain.access.EventoRepository;
import co.com.events.service.ArticuloService;
import co.com.events.service.CategoryService;
import co.com.events.service.EventService;

/**
 *
 * @author Felipe Armero C
 */
public class Main {
    public static void main(String[] args) {
     // Crear instancias de los repositorios
    EventoRepository eventoRepository = new EventoRepository(); // O la implementación concreta que uses
    CategoryRepository categoryRepository = new CategoryRepository(); // O la implementación concreta que uses
    ArticuloRepositorio articuloRepository = new ArticuloRepositorio();

    // Crear instancia del servicio con los repositorios
    EventService eventService = new EventService(eventoRepository);
    CategoryService categoryService = new CategoryService(categoryRepository);
    ArticuloService articuloService = new ArticuloService(articuloRepository);


    // Crear la interfaz gráfica con el servicio
    GUIEventos guiProducts = new GUIEventos(eventService,categoryService); // Instancia de GUIProducts
    GUICategories guiCategories = new GUICategories(categoryService); // Instancia de GUICategories
    GUIArticulos guiArticulos=new GUIArticulos(articuloService);
    //GestionarPaper paper = new GestionarPaper(articuloService);
    // Hacer visible la interfaz gráfica de productos
   // guiProducts.setVisible(true);
    //guiCategories.setVisible(true);
   guiArticulos.setVisible(true);
    
      // Iniciar la interfaz en el hilo del Event Dispatch Thread
            // Instancia la clase del JFrame
            //paper.setVisible(true);

}}