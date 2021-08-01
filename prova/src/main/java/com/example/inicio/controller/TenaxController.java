package com.example.inicio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inicio.model.Tenax;
import com.example.inicio.repository.TenaxRepository;

@CrossOrigin(origins = "http://localhost:3306")
@RestController
@RequestMapping("/api")

public class TenaxController {
	@Autowired
	TenaxRepository tenaxRepository;

	@GetMapping("/Tenax")
	public ResponseEntity<List<Tenax>> getAllTenax(@RequestParam(required = false) String nome) {
		try {
			List<Tenax> Tenax = new ArrayList<Tenax>();

			if (nome == null)
				tenaxRepository.findAll().forEach(Tenax::add);
			else
				tenaxRepository.findBynomeContaining(nome).forEach(Tenax::add);

			if (Tenax.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Tenax, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/Get/{id}")
	public ResponseEntity<Tenax> getTenaxById(@PathVariable("id") long id) {
		Optional<Tenax> tenaxData = tenaxRepository.findById(id);

		if (tenaxData.isPresent()) {
			return new ResponseEntity<>(tenaxData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteTenax(@PathVariable("id") long id) {
		try {
			tenaxRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Tenax> updateTenax(@PathVariable("id") long id, Tenax tenax) {
		Optional<Tenax> tenaxData = tenaxRepository.findById(id);

		if (tenaxData.isPresent()) {
			Tenax _tenax = tenaxData.get();
			_tenax.setNome(tenax.getNome());
			_tenax.setDescricao(tenax.getDescricao());

			return new ResponseEntity<>(tenaxRepository.save(_tenax), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<Tenax> createTenax(Tenax tenax) {
		try {
			Tenax _tenax = tenaxRepository.save(new Tenax(tenax.getNome(), tenax.getDescricao()));
			return new ResponseEntity<>(_tenax, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
