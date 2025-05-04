package es.upm.sos.biblioteca.cliente;

import es.upm.sos.biblioteca.cliente.model.Libro;

public class Cliente {
	static LibroService service = new LibroService();

	public static void main(String[] args) {
		testAddLibro();
		testGetLibro();
		testDeleteLibro();
		testUpdateLibro();
		testGetLibrosFiltered();
	}

	private static void testAddLibro() {
		// Test 201 Created
		Libro libroValido1 = new Libro("Curso de programación Java", "Mariona Nadal", "3ª edición", "9788441543249",
				"ANAYA MULTIMEDIA");
		Libro libroValido2 = new Libro("Libro2", "Autor2", "2ª edición", "9788441542222", "Editorial2");
		System.out.println("#### Add libro válido (201) ####");
		service.addLibro(libroValido1,null);
		service.addLibro(libroValido2,null);
		System.out.println();

		// Test 400 Bad Request
		Libro libroInvalido = new Libro(null, "Autor", "edicion", "ISBN", "Editorial");
		System.out.println("#### Add libro inválido (400) ####");
		service.addLibro(libroInvalido,null);
		System.out.println();

		// Test 409 Conflict
		System.out.println("#### Add libro duplicado (409) ####");
		service.addLibro(libroValido1,null);
		System.out.println();

		// Test 415 Unsupported Media Type
		System.out.println("#### Add libro con media type no soportado (415) ####");
		service.addLibro(libroValido2, "application/atom+xml");
		System.out.println();
	}

	private static void testGetLibro() {
		// Test 200 OK
		System.out.println("#### Get libro existente (200) ####");
		service.getLibroById(1);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Get libro inexistente (404) ####");
		service.getLibroById(404);
		System.out.println();
	}

	private static void testDeleteLibro() {
		// Test 204 No Content
		System.out.println("#### Delete libro existente (204) ####");
		service.deleteLibro(2);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Delete libro inexistente (404) ####");
		service.deleteLibro(404);
		System.out.println();
	}

	private static void testUpdateLibro() {
		// Test 204 No Content
		System.out.println("#### Update libro existente (204) ####");
		service.updateLibro(1, "Nuevo titulo", "Nuevo autor", "Nueva edicion", "ISBN", "Nueva editorial");
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Update libro inexistente (404) ####");
		service.updateLibro(999, "Nuevo titulo", "Nuevo autor", "Nueva edicion", "ISBN", "Nueva editorial");
		System.out.println();
	}

	private static void testGetLibrosFiltered() {
		System.out.println("#### Get libros filtrados (200) ####");
		service.getLibrosFiltered(0, 2);
		System.out.println();
	}
}
