package co.com.events.domain.entities;

/**
 *
 * @author Libardo Pantoja, Julio Hurtado
 */
public class Evento {


    private Long eventId;

    private String eventName;
    
    private String eventDescrip;
    
    private double eventPrice;
    
    private Long categoryId;
    
    private Location location;
    
    private int eventAforo;
    
    private Organizador organizador;
    
    

    public Evento()
    {
        // vacio
    }

    public Evento(Long eventId, String eventName, String eventDescrip, double eventPrice, Long categoryId, Location location, int aforo, Organizador organizador) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescrip = eventDescrip;
        this.eventPrice = eventPrice;
        this.categoryId = categoryId;
        this.location = location;
        this.eventAforo = aforo;
        this.organizador = organizador;
    }

    public int getAforo() {
        return eventAforo;
    }

    public void setAforo(int aforo) {
        this.eventAforo = aforo;
    }
    
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescrip() {
        return eventDescrip;
    }

    public void setEventDescrip(String eventDescrip) {
        this.eventDescrip = eventDescrip;
    }

    public double getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(double eventPrice) {
        this.eventPrice = eventPrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }
    
    

    


}