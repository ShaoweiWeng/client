package es.upm.sos.biblioteca.cliente;

import java.sql.Date;

import es.upm.sos.biblioteca.cliente.model.Libro;
import es.upm.sos.biblioteca.cliente.model.Usuario;

public class Cliente {
	static LibroService libroService = new LibroService();
	static UsuarioService usuarioService = new UsuarioService();
	static PrestamoService prestamoService = new PrestamoService();

	public static void main(String[] args) {
		testAddLibro();
		testGetLibro();
		testDeleteLibro();
		testUpdateLibro();
		testGetLibrosFiltered();
		testAddUsuario();
		testGetUsuarioId();
		testGetUsuarios();
		testDeleteUsuario();
		testUpdateUsuario();
		testPrestamo();
		testDevolucion();
		testAmpliarPrestamo();
		testGetPrestamosActuales();
		testGetHistorialPrestamos();
		testGetUsuarioInfo();
		testGetPrestamoById();
	}

	private static void testAddLibro() {
		// Test 201 Created
		Libro libroValido1 = new Libro("Libro1", "Autor1", "1ª edición", "9788441541111", "Editorial1");
		Libro libroValido2 = new Libro("Libro2", "Autor2", "2ª edición", "9788441542222", "Editorial2");
		Libro libroValido3 = new Libro("Libro3", "Autor3", "3ª edición", "9788441543333", "Editorial3");
		Libro libroValido4 = new Libro("Libro4", "Autor4", "4ª edición", "9788441544444", "Editorial4");
		System.out.println("#### Add libro válido (201) ####");
		libroService.addLibro(libroValido1);
		libroService.addLibro(libroValido2);
		libroService.addLibro(libroValido3);
		libroService.addLibro(libroValido4);
		System.out.println();

		// Test 400 Bad Request
		Libro libroInvalido = new Libro(null, "Autor", "edicion", "ISBN", "Editorial");
		System.out.println("#### Add libro inválido (400) ####");
		libroService.addLibro(libroInvalido);
		System.out.println();

		// Test 409 Conflict
		System.out.println("#### Add libro duplicado (409) ####");
		libroService.addLibro(libroValido1);
		System.out.println();
	}

	private static void testGetLibro() {
		// Test 200 OK
		System.out.println("#### Get libro existente (200) ####");
		libroService.getLibroById(1);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Get libro inexistente (404) ####");
		libroService.getLibroById(999);
		System.out.println();
	}

	private static void testDeleteLibro() {
		// Test 204 No Content
		System.out.println("#### Delete libro existente (204) ####");
		libroService.deleteLibro(3);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Delete libro inexistente (404) ####");
		libroService.deleteLibro(999);
		System.out.println();
	}

	private static void testUpdateLibro() {
		// Test 204 No Content
		System.out.println("#### Update libro existente (204) ####");
		libroService.updateLibro(2, "Nuevo titulo", "Nuevo autor", "Nueva edicion", "ISBN", "Nueva editorial");
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Update libro inexistente (404) ####");
		libroService.updateLibro(999, "Nuevo titulo", "Nuevo autor", "Nueva edicion", "ISBN", "Nueva editorial");
		System.out.println();
	}

	private static void testGetLibrosFiltered() {
		System.out.println("#### Get libros filtrados (200) ####");
		libroService.getLibrosFiltered(0, 2);
		System.out.println();
	}

	private static void testAddUsuario() {
		// Test 201 Created
		Usuario usuarioValido1 = new Usuario("User1", "UPM11111", Date.valueOf("1992-05-21"), "user1@example.com");
		Usuario usuarioValido2 = new Usuario("User2", "UPM22222", Date.valueOf("1992-05-21"), "user2@example.com");
		Usuario usuarioValido3 = new Usuario("User3", "UPM33333", Date.valueOf("1992-05-21"), "user3@example.com");

		System.out.println("#### Add usuario válido (201) ####");
		usuarioService.addUsuario(usuarioValido1);
		usuarioService.addUsuario(usuarioValido2);
		usuarioService.addUsuario(usuarioValido3);
		System.out.println();

		// Test 400 Bad Request
		Usuario usuarioInvalido = new Usuario(null, "UPM00000", Date.valueOf("2000-01-01"), "correo@example.com");

		System.out.println("#### Add usuario inválido (400) ####");
		usuarioService.addUsuario(usuarioInvalido);
		System.out.println();

		// Test 409 Conflict
		System.out.println("#### Add usuario duplicado (409) ####");
		usuarioService.addUsuario(usuarioValido1);
		System.out.println();
	}

