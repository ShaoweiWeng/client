package es.upm.sos.biblioteca.cliente;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import es.upm.sos.biblioteca.cliente.model.DTO;
import es.upm.sos.biblioteca.cliente.model.PagePrestamo;
import es.upm.sos.biblioteca.cliente.model.PageUsuario;
import es.upm.sos.biblioteca.cliente.model.Prestamo;
import es.upm.sos.biblioteca.cliente.model.Usuario;
import reactor.core.publisher.Mono;

public class UsuarioService {

	private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/usuario").build();

	public void addUsuario(Usuario usuario) {
		try {
			String referencia = webClient.post().uri("").contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(usuario), Usuario.class).retrieve()
					.onStatus(HttpStatusCode::is4xxClientError,
							response -> response.bodyToMono(String.class)
									.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
					.onStatus(HttpStatusCode::is5xxServerError,
							response -> response.bodyToMono(String.class)
									.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
					.toBodilessEntity().map(response -> {
						if (response.getHeaders().getLocation() != null) {
							return response.getHeaders().getLocation().toString();
						} else {
							throw new RuntimeException("No se recibió una URL en la cabecera Location");
						}
					}).block();
			if (referencia != null) {
				System.out.println(referencia);
			}
		} catch (RuntimeException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void getUsuarioById(int usuarioId) {
		Usuario usuario = webClient.get().uri("/" + usuarioId).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.bodyToMono(Usuario.class).block();

		if (usuario != null) {
			System.out.println("Usuario con id: " + usuario.getId() + " y nombre: " + usuario.getNombre());
			System.out.println("Matrícula: " + usuario.getMatricula());
			System.out.println("Fecha de nacimiento: " + usuario.getFecha_nacimiento());
			System.out.println("Email: " + usuario.getEmail());
			System.out.println("**********************");
		} else {
			System.out.println("Usuario no encontrado.");
		}
	}

	public void getUsuariosFiltered(int page, int size) {
		PageUsuario usuarios = webClient.get().uri("?page={page}&size={size}", page, size).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.bodyToMono(PageUsuario.class).block();

		System.out.println("Total de usuarios: " + usuarios.getPage().getTotalElements());
		System.out.println("Página actual: " + usuarios.getPage().getNumber());
		System.out.println("Tamaño página: " + usuarios.getPage().getSize());
		System.out.println("Número de páginas: " + usuarios.getPage().getTotalPages());
		System.out.println("**********************");
		System.out.println("Links");

		if (usuarios.get_links().getFirst() != null) {
			System.out.println("First: " + usuarios.get_links().getFirst().getHref());
		}
		if (usuarios.get_links().getSelf() != null) {
			System.out.println("Self: " + usuarios.get_links().getSelf().getHref());
		}
		if (usuarios.get_links().getNext() != null) {
			System.out.println("Next: " + usuarios.get_links().getNext().getHref());
		}
		if (usuarios.get_links().getLast() != null) {
			System.out.println("Last: " + usuarios.get_links().getLast().getHref());
		}

		System.out.println("**********************");

	}

	public void deleteUsuario(Integer usuarioId) {
		webClient.delete().uri("/{id}", usuarioId).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.toBodilessEntity().block();
	}

	public void updateUsuario(int usuarioId, String nombre, String email) {
		Usuario usuario = new Usuario();
		usuario.setId(usuarioId);
		usuario.setNombre(nombre);
		usuario.setEmail(email);

		webClient.put().uri("/{id}", usuarioId).contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(usuario), Usuario.class).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.toBodilessEntity().block();
	}

	public void prestamo(int idUsuario, int idLibro) {
		DTO prestamoDTO = new DTO();
		prestamoDTO.setUsuario_id(idUsuario);
		prestamoDTO.setLibro_id(idLibro);
		try {
			String referencia = webClient.post().uri("/prestamo").contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(prestamoDTO), DTO.class).retrieve()
					.onStatus(HttpStatusCode::is4xxClientError,
							response -> response.bodyToMono(String.class)
									.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
					.onStatus(HttpStatusCode::is5xxServerError,
							response -> response.bodyToMono(String.class)
									.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
					.toBodilessEntity().map(response -> {
						if (response.getHeaders().getLocation() != null) {
							return response.getHeaders().getLocation().toString();
						} else {
							throw new RuntimeException("");
						}
					}).block();
			if (referencia != null) {
				System.out.println("Préstamo creado en: " + referencia);
			}
		} catch (RuntimeException e) {
			System.err.println("Error al realizar el préstamo: " + e.getMessage());
		}
	}

	public void devolucion(int prestamoId) {
		webClient.put().uri("/prestamo/{id}", prestamoId).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.toBodilessEntity().map(response -> response.getStatusCode().is2xxSuccessful()).block();
	}

	public void ampliarPrestamo(int prestamoId) {
		webClient.put().uri("/prestamo/{id}/ampliar", prestamoId).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.toBodilessEntity().map(response -> response.getStatusCode().is2xxSuccessful()).block();
	}

	public void getPrestamosActuales(int usuarioId, Date date, int page, int size) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/{id}/prestamo").queryParam("page", page)
				.queryParam("size", size);

		if (date != null) {
			uriBuilder.queryParam("date", new SimpleDateFormat("yyyy-MM-dd").format(date));
		}

		PagePrestamo prestamos = webClient.get().uri(uriBuilder.build(usuarioId).toString()).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.bodyToMono(PagePrestamo.class).block();

		if (prestamos == null || prestamos.get_embedded() == null
				|| prestamos.get_embedded().getPrestamoList() == null) {
			return;
		}

		System.out.println("Total de préstamos actuales: " + prestamos.getPage().getTotalElements());
		System.out.println("Página actual: " + prestamos.getPage().getNumber());
		System.out.println("Tamaño de página: " + prestamos.getPage().getSize());
		System.out.println("Número de páginas: " + prestamos.getPage().getTotalPages());
		System.out.println("**********************");
		System.out.println("Links");

		if (prestamos.get_links().getFirst() != null) {
			System.out.println("First: " + prestamos.get_links().getFirst().getHref());
		}
		if (prestamos.get_links().getSelf() != null) {
			System.out.println("Self: " + prestamos.get_links().getSelf().getHref());
		}
		if (prestamos.get_links().getNext() != null) {
			System.out.println("Next: " + prestamos.get_links().getNext().getHref());
		}
		if (prestamos.get_links().getLast() != null) {
			System.out.println("Last: " + prestamos.get_links().getLast().getHref());
		}

		System.out.println("**********************");
		System.out.println("Préstamos");
		for (Prestamo prestamo : prestamos.get_embedded().getPrestamoList()) {
			System.out.println("Préstamo ID: " + prestamo.getId() + ", Libro: " + prestamo.getLibro().getTitulo()
					+ ", Usuario: " + prestamo.getUsuario().getNombre() + ", Fecha préstamo: "
					+ prestamo.getFecha_prestamo() + ", Fecha devolución tope: " + prestamo.getFecha_devolucion_tope());
		}
	}

	public void getHistorialPrestamos(int usuarioId, int page, int size) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/{id}/prestamo/historial")
				.queryParam("page", page).queryParam("size", size);

		PagePrestamo prestamos = webClient.get().uri(uriBuilder.build(usuarioId).toString()).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.bodyToMono(PagePrestamo.class).block();

		if (prestamos == null || prestamos.get_embedded() == null
				|| prestamos.get_embedded().getPrestamoList() == null) {
			return;
		}

		System.out.println("Total de préstamos devueltos: " + prestamos.getPage().getTotalElements());
		System.out.println("Página actual: " + prestamos.getPage().getNumber());
		System.out.println("Tamaño de página: " + prestamos.getPage().getSize());
		System.out.println("Número de páginas: " + prestamos.getPage().getTotalPages());
		System.out.println("**********************");
		System.out.println("Links");

		if (prestamos.get_links().getFirst() != null) {
			System.out.println("First: " + prestamos.get_links().getFirst().getHref());
		}
		if (prestamos.get_links().getSelf() != null) {
			System.out.println("Self: " + prestamos.get_links().getSelf().getHref());
		}
		if (prestamos.get_links().getNext() != null) {
			System.out.println("Next: " + prestamos.get_links().getNext().getHref());
		}
		if (prestamos.get_links().getLast() != null) {
			System.out.println("Last: " + prestamos.get_links().getLast().getHref());
		}

		System.out.println("**********************");
		System.out.println("Historial de préstamos");
		for (Prestamo prestamo : prestamos.get_embedded().getPrestamoList()) {
			System.out.println("Préstamo ID: " + prestamo.getId() + ", Libro: " + prestamo.getLibro().getTitulo()
					+ ", Usuario: " + prestamo.getUsuario().getNombre() + ", Fecha préstamo: "
					+ prestamo.getFecha_prestamo() + ", Fecha devolución: " + prestamo.getFecha_devolucion());
		}
	}

	public void getUsuarioInfo(int usuarioId) {
		getUsuarioById(usuarioId);

		System.out.println("**********************");
		System.out.println("Préstamos activos:");
		getPrestamosActuales(usuarioId, null, 0, 5);

		System.out.println("**********************");
		System.out.println("Historial de préstamos:");
		getHistorialPrestamos(usuarioId, 0, 5);
	}
}
