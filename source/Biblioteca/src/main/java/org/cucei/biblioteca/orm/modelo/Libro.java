package org.cucei.biblioteca.orm.modelo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Libro {

	public Libro(String titulo, String autor, String editorial, String resena) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.resena = resena;
		this.activo = true;
		this.fechaCaptura = Calendar.getInstance().getTime();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titulo;
	private String autor;
	private String editorial;
	@Column(length = 1500)
	private String resena;
	private boolean activo;
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaCaptura;
	@JsonIgnore
	@OneToOne
	private Usuario usuario;

}
