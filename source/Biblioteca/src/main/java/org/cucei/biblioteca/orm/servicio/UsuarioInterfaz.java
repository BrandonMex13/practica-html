package org.cucei.biblioteca.orm.servicio;

import java.util.List;

import org.cucei.biblioteca.orm.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioInterfaz extends JpaRepository<Usuario, Integer> {
	
	public List<Usuario> findALLByActivoTrue();

	public Usuario findByEmailAndContrasenaAndActivoTrue(String email, String contrasena);
	
	public Usuario findByIdAndActivoTrue(int id);

	
}
