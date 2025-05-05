package es.upm.sos.biblioteca.cliente.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoInfo {
	private Libro libro;

	private Date fecha_prestamo;
	private Date fecha_devolucion_tope;
	private Date fecha_devolucion;

}
