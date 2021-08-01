package com.example.inicio.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inicio.model.Tenax;

public interface TenaxRepository extends JpaRepository<Tenax, Long>{
	 List<Tenax> findBydescricao(String descricao);
	  List<Tenax> findBynomeContaining(String nome);
	
	}
	 

