package es.upm.sos.biblioteca.cliente;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import es.upm.sos.biblioteca.cliente.model.Prestamo;
import reactor.core.publisher.Mono;

public class PrestamoService {

	private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/prestamo").build();

	public void getPrestamoById(int prestamoId) {
		Prestamo prestamo = webClient.get().uri("/" + prestamoId).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.bodyToMono(Prestamo.class).block();

		if (prestamo != null) {
			System.out.println("**********Prestamo************");
			System.out.println("ID Prestamo: " + prestamo.getId());
			
			System.out.println("***********Libro***********");
			System.out.println("ID Libro: " + prestamo.getLibro().getId());
			System.out.println("Titulo: " + prestamo.getLibro().getTitulo());
			System.out.println("Autores: " + prestamo.getLibro().getAutores());
			System.out.println("Edicion: " + prestamo.getLibro().getEdicion());
			System.out.println("ISBN: " + prestamo.getLibro().getIsbn());
			System.out.println("Editorial: " + prestamo.getLibro().getEditorial());
			
			System.out.println("***********Usuario***********");
			System.out.println("ID Usuario: " + prestamo.getUsuario().getId());
			System.out.println("Nombre: " + prestamo.getUsuario().getNombre());
			System.out.println("Matricula: " + prestamo.getUsuario().getMatricula());
			System.out.println("Fecha de nacimiento: " + prestamo.getUsuario().getFecha_nacimiento());
			System.out.println("Email: " + prestamo.getUsuario().getEmail());
			System.out.println("Sancionado hasta: " + prestamo.getUsuario().getSancionado_hasta());
			
			System.out.println("***********Información préstamo***********");
			System.out.println("Fecha de préstamo: " + prestamo.getFecha_prestamo());
			System.out.println("Fecha de devolución tope: " + prestamo.getFecha_devolucion_tope());
			System.out.println("Fecha de devolución: " + prestamo.getFecha_devolucion());
			System.out.println("**********************");
		} else {
			System.out.println("prestamo no encontrado.");
		}
	}
}
