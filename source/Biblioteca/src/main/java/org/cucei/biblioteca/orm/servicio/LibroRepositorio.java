package org.cucei.biblioteca.orm.servicio;

import java.util.List;

import org.cucei.biblioteca.orm.modelo.Libro;
import org.cucei.biblioteca.orm.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroRepositorio {

	@Autowired
	LibroInterfaz li;
	@Autowired
	UsuarioInterfaz ui;

	public boolean nuevoLibro(Libro libro) {
		try {
			li.save(libro);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean eliminarLibro(int id) {
		try {
			Libro libro = li.getOne(id);
			libro.setActivo(false);
			libro.setUsuario(null);
			li.saveAndFlush(libro);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Libro> listaLibrosActivos() {
		return li.findALLByActivoTrue();
	}

	public boolean actualizarLibro(Libro libro) {
		try {
			li.save(libro);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean asignarLibro(int idUsuario, int idLibro) {
		try {
			Libro libro = li.findByIdAndActivoTrue(idLibro);
			Usuario usr = ui.findByIdAndActivoTrue(idUsuario);
			if (usr != null && libro.getUsuario() == null) {
				libro.setUsuario(ui.findByIdAndActivoTrue(idUsuario));
				li.flush();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean entregarLibro(int idLibro) {
		try {
			Libro libro = li.getOne(idLibro);
			libro.setUsuario(null);
			li.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Libro> listaLibrosReservados() {
		return li.findByUsuarioNotNull();
	}

	public List<Libro> listaLibrosActivosYDisponibles() {
		List<Libro> libro = li.findByUsuarioIsNullAndActivoTrue();
		return libro;
	}
}
