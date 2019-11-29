package org.cucei.biblioteca.rest;

import java.util.List;

import org.cucei.biblioteca.orm.modelo.Libro;
import org.cucei.biblioteca.orm.modelo.Usuario;
import org.cucei.biblioteca.orm.servicio.LibroRepositorio;
import org.cucei.biblioteca.orm.servicio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Usuarios {

	@Autowired
	UsuarioRepositorio usuarioRepo;
	@Autowired
	LibroRepositorio libroRepo;

	@GetMapping("nuevoUsuario")
	public ModelAndView nuevoUsuario() {
		return new ModelAndView("registrarUsuario");
	}

	@PostMapping("nuevoUsuario")
	public ModelAndView nuevoUsuarioSubmit(@RequestParam String nombre, @RequestParam String apellido,
			@RequestParam String fechaNacimiento, String email, String contrasena, ModelMap model) {
		if(usuarioRepo.nuevoUsuario(new Usuario(nombre, apellido, fechaNacimiento, email, contrasena))) {
		model.addAttribute("error", "Usuario creado con Exito");
		}else {
			model.addAttribute("error", "No se pudo registrar usuario, verifique la informacion e intente de nuevo.");
		}
		return new ModelAndView("index");
	}

	@DeleteMapping("eliminarUsuario")
	public boolean eliminarUsuario(@RequestParam int id) {
		return usuarioRepo.eliminarUsuario(id);
	}

	@GetMapping("listaUsuariosActivos")
	public @ResponseBody List<Usuario> listaUsuariosActivos() {
		return usuarioRepo.ListaUsuariosActivos();
	}

	@PostMapping("/validarLogin")
	public ModelAndView validarLogin(@RequestParam String email, @RequestParam String contrasena, ModelMap model) {
		Usuario usuario = usuarioRepo.validarLogin(email, contrasena);
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
			model.addAttribute("libros", libroRepo.listaLibrosActivos());
			return new ModelAndView("welcome", model);
		} else
			model.addAttribute("error", "Usuario no existente o contraseña erronea!");
		return new ModelAndView("index", model);
	}

	@PostMapping("actualizarUsuario")
	public ModelAndView actualizarUsuario(Usuario usuario, ModelMap model) {
		usuarioRepo.actualizarUsuario(usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("libros", libroRepo.listaLibrosActivos());
		return new ModelAndView("misLibros", model);
	}

	@GetMapping("/librosReservados")
	public ModelAndView librosReservaods(ModelMap model, @RequestParam int usuarioId) {
		model.remove("libros");
		List<Libro> libros = libroRepo.listaLibrosReservados();
		model.addAttribute("libros", libros);
		model.addAttribute("usuario", usuarioRepo.buscarPorId(usuarioId));
		return new ModelAndView("librosReservados", model);
	}

	@GetMapping("/addLibro")
	public ModelAndView addLibro(ModelMap model, @RequestParam int usuarioId) {
		Usuario usuario = usuarioRepo.buscarPorId(usuarioId);
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
			return new ModelAndView("addLibro", model);
		} else {
			model.addAttribute("error", "Session terminada.");
			return new ModelAndView("index", model);
		}
	}

	@GetMapping("/misLibros")
	public ModelAndView misLibros(ModelMap model, @RequestParam int usuarioId) {
		Usuario usuario = usuarioRepo.buscarPorId(usuarioId);
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
			model.addAttribute("libros", usuario.getLibros());
			return new ModelAndView("misLibros", model);
		} else {
			model.addAttribute("error", "Session terminada.");
			return new ModelAndView("index", model);
		}
	}

	@GetMapping("/misDatos")
	public ModelAndView misDatos(ModelMap model, @RequestParam int usuarioId) {
		Usuario usuario = usuarioRepo.buscarPorId(usuarioId);
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
			return new ModelAndView("misDatos", model);
		} else {
			model.addAttribute("error", "Session terminada.");
			return new ModelAndView("index", model);
		}
	}

	@GetMapping("/welcome")
	public ModelAndView welcome(ModelMap model, @RequestParam int usuarioId) {
		Usuario usuario = usuarioRepo.buscarPorId(usuarioId);
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
			model.addAttribute("libros", libroRepo.listaLibrosActivosYDisponibles());
			return new ModelAndView("welcome", model);
		} else
			model.addAttribute("error", "Usuario no existente o contraseña erronea!");
		return new ModelAndView("index", model);
	}

}
