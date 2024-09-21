package pe.edu.cibertec.adopta_patitas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.cibertec.adopta_patitas.viewmodel.LoginModel;

@Controller
@RequestMapping("/login")
public class LoginController {

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

        // Redirigir a la pantalla principal
        LoginModel loginModel = new LoginModel("00", "", "Iann Falcon");
        model.addAttribute("loginModel", loginModel);
        return "principal";

    }

}
