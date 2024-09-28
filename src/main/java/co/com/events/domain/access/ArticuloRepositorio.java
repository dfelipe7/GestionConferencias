package co.com.events.domain.access;

import co.com.events.domain.access.IArticuloRepositorio;
import co.com.events.domain.entities.Articulo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticuloRepositorio implements IArticuloRepositorio {

    private Connection conn;

    // Constructor para inicializar la conexión a la base de datos
    public ArticuloRepositorio() {
        connect(); // Conectar al iniciar
        initDatabase();
    }

    // Inicializa la base de datos y crea la tabla si no existe
    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS articulo ("
                + "articuloId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulo TEXT NOT NULL,"
                + "resumen TEXT NOT NULL,"
                + "palabrasClave TEXT NOT NULL,"
                + "autorId INTEGER NOT NULL,"
                + "conferenciaId INTEGER NOT NULL,"
                + "pdf BLOB);";  // Añadir columna para el archivo PDF
        try {
            System.out.println("Executing SQL: " + sql);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Table created or already exists.");
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloRepositorio.class.getName()).log(Level.SEVERE, "SQL error during table creation", ex);
        }
    }

    // Conexión a la base de datos
    public void connect() {
        String url = "jdbc:sqlite::memory:"; // Cambia esto si necesitas una base de datos persistente
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para desconectar de la base de datos
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                conn = null; // Opción para limpiar la conexión
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloRepositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean save(Articulo newArticulo, File pdfFile) {
        try {
            String sql = "INSERT INTO articulo (titulo, resumen, palabrasClave, autorId, conferenciaId, pdf) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newArticulo.getTitulo());
            pstmt.setString(2, newArticulo.getResumen());
            pstmt.setString(3, newArticulo.getPalabrasClave());
            pstmt.setLong(4, newArticulo.getAutorId());
            pstmt.setLong(5, newArticulo.getConferenciaId());

            // Leer el archivo PDF y convertirlo en bytes
            byte[] pdfBytes = readFileToByteArray(pdfFile);
            if (pdfBytes != null) {
                pstmt.setBytes(6, pdfBytes);
            } else {
                pstmt.setNull(6, Types.BLOB);
            }

            pstmt.executeUpdate();
            return true;
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ArticuloRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        try {
            String sql = "DELETE FROM articulo WHERE articuloId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean edit(Long id, Articulo editArticulo, File pdfFile) {
        try {
            String sql = "UPDATE articulo SET titulo = ?, resumen = ?, palabrasClave = ?, autorId = ?, conferenciaId = ?, pdf = ? WHERE articuloId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, editArticulo.getTitulo());
            pstmt.setString(2, editArticulo.getResumen());
            pstmt.setString(3, editArticulo.getPalabrasClave());
            pstmt.setLong(4, editArticulo.getAutorId());
            pstmt.setLong(5, editArticulo.getConferenciaId());

            // Leer el archivo PDF y convertirlo en bytes
            byte[] pdfBytes = readFileToByteArray(pdfFile);
            if (pdfBytes != null) {
                pstmt.setBytes(6, pdfBytes);
            } else {
                pstmt.setNull(6, Types.BLOB);
            }

            pstmt.setLong(7, id);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ArticuloRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Articulo findById(Long id, String outputFilePath) {
        try {
            String sql = "SELECT * FROM articulo WHERE articuloId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Articulo articulo = new Articulo();
                articulo.setArticuloId(rs.getLong("articuloId"));
                articulo.setTitulo(rs.getString("titulo"));
                articulo.setResumen(rs.getString("resumen"));
                articulo.setPalabrasClave(rs.getString("palabrasClave"));
                articulo.setAutorId(rs.getLong("autorId"));
                articulo.setConferenciaId(rs.getLong("conferenciaId"));

                // Guardar el PDF en el archivo de salida si existe
                byte[] pdfBytes = rs.getBytes("pdf");
                if (pdfBytes != null) {
                    writeByteArrayToFile(pdfBytes, outputFilePath);
                }

                return articulo;
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ArticuloRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Articulo findByName(String articuloName) {
        try {
            String sql = "SELECT * FROM articulo WHERE titulo = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, articuloName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Articulo articulo = new Articulo();
                articulo.setArticuloId(rs.getLong("articuloId"));
                articulo.setTitulo(rs.getString("titulo"));
                articulo.setResumen(rs.getString("resumen"));
                articulo.setPalabrasClave(rs.getString("palabrasClave"));
                articulo.setAutorId(rs.getLong("autorId"));
                articulo.setConferenciaId(rs.getLong("conferenciaId"));
                return articulo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Articulo> findAll() {
        List<Articulo> articulos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM articulo";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Articulo articulo = new Articulo();
                articulo.setArticuloId(rs.getLong("articuloId"));
                articulo.setTitulo(rs.getString("titulo"));
                articulo.setResumen(rs.getString("resumen"));
                articulo.setPalabrasClave(rs.getString("palabrasClave"));
                articulo.setAutorId(rs.getLong("autorId"));
                articulo.setConferenciaId(rs.getLong("conferenciaId"));
                articulo.setPdfFile(rs.getBytes("pdf"));
                articulos.add(articulo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articulos;
    }

    @Override
    public List<Articulo> findArticuloByConferencia(Long conferenciaId) {
        List<Articulo> articulos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM articulo WHERE conferenciaId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, conferenciaId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Articulo articulo = new Articulo();
                articulo.setArticuloId(rs.getLong("articuloId"));
                articulo.setTitulo(rs.getString("titulo"));
                articulo.setResumen(rs.getString("resumen"));
                articulo.setPalabrasClave(rs.getString("palabrasClave"));
                articulo.setAutorId(rs.getLong("autorId"));
                articulo.setConferenciaId(rs.getLong("conferenciaId"));
                articulos.add(articulo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articulos;
    }
    
    @Override
public byte[] obtenerPdfPorArticuloId(int articuloId) {
    byte[] pdfData = null;

    String sql = "SELECT pdf FROM articulo WHERE articuloId = ?";
    try (
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, articuloId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            pdfData = rs.getBytes("pdf");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return pdfData;
}


    // Métodos auxiliares para manejo de archivos
    private byte[] readFileToByteArray(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        }
    }

private void writeByteArrayToFile(byte[] bytes, String filePath) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(filePath)) {
        fos.write(bytes);
    }
}

      @Override
    public String getPdfFileName(Long articuloId) {
        // Implementación del método
        return null;
        // Implementación del método
    }
}
