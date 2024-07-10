package com.alura.literalura.LiterAlura.Model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String nombreAutor;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;
    private String idioma;
    private Long descargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.descargas = datosLibro.descargas();
        this.idioma = datosLibro.idioma().toString().substring(1,3);
        this.nombreAutor = datosLibro.autores().toString();
        if(nombreAutor.length() > 2){
            nombreAutor = nombreAutor.substring(15).replace("]", "");;
        }
        }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

//    public void setAutores(List<Autores> autores) {
//        autores.forEach(e -> e.setLibro(this));
//        this.libros = libros;
//    }

//    public Libro(List<DatosLibro> datosLibro) {
//        this.titulo = datosLibro.get(0).titulo();
//    }

    public Autor getAutor() {
        return autor;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

//    public Idiomas getIdioma() {
//        return idioma;
//    }
//
//    public void setIdioma(Idiomas idioma) {
//        this.idioma = idioma;
//    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return  "titulo = " + titulo +
                ", autor = " + nombreAutor  +
                ", descargas = " + descargas +
                ", idioma = " + idioma;
    }

}
