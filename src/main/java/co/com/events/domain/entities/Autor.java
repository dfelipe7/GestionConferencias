/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.events.domain.entities;

/**
 *
 * @author Unicauca
 */
public class Autor {
    private long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String usuario;
    private long contrasenia;

    public Autor() {
    }

    public Autor(long id, String nombre, String apellido, String correo, String usuario, long contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public long getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(long contrasenia) {
        this.contrasenia = contrasenia;
    }
 
    
    
    
}
