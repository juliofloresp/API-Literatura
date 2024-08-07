package com.alura.literalura.LiterAlura;


import com.alura.literalura.LiterAlura.Principal.Principal;
import com.alura.literalura.LiterAlura.Repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;

//	@Autowired
//	private AutorRepository repositoryAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		//Principal principalAutor = new Principal(repositoryAutor);
		principal.muestraElMenu();
	}
}