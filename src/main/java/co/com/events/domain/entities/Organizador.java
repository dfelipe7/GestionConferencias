package co.com.events.domain.entities;

/**
 *
 * @author Libardo Pantoja, Julio A. Hurtado
 */
class Organizador {
    private Long userId;
    private String firstName;
    private String lastName;
    //Demas atributos
    
    
    
    public Organizador()
    {}

    public Organizador(Long userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    

}


