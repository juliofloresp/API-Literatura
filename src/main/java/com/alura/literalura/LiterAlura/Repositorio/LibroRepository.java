package com.alura.literalura.LiterAlura.Repositorio;

import com.alura.literalura.LiterAlura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro,Long> {
}
