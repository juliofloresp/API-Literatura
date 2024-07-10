package com.alura.literalura.LiterAlura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private int añoNacimiento;
    private int añoMuerte;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Libro> libros;

public Autor() {}

    public Autor(Autores datosAutor) {
        this.nombre = datosAutor.autor();
        this.añoNacimiento = datosAutor.añoNacimiento();
        this.añoMuerte = datosAutor.añoMuerte();
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setAutor(this));
        this.libros = libros;
    }

    public void autorNombre(String nombre) {this.nombre = nombre;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getAñoNacimiento() {
        return añoNacimiento;
    }

    public void setAñoNacimiento(int añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    public int getAñoMuerte() {
        return añoMuerte;
    }

    public void setAñoMuerte(int añoMuerte) {
        this.añoMuerte = añoMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    @Override
    public String toString() {
        return "autor = " + nombre +
                ", año de Nacimiento = " + añoNacimiento +
                ", año de Muerte = " + añoMuerte;
    }
}
