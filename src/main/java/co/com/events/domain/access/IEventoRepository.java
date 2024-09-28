/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.com.events.domain.access;

import co.com.events.domain.entities.Evento;
import java.util.List;

/**
 *
 * @author Armero
 */
public interface IEventoRepository {
    
    
    boolean save(Evento newEvento);
    
    boolean delete(Long id);
    
    boolean edit (Long id, Evento editEvento);
    
    Evento findById(Long id);
    
    Evento findByName(String eventName);
    
    
    List<Evento> findEventByCategory(Long categoryId);
    
    List<Evento> findAll();
}
