package es.upm.sos.biblioteca.cliente;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import es.upm.sos.biblioteca.cliente.model.*;

import reactor.core.publisher.Mono;

public class LibroService {
    private WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/libro")
            .build();

    public String addLibro(Libro libro) {
        try {
            return webClient.post()
                    .uri("/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(libro), Libro.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> 
                        response.bodyToMono(String.class)
                                .doOnNext(body -> System.err.println("Error 4xx: " + body))
                                .then(Mono.empty()))
                    .onStatus(HttpStatusCode::is5xxServerError, response -> 
                        response.bodyToMono(String.class)
                                .doOnNext(body -> System.err.println("Error 5xx: " + body))
                                .then(Mono.empty()))
                    .toBodilessEntity()
                    .map(response -> response.getHeaders().getLocation().toString())
                    .block();
        } catch (Exception e) {
            System.err.println("Error en addLibro: " + e.getMessage());
            return null;
        }
    }

    public Libro getLibroById(Integer id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status == HttpStatusCode.valueOf(404), 
                    response -> Mono.empty())
                .bodyToMono(Libro.class)
                .block();
    }

    public PageLibro getLibrosFiltered(String contains, boolean available, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("contains", contains)
                        .queryParam("available", available)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToMono(PageLibro.class)
                .block();
    }

    public boolean deleteLibro(Integer id) {
        return webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status == HttpStatusCode.valueOf(404), 
                    response -> Mono.empty())
                .toBodilessEntity()
                .block() != null;
    }

    public boolean updateLibro(Integer id, Libro libro) {
        return webClient.put()
                .uri("/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(libro), Libro.class)
                .retrieve()
                .onStatus(status -> status == HttpStatusCode.valueOf(404), 
                    response -> Mono.empty())
                .toBodilessEntity()
                .block() != null;
    }
}
