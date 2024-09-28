package co.com.events.presentation;

import co.com.events.domain.entities.Articulo;
import co.com.events.service.ArticuloService;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GestionarPaper extends javax.swing.JFrame {

    private ArticuloService articuloService; // Servicio que maneja la lógica de artículos
    private String rutaArchivo = ""; // Ruta del archivo PDF seleccionado
    private Long idArticulo = null; // ID del artículo actual

    public GestionarPaper(ArticuloService articuloService) {
        this.articuloService = articuloService;
        initComponents();
        visualizarArticulos(); // Muestra los artículos en la tabla al inicializar
        activaBotones(false, false, false); // Desactiva los botones al inicio
        txtTitulo.setEnabled(false);
        txtResumen.setEnabled(false);
        txtPalabrasClave.setEnabled(false);
    }

    // Método para visualizar todos los artículos en la tabla
    public void visualizarArticulos() {
        List<Articulo> articulos = articuloService.findAllArticulos();
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0); // Limpiar tabla antes de llenarla

        for (Articulo articulo : articulos) {
            String nombrePdf = articulo.getNombrePdf();
            String rutaPdfCompleta;

            // Verifica si el nombre del PDF es válido
            if (nombrePdf == null || nombrePdf.isEmpty()) {
                nombrePdf = "No disponible"; // Valor por defecto
                rutaPdfCompleta = "No disponible"; // No construye la ruta
            } else {
                rutaPdfCompleta = "/ruta/completa/a/pdfs/" + nombrePdf; // Construye la ruta si hay un nombre de PDF
            }

            // Agregar fila al modelo
            Object[] row = new Object[]{
                articulo.getArticuloId(),
                articulo.getTitulo(),
                articulo.getResumen(),
                articulo.getPalabrasClave(),
                articulo.getAutorId(),
                articulo.getConferenciaId(),
                nombrePdf // Ahora muestra el nombre del PDF en la tabla
            };
            model.addRow(row);
        }

        // Agregar un MouseListener a la tabla para abrir el PDF
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabla.getSelectedRow();
                if (row >= 0) {
                    String pdfPath = (String) model.getValueAt(row, 6); // Suponiendo que la ruta del PDF es la columna 6
                    if (!pdfPath.equals("No disponible")) {
                        // Intenta abrir el PDF solo si hay un nombre válido
                        openPdf("/ruta/completa/a/pdfs/" + pdfPath);
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay PDF disponible para este artículo.");
                    }
                }
            }
        });
    }

    // Método para abrir un archivo PDF
    private void openPdf(String pdfPath) {
        try {
            File pdfFile = new File(pdfPath);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile); // Abre el PDF con el visor predeterminado
            } else {
                JOptionPane.showMessageDialog(null, "El archivo PDF no existe en la ruta: " + pdfPath);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir el PDF: " + ex.getMessage());
        }
    }

    // Método para guardar el paper a través de ArticuloService
    public void guardarPaper(String titulo, String resumen, String palabrasClave, Long autorId, Long conferenciaId, File ruta) {
        boolean success = articuloService.saveArticulo(titulo, resumen, palabrasClave, autorId, conferenciaId, ruta);
        if (success) {
            JOptionPane.showMessageDialog(null, "Artículo guardado exitosamente.");
            visualizarArticulos();
            activaBotones(false, false, false);
            txtTitulo.setEnabled(false);
            txtResumen.setEnabled(false);
            txtPalabrasClave.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar el artículo. Revisa los campos y selecciona un archivo PDF válido.");
        }
    }

    // Método para modificar un paper existente
    public void modificarPaper(Long id, String titulo, String resumen, String palabrasClave, Long autorId, Long conferenciaId, File ruta) {
        Articulo articulo = new Articulo();
        articulo.setTitulo(titulo);
        articulo.setResumen(resumen);
        articulo.setPalabrasClave(palabrasClave);
        articulo.setAutorId(autorId);
        articulo.setConferenciaId(conferenciaId);

        boolean success = articuloService.editArticulo(id, articulo, ruta);
        if (success) {
            JOptionPane.showMessageDialog(null, "Artículo modificado exitosamente.");
            visualizarArticulos();
            activaBotones(false, false, false);
            txtTitulo.setEnabled(false);
            txtResumen.setEnabled(false);
            txtPalabrasClave.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Error al modificar el artículo. Revisa los campos y selecciona un archivo PDF válido.");
        }
    }

    // Método para eliminar un paper
    public void eliminarPaper(Long id) {
        boolean success = articuloService.deleteArticulo(id);
        if (success) {
            JOptionPane.showMessageDialog(null, "Artículo eliminado exitosamente.");
            visualizarArticulos();
            activaBotones(false, false, false);
            txtTitulo.setEnabled(false);
            txtResumen.setEnabled(false);
            txtPalabrasClave.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar el artículo.");
        }
    }

    // Método para seleccionar un archivo PDF
    private void seleccionarPaper() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf")); // Filtra solo PDFs
        int returnValue = fileChooser.showOpenDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            rutaArchivo = selectedFile.getAbsolutePath(); // Guarda la ruta completa del archivo PDF
            // Aquí puedes mostrar la ruta en un JLabel o JTextField, por ejemplo:
            btnSeleccionar.setText(rutaArchivo); // Asegúrate de tener un campo para mostrar la ruta
        } else {
            JOptionPane.showMessageDialog(this, "No se seleccionó ningún archivo.");
        }
    }

    // Acción del botón para abrir el archivo PDF
    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {
        seleccionarPaper();
    }

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            idArticulo = (Long) tabla.getValueAt(row, 0); // Obtener ID del artículo
            txtTitulo.setText((String) tabla.getValueAt(row, 1));
            txtResumen.setText((String) tabla.getValueAt(row, 2));
            txtPalabrasClave.setText((String) tabla.getValueAt(row, 3));
            rutaArchivo = (String) tabla.getValueAt(row, 6); // Ruta del PDF

            activaBotones(false, true, true);
            txtTitulo.setEnabled(true);
            txtResumen.setEnabled(true);
            txtPalabrasClave.setEnabled(true);
        }
    }

    // Activa o desactiva los botones según el estado de la aplicación
    public void activaBotones(boolean guardar, boolean modificar, boolean eliminar) {
        btnGuardar.setEnabled(guardar);
        btnModificar.setEnabled(modificar);
        btnEliminar.setEnabled(eliminar);
        txtTitulo.setText("");
        txtResumen.setText("");
        txtPalabrasClave.setText("");
        btnSeleccionar.setText("Seleccionar...");
    }

    // Inicialización de componentes de la interfaz gráfica
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtResumen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPalabrasClave = new javax.swing.JTextField();
        btnSeleccionar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestión de Artículos");

        jLabel1.setText("Título:");
        jLabel2.setText("Resumen:");
        jLabel3.setText("Palabras clave:");

        btnSeleccionar.setText("Seleccionar PDF");
        btnSeleccionar.addActionListener(evt -> btnSeleccionarActionPerformed(evt));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(evt -> btnGuardarActionPerformed(evt));

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(evt -> btnModificarActionPerformed(evt));

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(evt -> btnEliminarActionPerformed(evt));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(evt -> btnNuevoActionPerformed(evt));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(evt -> btnCancelarActionPerformed(evt));

        tabla.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Título", "Resumen", "Palabras clave", "Autor ID", "Conferencia ID", "Archivo PDF"}
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        // Layout del panel y configuración
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPalabrasClave, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtResumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPalabrasClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSeleccionar)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
//private String rutaArchivo; // Inicialmente debe ser null o vacío
private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
    String titulo = txtTitulo.getText();
    String resumen = txtResumen.getText();
    String palabrasClave = txtPalabrasClave.getText();
    Long autorId = 1L; // Asignar autorId desde la sesión o la selección
    Long conferenciaId = 1L; // Asignar conferenciaId desde la selección del usuario

    // Verifica que se haya seleccionado un archivo PDF
    if (rutaArchivo == null || rutaArchivo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un archivo PDF.");
        return; // Sale si no hay archivo seleccionado
    }

    File archivo = new File(rutaArchivo); // Usa la ruta seleccionada

    if (idArticulo == null) {
        // Si idArticulo es null, llama a guardarPaper
        guardarPaper(titulo, resumen, palabrasClave, autorId, conferenciaId, archivo);
    } else {
        // Si existe un idArticulo, llama a modificarPaper
        modificarPaper(idArticulo, titulo, resumen, palabrasClave, autorId, conferenciaId, archivo);
    }
}

    // Acción del botón Modificar
private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {
    String titulo = txtTitulo.getText();
    String resumen = txtResumen.getText();
    String palabrasClave = txtPalabrasClave.getText();
    Long autorId = 1L; // Asignar autorId desde la sesión o la selección
    Long conferenciaId = 1L; // Asignar conferenciaId desde la selección del usuario
    File archivo = new File(rutaArchivo); // Cambiar ruta_archivo a rutaArchivo

    modificarPaper(idArticulo, titulo, resumen, palabrasClave, autorId, conferenciaId, archivo);
}


    // Acción del botón Eliminar
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        eliminarPaper(idArticulo);
    }

    // Acción del botón Nuevo
    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {
        activaBotones(true, false, false);
        txtTitulo.setEnabled(true);
        txtResumen.setEnabled(true);
        txtPalabrasClave.setEnabled(true);
        idArticulo = null;
    }

    // Acción del botón Cancelar
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        activaBotones(false, false, false);
        txtTitulo.setEnabled(false);
        txtResumen.setEnabled(false);
        txtPalabrasClave.setEnabled(false);
    }


    // Declaración de componentes
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtPalabrasClave;
    private javax.swing.JTextField txtResumen;
    private javax.swing.JTextField txtTitulo;
}
