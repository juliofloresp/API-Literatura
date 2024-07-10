package com.alura.literalura.LiterAlura.Repositorio;

import com.alura.literalura.LiterAlura.Model.Autor;
import com.alura.literalura.LiterAlura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByIdiomaContainsIgnoreCase(String idioma);

    @Query("select a from Autor a where a.añoNacimiento <= :año AND a.añoMuerte > :año")
    List<Autor> encontrarAutorVivo(int año);

}
