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
import co.com.events.domain.entities.Usuario;
import co.com.events.domain.access.IUsuarioRepositorio;
import co.com.events.domain.entities.Rol;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService  {
    private final IUsuarioRepositorio usuarioRepository;  // Repositorio para operaciones de usuario
    private final IRolRepositorio rolRepositorio ;  // Repositorio para operaciones de usuario

    public UsuarioService(IUsuarioRepositorio usuarioRepository,  IRolRepositorio rolRepositorio) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepositorio=rolRepositorio;
    }

    public boolean save(Usuario usuario) {
        System.out.println("---"+usuario.getRole());
        return usuarioRepository.save(usuario);  // Delegar al repositorio
    }

    public boolean delete(Long userId) {
        return usuarioRepository.delete(userId);  // Delegar al repositorio
    }

    public boolean edit(Long userId, Usuario usuario) {
        return usuarioRepository.edit(userId, usuario);  // Delegar al repositorio
    }

    public Usuario findById(Long userId) {
        return usuarioRepository.findById(userId);  // Delegar al repositorio
    }
 public List<Usuario> findByRole(String roleName) {
    List<Usuario> usuariosPorRol = new ArrayList<>();

    // Busca el rol por su nombre
    Rol rol = rolRepositorio.findByName(roleName);

    if (rol != null) {
        // Si se encuentra el rol, busca los usuarios con ese rol
        Long rolId = rol.getRoleId();
        usuariosPorRol = usuarioRepository.findByRoleId(rolId);
    }

    return usuariosPorRol;
}



    public List<Usuario> findAll() {
        return usuarioRepository.findAll();  // Delegar al repositorio
    }
}
