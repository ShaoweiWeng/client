package es.upm.sos.biblioteca.cliente;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import es.upm.sos.biblioteca.cliente.model.*;
import reactor.core.publisher.Mono;

public class LibroService {
	private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/libro").build();

	public void addLibro(Libro libro, String tipo) {
		try {
			String referencia = webClient.post().uri("").contentType(MediaType.APPLICATION_JSON).contentType(MediaType.valueOf(tipo))
					.body(Mono.just(libro), Libro.class).retrieve()
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

	public void getLibroById(int libroId) {
		Libro libro = webClient.get().uri("/" + libroId).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.bodyToMono(Libro.class).block();

		if (libro != null) {
			System.out.println("El libro con id: " + libro.getId() + " y titulo: " + libro.getTitulo());
			System.out.println("Autores: " + libro.getAutores());
			System.out.println("ISBN: " + libro.getIsbn());
			System.out.println("Edicion: " + libro.getEdicion());
			System.out.println("Editorial: " + libro.getEditorial());
			System.out.println("Disponible: " + libro.isDisponible());
			System.out.println("**********************");
		} else {
			System.out.println("Libro no encontrado.");
		}
	}

	public void getLibrosFiltered(int page, int size) {
		PageLibro libros = webClient.get().uri("?page={page}&size={size}", page, size).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.bodyToMono(PageLibro.class).block();

		System.out.println("Total de libros: " + libros.getPage().getTotalElements());
		System.out.println("Página actual: " + libros.getPage().getNumber());
		System.out.println("Tamaño página: " + libros.getPage().getSize());
		System.out.println("Número de páginas: " + libros.getPage().getTotalPages());
		System.out.println("**********************");
		System.out.println("Links");

		if (libros.get_links().getFirst() != null) {
			System.out.println("First: " + libros.get_links().getFirst().getHref());
		} else if (libros.get_links().getSelf() != null) {
			System.out.println("First: " + libros.get_links().getSelf().getHref());
		} else if (libros.get_links().getNext() != null) {
			System.out.println("Next: " + libros.get_links().getNext().getHref());
		} else if (libros.get_links().getLast() != null) {
			System.out.println("Last: " + libros.get_links().getLast().getHref());
		}

		System.out.println("**********************");
		System.out.println("Libros");
		for (Libro libro : libros.get_embedded().getLibroList()) {
			System.out.println("El libro con id: " + libro.getId() + " y titulo: " + libro.getTitulo()
					+ " se encuentra disponible en el enlace: " + libro.get_links().getSelf().getHref());
		}
	}

	public void updateLibro(int libroId, String titulo, String autores, String isbn, String edicion, String editorial) {
		Libro libro = new Libro();
		libro.setId(libroId);
		libro.setTitulo(titulo);
		libro.setAutores(autores);
		libro.setIsbn(isbn);
		libro.setEdicion(edicion);
		libro.setEditorial(editorial);

		webClient.put().uri("/{id}", libroId).contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(libro), Libro.class).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.toBodilessEntity().block();
	}

	public void deleteLibro(Integer libroId) {
		webClient.delete().uri("/{id}", libroId).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty()))
				.onStatus(HttpStatusCode::is5xxServerError,
						response -> response.bodyToMono(String.class)
								.doOnNext(body -> System.err.println("Error 5xx: " + body)).then(Mono.empty()))
				.toBodilessEntity().block();
	}
}
