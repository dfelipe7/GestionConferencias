/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.com.events.domain.access;


/**
 *
 * @author Unicauca
 */

import co.com.events.domain.entities.Rol;
import java.util.List;

public interface IRolRepositorio {
    boolean save(Rol rol);
    Rol findById(Long id);
    List<Rol> findAll();
    boolean delete(Long id);
    boolean edit(Long id, Rol rol);
    Rol findByName(String nombre);
}
