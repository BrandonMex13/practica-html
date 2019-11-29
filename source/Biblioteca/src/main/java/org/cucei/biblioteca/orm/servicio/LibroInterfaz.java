package org.cucei.biblioteca.orm.servicio;

import java.util.List;

import org.cucei.biblioteca.orm.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroInterfaz extends JpaRepository<Libro, Integer> {
	
	public List<Libro> findALLByActivoTrue();

	public Libro findByIdAndActivoTrue(int idLibro);

	public List<Libro> findByUsuarioNotNull();

	public List<Libro> findByUsuarioIsNullAndActivoTrue();

	
}
