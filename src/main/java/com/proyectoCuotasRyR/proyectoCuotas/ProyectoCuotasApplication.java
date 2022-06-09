package com.proyectoCuotasRyR.proyectoCuotas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;

@SpringBootApplication
public class ProyectoCuotasApplication implements CommandLineRunner {
    
    @Autowired
	private I_Usuario_Repo repo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private I_UploadFile_Service uploadFileService;

	public static void main(String[] args) {
		SpringApplication.run(ProyectoCuotasApplication.class, args);
	}
	
    public void run(String... args) throws Exception {
	        // TODO Auto-generated method stub
	      // uploadFileService.deleteAll(); 
	      // uploadFileService.init(); 
	        
	      /*  String password = "12345";
	        
	     	List<Usuario> usuarios = (List<Usuario>) repo.findAll();

		for(Usuario usuario : usuarios){
			  for(int i=0; i<2; i++) {
				String bcryptPassword = passwordEncoder.encode(password);
				System.out.println(bcryptPassword);
				usuario.setPassword(bcryptPassword);
				repo.save(usuario);
			}

		}*/
	        
	      
	       
    }

}
