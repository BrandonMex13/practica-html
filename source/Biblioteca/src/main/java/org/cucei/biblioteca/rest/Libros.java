package org.cucei.biblioteca.rest;

import java.util.List;

import org.cucei.biblioteca.orm.modelo.Libro;
import org.cucei.biblioteca.orm.modelo.Usuario;
import org.cucei.biblioteca.orm.servicio.LibroRepositorio;
import org.cucei.biblioteca.orm.servicio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Libros {

	@Autowired
	LibroRepositorio libroRepo;
	@Autowired
	UsuarioRepositorio usuarioRepo;

	@PostMapping("nuevoLibro")
	public ModelAndView nuevolibro(ModelMap model, @RequestParam String titulo, @RequestParam String autor,
			@RequestParam String editorial, String resena, int idUsuario) {
		Usuario usuario = usuarioRepo.buscarPorId(idUsuario);
		if (usuario != null) {
			libroRepo.nuevoLibro(new Libro(titulo, autor, editorial, resena));
			model.addAttribute("usuario", usuario);
			model.addAttribute("libros", libroRepo.listaLibrosActivosYDisponibles());
			return new ModelAndView("welcome", model);
		}
		model.addAttribute("error", "Usuario no existente o contraseña erronea!");
		return new ModelAndView("index", model);
	}

	@GetMapping("eliminarLibro")
	public ModelAndView eliminarlibro(@RequestParam int idUsuario, @RequestParam int idLibro, ModelMap model) {
		System.out.println(libroRepo.eliminarLibro(idLibro));
		Usuario usuario = usuarioRepo.buscarPorId(idUsuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("libros", usuario.getLibros());
		return new ModelAndView("misLibros", model);
	}

	@GetMapping("listaLibrosActivos")
	public List<Libro> listalibrosActivos() {
		return libroRepo.listaLibrosActivos();
	}

	@PostMapping("actualizarLibros")
	public boolean actualizarlibro(Libro libro) {
		return libroRepo.actualizarLibro(libro);
	}

	@PostMapping("asignarLibro")
	public boolean asignarLibro(@RequestParam int idUsuario, @RequestParam int idLibro) {
		return libroRepo.asignarLibro(idUsuario, idLibro);
	}

	@GetMapping("asignarLibro")
	public ModelAndView apartarLibro(ModelMap model, @RequestParam int idUsuario, @RequestParam int idLibro) {
		Usuario usuario = usuarioRepo.buscarPorId(idUsuario);
		if (usuario != null) {
			libroRepo.asignarLibro(idUsuario, idLibro);
			model.addAttribute("usuario", usuario);
			model.addAttribute("libros", libroRepo.listaLibrosActivosYDisponibles());
			return new ModelAndView("welcome", model);
		} else
			model.addAttribute("error", "Usuario no existente o contraseña erronea!");
		return new ModelAndView("index", model);
	}

	@PostMapping("PostMapping")
	public boolean entregarLibro(@RequestParam int idLibro) {
		return libroRepo.entregarLibro(idLibro);
	}

}
