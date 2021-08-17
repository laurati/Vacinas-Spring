package com.zup.vacinas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zup.vacinas.model.Usuario;
import com.zup.vacinas.model.VacinaDto;
import com.zup.vacinas.model.Vacinacao;
import com.zup.vacinas.repository.UsuarioRepository;
import com.zup.vacinas.repository.VacinacaoRepository;

@RestController
@RequestMapping("/vacinas")
public class VacinacaoController {
	
	@Autowired
	private VacinacaoRepository vacinacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public List<Vacinacao> listar(){
		return vacinacaoRepository.findAll();	
	}
	
	@PostMapping
	public ResponseEntity<VacinaDto> vacinar(@RequestBody VacinaDto vacinaDto) {
		List<Usuario> usuarioDB = usuarioRepository.findByEmail(vacinaDto.getEmail());
		if(usuarioDB.size() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {
			if(vacinaDto.getNomeVacina().isEmpty() || vacinaDto.getDataVacinaca().isEmpty() || vacinaDto.getEmail().isEmpty() ) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}else {
				Vacinacao vacina = new Vacinacao();
				vacina.setNomeVacina(vacinaDto.getNomeVacina());
				vacina.setDataVacinaca(vacinaDto.getDataVacinaca());
				vacina.setIdUsuario(usuarioDB.get(0).getId());
				
				vacinacaoRepository.save(vacina);
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
			
			
		}
				
	}
	
	
}
