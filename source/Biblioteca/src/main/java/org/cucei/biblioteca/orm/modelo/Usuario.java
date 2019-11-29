package org.cucei.biblioteca.orm.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Usuario {

	public Usuario(String nombre, String apellido, String fechaNacimiento, String email, String contrasena) {
		super();
		this.email = email;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.activo = true;
	}

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int id;
	@Column(unique= true)
	private String email;
	private String contrasena;
	private String nombre;
	private String apellido;
	private String fechaNacimiento;
	private boolean activo = true;
	@OneToMany(mappedBy = "usuario")
	private List<Libro> libros;

}
