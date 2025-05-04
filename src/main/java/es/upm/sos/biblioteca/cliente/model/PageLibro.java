package es.upm.sos.biblioteca.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageLibro {
    private Libros _embedded;
    private PageLinks _links;
    private PageMetadata page;
}