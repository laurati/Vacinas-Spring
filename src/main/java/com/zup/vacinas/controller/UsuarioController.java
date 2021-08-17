package com.zup.vacinas.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.zup.vacinas.model.Usuario;
import com.zup.vacinas.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public List<Usuario> listar(){
		return usuarioRepository.findAll();	
	}
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<Usuario> listarPorId(@PathVariable Long usuarioId){
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping
	public ResponseEntity<Usuario> adicionar(@RequestBody @Valid Usuario usuario){
		List<Usuario> usuarioDB = usuarioRepository.findByEmail(usuario.getEmail());
		if (usuarioDB.size()>0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {
			usuarioDB = usuarioRepository.findByCpf(usuario.getCpf());
			if(usuarioDB.size()>0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}else {
				usuarioRepository.save(usuario);
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
			
		}
	}
	

	
	
	
}
