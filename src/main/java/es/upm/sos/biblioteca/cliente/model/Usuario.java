package es.upm.sos.biblioteca.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	private Integer id;
	private String nombre;
	private String matricula;
	private Date fecha_nacimiento;
	private String email;
	private String sancionado_hasta;
	private ResourceLink _links;

	public Usuario(String nombre, String matricula, Date fecha_nacimiento, String email) {
		this.nombre = nombre;
		this.matricula = matricula;
		this.fecha_nacimiento = fecha_nacimiento;
		this.email = email;
	}
}
