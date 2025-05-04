package es.upm.sos.biblioteca.cliente;
import es.upm.sos.biblioteca.cliente.model.Libro;
import es.upm.sos.biblioteca.cliente.model.PageLibro;

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
        Libro libroValido = new Libro("Curso de programación Java", "Mariona Nadal", 
                                    "edicion", "9788441543249", "ANAYA MULTIMEDIA");
        System.out.println("#### Add libro válido (201) ####");
        System.out.println(service.addLibro(libroValido));

        // Test 400 Bad Request
        Libro libroInvalido = new Libro(null, "Autor", "edicion", "ISBN", "Editorial");
        System.out.println("#### Add libro inválido (400) ####");
        System.out.println(service.addLibro(libroInvalido));
    }

    private static void testGetLibro() {
        // Test 200 OK
        System.out.println("#### Get libro existente (200) ####");
        System.out.println(service.getLibroById(1));

        // Test 404 Not Found
        System.out.println("#### Get libro inexistente (404) ####");
        System.out.println(service.getLibroById(999));
    }

    private static void testDeleteLibro() {
        // Test 204 No Content
        System.out.println("#### Delete libro existente (204) ####");
        System.out.println(service.deleteLibro(1));

        // Test 404 Not Found
        System.out.println("#### Delete libro inexistente (404) ####");
        System.out.println(service.deleteLibro(999));
    }

    private static void testUpdateLibro() {
        Libro libroActualizado = new Libro("Nuevo título", "Nuevo autor", 
                                         "edicion", null, "Nueva editorial");
        // Test 204 No Content
        System.out.println("#### Update libro existente (204) ####");
        System.out.println(service.updateLibro(1, libroActualizado));

        // Test 404 Not Found
        System.out.println("#### Update libro inexistente (404) ####");
        System.out.println(service.updateLibro(999, libroActualizado));
    }

    private static void testGetLibrosFiltered() {
        System.out.println("#### Get libros filtrados (200) ####");
        PageLibro pagina = service.getLibrosFiltered("Java", true, 0, 10);
        System.out.println("Total libros: " + pagina.getPage().getTotalElements());
    }
}
