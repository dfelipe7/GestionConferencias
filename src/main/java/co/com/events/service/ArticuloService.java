package co.com.events.service;

import co.com.events.domain.access.ArticuloRepositorio;
import co.com.events.domain.entities.Articulo;
import co.com.events.domain.access.IArticuloRepositorio;
import java.io.File;
import java.util.List;

public class ArticuloService {
    private IArticuloRepositorio articuloRepositorio;

    public ArticuloService(IArticuloRepositorio articuloRepositorio) {
        this.articuloRepositorio = articuloRepositorio;
    }

    // Método para guardar un nuevo artículo junto con un archivo PDF
    public boolean saveArticulo(String titulo, String resumen, String palabrasClave, Long autorId, Long conferenciaId, File pdfFile) {
        Articulo nuevoArticulo = new Articulo();
        nuevoArticulo.setTitulo(titulo);
        nuevoArticulo.setResumen(resumen);
        nuevoArticulo.setPalabrasClave(palabrasClave);
        nuevoArticulo.setAutorId(autorId);
        nuevoArticulo.setConferenciaId(conferenciaId);
        

        // Validar artículo y el archivo PDF
        if (nuevoArticulo.getTitulo().isEmpty() || pdfFile == null || !pdfFile.exists()) {
            return false; // Validación básica de campos y archivo PDF
        }
        System.out.println("--"+pdfFile.getName());
        return articuloRepositorio.save(nuevoArticulo, pdfFile);
    }
  public byte[] obtenerPdfPorArticuloId(int articuloId) {
    return articuloRepositorio.obtenerPdfPorArticuloId(articuloId);
}

    // Método para obtener todos los artículos
    public List<Articulo> findAllArticulos() {
        return articuloRepositorio.findAll();
    }

    // Método para buscar un artículo por ID y descargar el PDF
    public Articulo findArticuloById(Long id, String outputFilePath) {
        return articuloRepositorio.findById(id, outputFilePath); // Guardar PDF en la ruta especificada
    }

    // Método para buscar un artículo por nombre
    public Articulo findArticuloByName(String titulo) {
        return articuloRepositorio.findByName(titulo);
    }

    // Método para eliminar un artículo
    public boolean deleteArticulo(Long id) {
        return articuloRepositorio.delete(id);
    }

    // Método para editar un artículo junto con el archivo PDF
    public boolean editArticulo(Long id, Articulo articulo, File pdfFile) {
        // Validar artículo y archivo PDF
        if (articulo == null || articulo.getTitulo().isEmpty() || pdfFile == null || !pdfFile.exists()) {
            return false;
        }
        return articuloRepositorio.edit(id, articulo, pdfFile);
    }
    

    // Método para buscar artículos por ID de conferencia
    public List<Articulo> findArticulosByConferenciaId(Long conferenciaId) {
        return articuloRepositorio.findArticuloByConferencia(conferenciaId);
    }
}
