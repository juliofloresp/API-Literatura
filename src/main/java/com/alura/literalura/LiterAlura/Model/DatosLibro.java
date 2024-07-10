package com.alura.literalura.LiterAlura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.function.Predicate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias("title") String titulo,
                         @JsonAlias("authors") List<Autores> autores,
                         @JsonAlias("languages") List <String> idioma,
                         @JsonAlias("download_count") Long descargas
                         ) {
}
