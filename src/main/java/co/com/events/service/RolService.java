/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.events.service;

/**
 *
 * @author Unicauca
 */

import co.com.events.domain.access.IRolRepositorio;
import co.com.events.domain.entities.Rol;

import java.util.List;

public class RolService  {
    private final IRolRepositorio rolRepository;

    public RolService(IRolRepositorio rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }
       // Método para buscar un rol por su nombre
    public Rol findByName(String nombre) {
        return rolRepository.findByName(nombre);
    }
}

