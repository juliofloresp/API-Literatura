package com.alura.literalura.LiterAlura.Principal;

import com.alura.literalura.LiterAlura.Model.DatosLibro;
import com.alura.literalura.LiterAlura.Model.Libro;
import com.alura.literalura.LiterAlura.Repositorio.LibroRepository;
import com.alura.literalura.LiterAlura.Service.ConsumoAPI;
import com.alura.literalura.LiterAlura.Service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibro = new ArrayList<>();
    private LibroRepository repositorio;
    private List<Libro> libros;


    public Principal(LibroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                        1 - Buscar series 
                        2 - Buscar episodios
                        3 - Mostrar series buscadas
                                      
                        0 - Salir
                        """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    public DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del Libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        System.out.println(json);
        DatosLibro datos = conversor.obtenerDatos(json, DatosLibro.class);
        return datos;
    }

    public void buscarLibro() {
        DatosLibro datos = getDatosLibro();
        Libro libro = new Libro(datos);
        repositorio.save(libro);
        //datosSeries.add(datos);
        System.out.println(datos);
    }
}
