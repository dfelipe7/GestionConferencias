/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.com.events.domain.access;

import co.com.events.domain.entities.Rol;
import co.com.events.domain.entities.Usuario;
import java.util.List;

public interface IUsuarioRepositorio {
    boolean save(Usuario usuario);
    Usuario findById(Long id);
    List<Usuario> findAll();
    boolean delete(Long id);
    boolean edit(Long id, Usuario usuario);
    List<Usuario> findByRoleId(Long roleId); // Método para buscar usuarios por roleId
    Usuario findByUsername(String username);
}
