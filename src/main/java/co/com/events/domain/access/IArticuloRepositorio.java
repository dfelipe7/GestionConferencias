package co.com.events.domain.access;

import co.com.events.domain.entities.Articulo;
import java.io.File;
import java.util.List;

/**
 *
 * @author Unicauca
 */
public interface IArticuloRepositorio {
    
    byte[] obtenerPdfPorArticuloId(int articuloId);


    // Guardar un nuevo artículo, incluyendo el archivo PDF asociado
    boolean save(Articulo newArticulo, File pdfFile);

    // Eliminar un artículo por su ID
    boolean delete(Long id);

    // Editar un artículo existente por su ID, incluyendo la actualización del archivo PDF
    boolean edit(Long id, Articulo editArticulo, File pdfFile);

    // Buscar un artículo por su ID y guardar el archivo PDF recuperado en una ruta de salida
    Articulo findById(Long id, String outputFilePath);

    // Buscar un artículo por su título
    Articulo findByName(String articuloName);

    // Obtener una lista de artículos asociados a una conferencia en particular
    List<Articulo> findArticuloByConferencia(Long conferenciaId);

    // Obtener todos los artículos
    List<Articulo> findAll();

    // Opcional: Método para obtener el nombre del archivo PDF dado el ID del artículo
    String getPdfFileName(Long articuloId);
}
