package org.cucei.biblioteca.orm.servicio;

import java.util.List;

import org.cucei.biblioteca.orm.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioRepositorio {

	@Autowired
	UsuarioInterfaz ui;

	public boolean nuevoUsuario(Usuario usuario) {
		try {
			ui.save(usuario);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean eliminarUsuario(int id) {
		try {
			Usuario usr = ui.getOne(id);
			usr.setActivo(false);
			ui.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Usuario> ListaUsuariosActivos() {
		return ui.findALLByActivoTrue();
	}

	public Usuario validarLogin(String email, String contrasena) {
		return ui.findByEmailAndContrasenaAndActivoTrue(email, contrasena);
	}

	public boolean actualizarUsuario(Usuario usuario) {
		try {
			ui.saveAndFlush(usuario);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Usuario buscarPorId(int usuarioId) {
		return ui.getOne(usuarioId);
	}
}
