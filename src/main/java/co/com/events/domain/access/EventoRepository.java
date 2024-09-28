/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.events.domain.access;

/**
 *
 * @author Armero
 */
import co.com.events.domain.entities.Evento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.JavaFileManager.Location;

public class EventoRepository implements IEventoRepository{

    private Connection conn;

    public EventoRepository() {
        initDatabase();
    }

    @Override
    public boolean save(Evento event) {
    try {
        String sql = "INSERT INTO evento (eventName, eventDescrip, eventPrice, eventAforo, CategoryId) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, event.getEventName());
        pstmt.setString(2, event.getEventDescrip());
        pstmt.setDouble(3, event.getEventPrice());
        pstmt.setInt(4, event.getAforo());
        

        if (event.getCategoryId() != null) {
            pstmt.setLong(5, event.getCategoryId());
        } else {
            pstmt.setNull(5, java.sql.Types.BIGINT); // Permite valores nulos
        }

        pstmt.executeUpdate();
        return true;
    } catch (SQLException ex) {
        Logger.getLogger(EventoRepository.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}



    @Override
    public List<Evento> findAll() {
        List<Evento> eventos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM evento";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Evento evento = new Evento();
                evento.setEventId(rs.getLong("eventId"));
                evento.setEventName(rs.getString("eventName"));
                evento.setEventDescrip(rs.getString("eventDescrip"));
                evento.setEventPrice(rs.getLong("eventPrice"));
                evento.setAforo(rs.getInt("eventAforo"));
                eventos.add(evento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventoRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eventos;
    }
    
    @Override
    public Evento findByName(String name) {
        try {
            String sql = "SELECT * FROM evento WHERE eventName = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Evento evento = new Evento();
                evento.setEventId(rs.getLong("eventId"));
                evento.setEventName(rs.getString("eventName"));
                evento.setEventDescrip(rs.getString("eventDescrip"));
                evento.setEventPrice(rs.getDouble("eventPrice"));
                evento.setAforo(rs.getInt("eventAforo"));
                evento.setCategoryId(rs.getLong("categoryId"));
                return evento;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventoRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Evento findById(Long id) {
        try {
            String sql = "SELECT * FROM evento WHERE eventId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Evento evento = new Evento();
                
                evento.setEventId(rs.getLong("eventId"));
                evento.setEventName(rs.getString("eventName"));
                evento.setEventDescrip(rs.getString("eventDescrip"));
                evento.setEventPrice(rs.getDouble("eventPrice"));
                evento.setAforo(rs.getInt("eventAforo"));
                evento.setCategoryId(rs.getLong("categoryId"));
                return evento;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventoRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            String sql = "DELETE FROM evento WHERE eventId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EventoRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean edit(Long id, Evento evento) {
    try {
        String sql = "UPDATE evento SET eventName = ?, eventDescrip = ?, categoryId = ?, eventPrice = ?, eventAforo = ? WHERE eventId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, evento.getEventName());
        pstmt.setString(2, evento.getEventDescrip());
        pstmt.setDouble(4, evento.getEventPrice());
        pstmt.setInt(5, evento.getAforo());

        Long categoryId = evento.getCategoryId();
        if (categoryId != null) {
            pstmt.setLong(3, categoryId);
        } else {
            pstmt.setNull(3, java.sql.Types.BIGINT);
        }

        pstmt.setLong(4, id);
        pstmt.executeUpdate();
        return true;
    } catch (SQLException ex) {
        Logger.getLogger(EventoRepository.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}

public List<Evento> findByCategoryId(Long categoryId) {
    List<Evento> eventos = new ArrayList<>();
    String sql = "SELECT * FROM evento WHERE categoryId = ?";
    
    try {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, categoryId);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Evento evento = new Evento();
            evento.setEventId(rs.getLong("eventId"));
            evento.setEventName(rs.getString("eventName"));
            evento.setEventDescrip(rs.getString("eventDescrip"));
            evento.setCategoryId(rs.getLong("categoryId"));
            eventos.add(evento);
        }
    } catch (SQLException ex) {
        Logger.getLogger(EventoRepository.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return eventos;
}


    

private void initDatabase() {
    String sql = "CREATE TABLE IF NOT EXISTS evento ("
            + "eventId INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "eventName TEXT NOT NULL,"
            + "eventDescrip TEXT NOT NULL,"
            + "eventPrice DOUBLE NOT NULL,"
            + "eventAforo INTEGER NOT NULL," 
            + "categoryId INTEGER NULL );";
    try {
        connect();
        System.out.println("Executing SQL: " + sql); // Mensaje de depuración
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        System.out.println("Table created or already exists.");
    } catch (SQLException ex) {
        Logger.getLogger(EventoRepository.class.getName()).log(Level.SEVERE, "SQL error during table creation", ex);
    } finally {
        //disconnect();
    }
}


    public void connect() {
        String url = "jdbc:sqlite::memory:";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(EventoRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Evento> findEventByCategory(Long categoryId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}