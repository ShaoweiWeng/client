package es.upm.sos.biblioteca.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private Integer id;
    private String titulo;
    private String autores;
    private String edicion;
    private String isbn;
    private String editorial;
    private boolean disponible;
    private ResourceLink _links;

    public Libro(String titulo, String autores, String edicion, String isbn, String editorial) {
        this.titulo = titulo;
        this.autores = autores;
        this.edicion = edicion;
        this.isbn = isbn;
        this.editorial = editorial;
    }
}