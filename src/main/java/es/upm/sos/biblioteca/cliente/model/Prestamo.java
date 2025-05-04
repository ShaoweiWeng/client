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
public class Prestamo {
	private Integer id;
	private Libro libro;
	private Usuario usuario;
	private Date fecha_prestamo;
	private Date fecha_devolucion_tope;
	private Date fecha_devolucion;

	public Prestamo(Libro libro, Usuario usuario, Date fecha_prestamo, Date fecha_devolucion_tope) {
		this.libro = libro;
		this.usuario = usuario;
		this.fecha_prestamo = fecha_prestamo;
		this.fecha_devolucion_tope = fecha_devolucion_tope;
	}
}
