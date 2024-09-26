package pe.edu.cibertec.adopta_patitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.adopta_patitas.dto.LoginRequestDTO;
import pe.edu.cibertec.adopta_patitas.dto.LoginResponseDTO;
import pe.edu.cibertec.adopta_patitas.viewmodel.LoginModel;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/inicio")
    public String inicio (Model model){
        LoginModel loginModel =  new LoginModel("00", "", "");
        model.addAttribute("loginModel", loginModel);
        return "inicio";
    }

    @PostMapping("/autenticar")
    public String autenticar (@RequestParam("tipoDocumento") String tipoDocumento,
                              @RequestParam("numeroDocumento") String numeroDocumento,
                              @RequestParam("password") String password,
                              Model model) {

        // Validar campos de entrada
        if(tipoDocumento == null || tipoDocumento.trim().isEmpty() ||
           numeroDocumento == null || numeroDocumento.trim().isEmpty() ||
           password == null || password.trim().isEmpty())
        {
            LoginModel loginModel = new LoginModel("01", "Error: Debe llenar el formulario con sus credenciales", "");
            model.addAttribute("loginModel", loginModel);
            return "inicio";
        }

        try {

            // Invocamos el servicio de autenticacion
            String ruta_api = "http://localhost:8081/autenticacion/login";

            // Creamos el objeto para la solicitud y enviamos los parametros
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO(tipoDocumento, numeroDocumento, password);

            // Realizamos la solicitud
            LoginResponseDTO loginResponseDTO = restTemplate.postForObject(ruta_api, loginRequestDTO, LoginResponseDTO.class);

            // Si la respuesta no es null y el codigo es 00
            if (loginResponseDTO != null && loginResponseDTO.codigo().equals("00")){

                // Autenticacion exitosa, redirigimos a la pagina principal
                LoginModel loginModel = new LoginModel("00", "", loginResponseDTO.nombreUsuario());
                model.addAttribute("loginModel", loginModel);
                return "principal";

            } else {

                // Autenticacion fallida, redirigimos al login nuevamente
                LoginModel loginModel = new LoginModel("02", "Error: Credenciales incorrectas", "");
                model.addAttribute("loginModel", loginModel);
                return "inicio";

            }

        } catch (Exception e) {

            // Si falló la comunicación con el backend
            LoginModel loginModel = new LoginModel("99", "Error: Ocurrió un problema al autenticar", "");
            model.addAttribute("loginModel", loginModel);
            return "inicio";

        }

    }

}
