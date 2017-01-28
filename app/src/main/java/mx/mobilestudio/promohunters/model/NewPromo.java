package mx.mobilestudio.promohunters.model;

/**
 * Created by mobile on 12/17/16.
 */

public class NewPromo {
    private String title;
    private String categoria;
    private String descripcion;
    private float precio;
    private String link;


    private String tienda;

    public NewPromo() {


    }


    public NewPromo(String categoria, String descripcion, String link, float precio, String title,String tienda) {
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.link = link;
        this.precio = precio;
        this.title = title;
        this.tienda = tienda;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }


}
