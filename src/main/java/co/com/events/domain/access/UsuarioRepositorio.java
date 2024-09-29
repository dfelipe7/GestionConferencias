package co.com.events.domain.access;

import co.com.events.domain.entities.Usuario;
import co.com.events.domain.entities.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioRepositorio implements IUsuarioRepositorio {

    private Connection conn;

    public UsuarioRepositorio() {
        initDatabase();
    }

    @Override
    public boolean save(Usuario usuario) {
        try {
            String sql = "INSERT INTO usuario (username, password, email, roleId) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getEmail());

            if (usuario.getRole() != null) {
                pstmt.setLong(4, usuario.getRole().getRoleId());
                System.out.println("Rolbd"+usuario.getRole());
                System.out.println("Rolbd"+usuario.getRole().getRoleId());

            } else {
                pstmt.setNull(4, java.sql.Types.BIGINT); // Permite valores nulos
            }

            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Usuario findById(Long id) {
        try {
            RolRepositorio rolRepositorio = new RolRepositorio(); // Asegúrate de que esta conexión esté bien administrada
            String sql = "SELECT * FROM usuario WHERE userId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUserId(rs.getLong("userId"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setEmail(rs.getString("email"));
                 
                 Long roleId = rs.getLong("roleId");
                 if (!rs.wasNull()) {
                Rol rol = rolRepositorio.findById(roleId); // Método que debes implementar en RolRepositorio
                usuario.setRole(rol); // Establecer el rol en el usuario
            }
                
                // Aquí podrías necesitar buscar el rol en la base de datos
                return usuario;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
  @Override
public List<Usuario> findByRoleId(Long roleId) {
    RolRepositorio rolRepositorio = new RolRepositorio();
    List<Usuario> usuarios = new ArrayList<>();
    try {
        String sql = "SELECT * FROM usuario WHERE roleId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, roleId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setUserId(rs.getLong("userId"));
            usuario.setUsername(rs.getString("username"));
            usuario.setPassword(rs.getString("password"));
            usuario.setEmail(rs.getString("email"));
            
            // Obtener el rol del usuario
            Rol rol = new Rol();
            rol.setRoleId(roleId);
            rol.setRoleName(rolRepositorio.findById(roleId).getRoleName()); // Obtener el nombre del rol
            usuario.setRole(rol);
            
            usuarios.add(usuario);
        }
    } catch (SQLException ex) {
        Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
    }
    return usuarios;
}

   /*
@Override

public List<Usuario> findByRoleId(Long roleId) {
    List<Usuario> usuarios = new ArrayList<>();
    try {
        String sql = "SELECT * FROM usuario WHERE roleId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, roleId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setUserId(rs.getLong("userId"));
            usuario.setUsername(rs.getString("username"));
            usuario.setPassword(rs.getString("password"));
            usuario.setEmail(rs.getString("email"));

            // Obtenemos el ID del rol del usuario
            Long fetchedRoleId = rs.getLong("roleId");

            // Creamos un objeto Rol y asignamos el ID
            Rol rol = new Rol();
            rol.setRoleId(fetchedRoleId);
            
            // Establecemos el rol en el objeto usuario
            usuario.setRole(rol);
            
            // Añadimos el usuario a la lista de usuarios
            usuarios.add(usuario);
        }
    } catch (SQLException ex) {
        Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
    }
    return usuarios;
}
*/




    @Override
    public List<Usuario> findAll() {
    List<Usuario> usuarios = new ArrayList<>();
    RolRepositorio rolRepositorio = new RolRepositorio(); // Asegúrate de que esta conexión esté bien administrada
    try {
        String sql = "SELECT * FROM usuario";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setUserId(rs.getLong("userId"));
            usuario.setUsername(rs.getString("username"));
            usuario.setPassword(rs.getString("password"));
            usuario.setEmail(rs.getString("email"));
            
            Long roleId = rs.getLong("roleId");
            if (!rs.wasNull()) {
                Rol rol = rolRepositorio.findById(roleId); // Método que debes implementar en RolRepositorio
                usuario.setRole(rol); // Establecer el rol en el usuario
            }
            
            usuarios.add(usuario);
        }
    } catch (SQLException ex) {
        Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
    }
    return usuarios;
}


    @Override
    public boolean delete(Long id) {
        try {
            String sql = "DELETE FROM usuario WHERE userId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean edit(Long id, Usuario usuario) {
        try {
            String sql = "UPDATE usuario SET username = ?, password = ?, email = ?, roleId = ? WHERE userId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setLong(4, usuario.getRole().getRoleId());
            pstmt.setLong(5, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS usuario ("
                + "userId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL,"
                + "password TEXT NOT NULL,"
                + "email TEXT NOT NULL,"
                + "roleId INTEGER NULL );";
        try {
            connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, "SQL error during table creation", ex);
        }
    }

    public void connect() {
        String url = "jdbc:sqlite::memory:";  // Cambia esto según la ubicación de tu base de datos
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
