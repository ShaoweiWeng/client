package es.upm.sos.biblioteca.cliente.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInfo {
	private Usuario usuario;
	private List<PrestamoInfo> prestamosActivos;
	private List<PrestamoInfo> ultimosPrestamosDevueltos;

}
