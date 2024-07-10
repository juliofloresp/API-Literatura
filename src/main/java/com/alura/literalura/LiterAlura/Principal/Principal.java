package com.alura.literalura.LiterAlura.Principal;

import com.alura.literalura.LiterAlura.Model.*;
//import com.alura.literalura.LiterAlura.Model.Results;
import com.alura.literalura.LiterAlura.Repositorio.LibroRepository;
import com.alura.literalura.LiterAlura.Service.ConsumoAPI;
import com.alura.literalura.LiterAlura.Service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibro = new ArrayList<>();
    private LibroRepository repositorioLibro;
    private List<Libro> libros;


    public Principal(LibroRepository repositorio) {
        this.repositorioLibro = repositorio;
    }


    boolean encendido = true;
    int opcion;


    public void muestraElMenu() {

        while (encendido) {
            var menu = """
                        1 - Buscar libro 
                        2 - Buscar libros dependiendo por idioma
                        3 - Buscar libro por idioma
                        4 - Conocer autores encontrados por libro
                        5 - Saber que autores estaban vivos en determinado año
                        6 - Libros por Idioma guardados en BD
                        7 - Autores Vivos guardados en BD
                                      
                        0 - Salir
                        """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    buscarLibro();
                    encendido = false;
                    break;
                case 2:
                    buscarLibrosIdioma();
                    encendido = false;
                    break;
                case 3:
                    buscarLibroIdioma();
                    encendido = false;
                    break;
                case 4:
                    buscarAutorLibro();
                    encendido = false;
                    break;
                case 5:
                    autorVivo();
                    encendido = false;
                    break;
                case 6:
                    mostrarLibrosPorIdioma();
                    encendido = false;
                    break;
                case 7:
                    mostrarAutoressVivos();
                    encendido = false;
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    encendido = false;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    public void buscarLibro() {
        
            System.out.println("Escribe el nombre del Libro que deseas buscar");
            teclado.nextLine();
            var nombreLibro = teclado.nextLine();
            var promp = "?search=";
            var json = consumoApi.obtenerDatos(URL_BASE + promp + nombreLibro.replace(" ", "+"));

            List<DatosLibreria> librerias = new ArrayList<>();
            var datosLibros = conversor.obtenerDatos(json, DatosLibreria.class);

            librerias.add(datosLibros);

            System.out.println("El primer libro encontrado fue: ");

            //Lista de libros iterados de librerias
//            List<DatosLibro> datoLibro = librerias.stream()
//                    .flatMap(l -> l.libros().stream())
//                    .collect(Collectors.toList());

            List<Libro> datos = librerias.stream()
                    .flatMap(l -> l.libros().stream()
                            .map(d -> new Libro(d)))
                    .collect(Collectors.toList());

            //Organizar lista de libros comparando sus descargas, del mayor al menor, y eligiendo sólo 1.
            datos.stream()
                    .limit(1)
                    .forEach(System.out::println);

            var libro = datos.get(0);

            repositorioLibro.save(libro);


    }

    public void buscarLibrosIdioma() {
        System.out.println("Elige el idioma de tu elección");
        System.out.println("1. EN - Inglés");
        System.out.println("2. ES - Español");
        System.out.println("3. Conozco la clave de dos digitos del idioma");
        System.out.println("0. Regresar al menú principal");


        var idioma = teclado.nextInt();
        String idiom = null;

            try {
                switch (idioma) {
                    case 1:
                        elegirIdioma("en", idioma);
                        break;

                    case 2:
                        elegirIdioma("es", idioma);
                        break;

                    case 3:
                        System.out.println("Ingresa el código de dos letras del idioma elegido");
                        teclado.nextLine();
                        idiom = teclado.nextLine();
                        elegirIdioma(idiom, idioma);
                        break;

                    case 0:
                        muestraElMenu();
                    default: System.out.println("La opción introducida no es valida, ingresa un número de opción valido");
                }
            }catch (InputMismatchException ex){
                System.out.println("Ingresa un valor numerico valido");
            }

    }

    public void buscarLibroIdioma() {
        System.out.println("Elige el idioma de tu elección");
        System.out.println("1. EN - Inglés");
        System.out.println("2. ES - Español");
        System.out.println("3. Conozco la clave de dos digitos del idioma");
        System.out.println("0. Regresar al menú principal");


        var idioma = teclado.nextInt();
        String idiom = null;

        try {
            switch (idioma) {
                case 1:
                    elegirIdioma("en", idioma);
                    break;

                case 2:
                    elegirIdioma("es", idioma);
                    break;

                case 3:
                    System.out.println("Ingresa el código de dos letras del idioma elegido");
                    teclado.nextLine();
                    idiom = teclado.nextLine();
                    elegirIdioma(idiom, idioma);
                    break;

                case 0:
                    muestraElMenu();
                default: System.out.println("La opción introducida no es valida, ingresa un número de opción valido");
            }
        }catch (InputMismatchException ex){
            System.out.println("Ingresa un valor numerico valido");
        }

    }

    private void elegirIdioma(String idioma, int opcion) {

        var promp = "?languages=";
        var json = consumoApi.obtenerDatos(URL_BASE + promp + idioma);

        List<DatosLibreria> librerias = new ArrayList<>();
        var datosLibros = conversor.obtenerDatos(json, DatosLibreria.class);

        librerias.add(datosLibros);

            if (this.opcion == 2) {

                System.out.println("Los libros encontrados con ese idioma son: ");

            List<Libro> librosIdiomas = librerias.stream()
                    .flatMap(l -> l.libros().stream()
                            .map( d -> new Libro(d)))
                    .collect(Collectors.toList());

            librosIdiomas.forEach(System.out::println);

            repositorioLibro.save(librosIdiomas.get(0));

        }else if (this.opcion == 3){
            System.out.println("Los libros encontrados con ese idioma es: ");

            List<Libro> libroIdioma = librerias.stream()
                    .flatMap(l -> l.libros().stream()
                            .map( d -> new Libro(d)))
                    .collect(Collectors.toList());

            libroIdioma.stream().
                    limit(1)
                            .forEach(System.out::println);

                repositorioLibro.save(libroIdioma.get(0));
        }
    }

    private void buscarAutorLibro() {
        System.out.println("Escribe el titulo del Libro que deseas buscar");
        teclado.nextLine();
        var nombreLibro = teclado.nextLine();
        var promp = "?search=";
        var json = consumoApi.obtenerDatos(URL_BASE + promp + nombreLibro.replace(" ", "+"));

        List<DatosLibreria> librerias = new ArrayList<>();
        var datosLibros = conversor.obtenerDatos(json, DatosLibreria.class);

        librerias.add(datosLibros);

        System.out.println("Los autores encontrados para esa busqueda son : ");

        List<DatosLibro> datosLibro = librerias.stream()
                .flatMap(l -> l.libros().stream())
                .collect(Collectors.toList());

        libros = datosLibro.stream()
                .map(li -> new Libro(li))
                .collect(Collectors.toList());

        var libroEncontrado = libros.get(0);

        List<Autor> autores = datosLibro.stream()
                .flatMap(li -> li.autores().stream())
                .map(autor -> new Autor(autor))
                .collect(Collectors.toList());

        //Selecciono el primer Autor
        autores.stream().findFirst();

        autores.forEach(System.out::println);

        //Guardar en el objeto Libro el autor encontrado.
        libroEncontrado.setAutor(autores.get(0));

        repositorioLibro.save(libroEncontrado);
    }

    private void autorVivo() {
        System.out.println("Escribe el año de tu elección");
        var año = teclado.nextInt();
        var promp1 = "?author_year_start=";
        var promp2 = "&author_year_end=";
        var json = consumoApi.obtenerDatos(URL_BASE + promp1 + año + promp2 + año);

        List<DatosLibreria> librerias = new ArrayList<>();
        var datosLibros = conversor.obtenerDatos(json, DatosLibreria.class);

        librerias.add(datosLibros);

        if(año <= 2023){
            System.out.println("Los autores vivos en ese año que fueron encontrados son : ");
            List<DatosLibro> datosLibro = librerias.stream()
                    .flatMap(l -> l.libros().stream())
                    .collect(Collectors.toList());

            List<Autor> autores = datosLibro.stream()
                    .flatMap(d -> d.autores().stream())
                    .map(autor -> new Autor(autor))
                    .collect(Collectors.toList());

            autores.forEach(System.out::println);

        }else{
            System.out.println("Fechas sin registros de autores");
        }
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("Elige el idioma de tu elección");
        System.out.println("1. EN - Inglés");
        System.out.println("2. ES - Español");
        System.out.println("3. Conozco la clave de dos digitos del idioma");
        System.out.println("0. Regresar al menú principal");

        var opcion = teclado.nextInt();
        List<Libro> librosPorIdioma;
        String idiom = null;

        try {
            switch (opcion) {
                case 1:
                    librosPorIdioma = repositorioLibro.findByIdiomaContainsIgnoreCase("en");
                    if (librosPorIdioma.isEmpty()) {
                        System.out.println("No hay libros en BD con ese idioma, regresa al menu " +
                                "y elige la opción 2 o 3 para buscar nuevos libros y guardarlos en BD");
                    }else{
                        System.out.println("Los libros encontrados con ese idioma son: ");
                        librosPorIdioma.forEach(System.out::println);
                    }
                    break;

                case 2:
                    librosPorIdioma = repositorioLibro.findByIdiomaContainsIgnoreCase("es");
                    if (librosPorIdioma.isEmpty()) {
                        System.out.println("No hay libros en BD con ese idioma, regresa al menu " +
                                "y elige la opción 2 o 3 para buscar nuevos libros y guardarlos en BD");
                    }else{
                        System.out.println("Los libros encontrados con ese idioma son: ");
                        librosPorIdioma.forEach(System.out::println);
                    }
                    break;

                case 3:
                    System.out.println("Ingresa el código de dos letras del idioma elegido");
                    teclado.nextLine();
                    idiom = teclado.nextLine();
                    librosPorIdioma = repositorioLibro.findByIdiomaContainsIgnoreCase(idiom);
                    if (librosPorIdioma.isEmpty()) {
                        System.out.println("No hay libros en BD con ese idioma, regresa al menu " +
                                "y elige la opción 2 o 3 para buscar nuevos libros y guardarlos en BD");
                    }else {
                        System.out.println("Los libros encontrados con ese idioma son: ");
                        librosPorIdioma.forEach(System.out::println);
                    }
                    break;

                case 0:
                    muestraElMenu();
                default: System.out.println("La opción introducida no es valida, ingresa un número de opción valido");
            }
        }catch (InputMismatchException ex){
            System.out.println("Ingresa un valor numerico valido");
        }

        }

    private void mostrarAutoressVivos() {

        System.out.println("Escribe un año para ver que Autores estaban vivos en ese año: ");
        int año = teclado.nextInt();

        List<Autor> autorVivo = repositorioLibro.encontrarAutorVivo(año);

        if (autorVivo.isEmpty()){
            System.out.println("Fechas sin registros de autores, regresa al Menu y elige la opción 4 y 5 para" +
                    "registrar más autores");
        }else {
            System.out.println("Los autores vivos en ese año son: ");
            autorVivo.forEach(System.out::println);
        }
    }

}