	private static void testGetUsuarioId() {
		// Test 200 OK
		System.out.println("#### Get usuario existente (200) ####");
		usuarioService.getUsuarioById(1);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Get usuario inexistente (404) ####");
		usuarioService.getUsuarioById(999);
		System.out.println();
	}

	private static void testGetUsuarios() {
		// Test 200 OK
		System.out.println("#### Get todos los usuarios (200) ####");
		usuarioService.getUsuariosFiltered(0, 10);
		usuarioService.getUsuariosFiltered(1, 10);
		System.out.println();
	}

	private static void testDeleteUsuario() {
		// Test 204 OK
		System.out.println("#### Delete usuario existente (204) ####");
		usuarioService.deleteUsuario(3);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Delete usuario inexistente (404) ####");
		usuarioService.deleteUsuario(999);
		System.out.println();
	}

	private static void testUpdateUsuario() {
		// Test 204 No Content
		System.out.println("#### Update usuario existente (204) ####");
		usuarioService.updateUsuario(2, "NewUser", "newUser@example.com");
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Update usuario inexistente (404) ####");
		usuarioService.updateUsuario(999, "Nombre Desconocido", "correo@example.com");
		System.out.println();
	}

	private static void testPrestamo() {
		// Test 201 OK
		System.out.println("#### Add Prestamo (201) ####");
		usuarioService.prestamo(1, 1);
		usuarioService.prestamo(1, 2);
		System.out.println();

		// Test 400 Bad Request
		System.out.println("#### Add Prestamo inválido (400) ####");
		usuarioService.prestamo(1, 999);
		System.out.println();

		// Test 403 Forbidden
		System.out.println("#### Add Prestamo no disponible (403) ####");
		usuarioService.prestamo(2, 1);

		// Test 409 Conflict
		System.out.println("#### Add Prestamo duplicado (409) ####");
		usuarioService.prestamo(1, 1);
		System.out.println();
	}

	private static void testDevolucion() {
		// Test 204 OK
		System.out.println("#### Devolución de préstamo existente (204) ####");
		usuarioService.devolucion(2);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Devolución de préstamo inexistente (404) ####");
		usuarioService.devolucion(999);
		System.out.println();
	}

	private static void testAmpliarPrestamo() {
		// Test 204 OK
		System.out.println("#### Ampliar préstamo existente (204) ####");
		usuarioService.ampliarPrestamo(1);
		System.out.println();

		// Test 400 Bad Request
		System.out.println("#### Ampliar préstamo inválido (400) ####");
		usuarioService.ampliarPrestamo(2);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Ampliar préstamo inexistente (404) ####");
		usuarioService.ampliarPrestamo(999);
		System.out.println();
	}

	private static void testGetPrestamosActuales() {
		// Test 200 OK
		System.out.println("#### Obtener préstamos actuales de usuario existente (200) ####");
		usuarioService.getPrestamosActuales(1, null, 0, 5);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Obtener préstamos de usuario inexistente (404) ####");
		usuarioService.getPrestamosActuales(999, null, 0, 5);
		System.out.println();
	}

	private static void testGetHistorialPrestamos() {
		// Test 200 OK
		System.out.println("#### Obtener historial de préstamos de usuario existente (200) ####");
		usuarioService.getHistorialPrestamos(1, 0, 5);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Obtener historial de préstamos de usuario inexistente (404) ####");
		usuarioService.getHistorialPrestamos(999, 0, 5);
		System.out.println();
	}

	private static void testGetUsuarioInfo() {
		// Test 200 OK
		System.out.println("#### Obtener información de usuario existente (200) ####");
		usuarioService.getUsuarioInfo(1);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Obtener información de usuario inexistente (404) ####");
		usuarioService.getUsuarioInfo(999);
		System.out.println();
	}

	private static void testGetPrestamoById() {
		// Test 200 OK
		System.out.println("#### Get prestamo existente (200) ####");
		prestamoService.getPrestamoById(1);
		System.out.println();

		// Test 404 Not Found
		System.out.println("#### Get prestamo inexistente (404) ####");
		prestamoService.getPrestamoById(404);
		System.out.println();
	}
}
