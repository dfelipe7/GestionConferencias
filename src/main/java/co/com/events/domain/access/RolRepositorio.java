/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.events.domain.access;

import co.com.events.domain.entities.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.SQLException;

import java.sql.DriverManager;

import java.sql.SQLException;

public class RolRepositorio implements IRolRepositorio {

    private Connection conn;

    public RolRepositorio() {
        initDatabase();
    }

    @Override
    public boolean save(Rol rol) {
        try {
            String sql = "INSERT INTO rol (roleName) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, rol.getRoleName());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RolRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Rol findById(Long id) {
        try {
            String sql = "SELECT * FROM rol WHERE roleId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Rol rol = new Rol();
                rol.setRoleId(rs.getLong("roleId"));
                rol.setRoleName(rs.getString("roleName"));
                return rol;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public Rol findByName(String nombre) {
    try {
        String sql = "SELECT * FROM rol WHERE roleName = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nombre);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Rol rol = new Rol();
            rol.setRoleId(rs.getLong("roleId"));
            rol.setRoleName(rs.getString("roleName"));
            return rol;
        }
    } catch (SQLException ex) {
        Logger.getLogger(RolRepositorio.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}


    @Override
    public List<Rol> findAll() {
        List<Rol> roles = new ArrayList<>();
        try {
            String sql = "SELECT * FROM rol";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setRoleId(rs.getLong("roleId"));
                rol.setRoleName(rs.getString("roleName"));
                roles.add(rol);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }

    @Override
    public boolean delete(Long id) {
        try {
            String sql = "DELETE FROM rol WHERE roleId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RolRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean edit(Long id, Rol rol) {
        try {
            String sql = "UPDATE rol SET roleName = ? WHERE roleId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, rol.getRoleName());
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RolRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

 private void initDatabase() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS rol ("
                + "roleId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "roleName TEXT NOT NULL UNIQUE"
                + ");";

        try {
            connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sqlCreateTable);
            System.out.println("Table 'rol' created or already exists.");

            // Llenar la tabla con roles predefinidos si está vacía
            String sqlCheckEmpty = "SELECT COUNT(*) FROM rol";
            ResultSet rs = stmt.executeQuery(sqlCheckEmpty);
            if (rs.next() && rs.getInt(1) == 0) {
                // Insertar roles predefinidos
                String sqlInsert = "INSERT INTO rol (roleName) VALUES (?)";
                PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
                String[] predefinedRoles = {"Administrador", "Organizador", "Autor", "Evaluador"};
                for (String role : predefinedRoles) {
                    pstmt.setString(1, role);
                    pstmt.executeUpdate();
                }
                System.out.println("Default roles inserted.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolRepositorio.class.getName()).log(Level.SEVERE, "SQL error during table creation or initialization", ex);
        }
    }


    public void connect() {
        String url = "jdbc:sqlite::memory:";  // Cambia esto según la ubicación de tu base de datos
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(RolRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
