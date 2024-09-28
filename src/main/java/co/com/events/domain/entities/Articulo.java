package co.com.events.domain.entities;

public class Articulo {
    private Long articuloId;
    private String titulo;
    private String resumen;
    private String palabrasClave;
    private Long autorId;          // ID del autor
    private Long conferenciaId;    // ID de la conferencia a la que pertenece
    private byte[] pdfFile;        // Atributo para almacenar el archivo PDF
    private String nombrePdf;      // Nombre del archivo PDF

    // Constructor
    public Articulo() {
    }

    public Articulo(Long articuloId, String titulo, String resumen, String palabrasClave, Long autorId, Long conferenciaId, byte[] pdfFile, String nombrePdf) {
        this.articuloId = articuloId;
        this.titulo = titulo;
        this.resumen = resumen;
        this.palabrasClave = palabrasClave;
        this.autorId = autorId;
        this.conferenciaId = conferenciaId;
        this.pdfFile = pdfFile;
        this.nombrePdf = nombrePdf; // Inicializa el nombre del PDF
    }
    
    // Getters y Setters
    public Long getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Long articuloId) {
        this.articuloId = articuloId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public Long getConferenciaId() {
        return conferenciaId;
    }

    public void setConferenciaId(Long conferenciaId) {
        this.conferenciaId = conferenciaId;
    }

    public byte[] getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(byte[] pdfFile) {
        this.pdfFile = pdfFile;
    }

public String getNombrePdf() {
    return nombrePdf; // Asegúrate de que este valor esté inicializado correctamente
}


    public void setNombrePdf(String nombrePdf) {
        this.nombrePdf = nombrePdf;
    }
}
