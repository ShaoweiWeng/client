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
			System.out.println("ID: " + prestamo.getId());
			System.out.println("Libro: " + prestamo.getLibro().getId() + ", " + prestamo.getLibro().getTitulo() + ", "
					+ prestamo.getLibro().getAutores() + ", " + prestamo.getLibro().getEdicion() + ", "
					+ prestamo.getLibro().getIsbn() + ", " + prestamo.getLibro().getEditorial());
			System.out.println("Usuario: " + prestamo.getUsuario().getId() + ", " + prestamo.getUsuario().getNombre()
					+ ", " + prestamo.getUsuario().getMatricula() + ", " + prestamo.getUsuario().getEmail() + ", "
					+ prestamo.getUsuario().getFecha_nacimiento() + ", " + prestamo.getUsuario().getSancionado_hasta());
			System.out.println("Fecha de préstamo: " + prestamo.getFecha_prestamo());
			System.out.println("Fecha de devolución tope: " + prestamo.getFecha_devolucion_tope());
			System.out.println("Fecha de devolución: " + prestamo.getFecha_devolucion());
			System.out.println("**********************");
		} else {
			System.out.println("prestamo no encontrado.");
		}
	}
}
