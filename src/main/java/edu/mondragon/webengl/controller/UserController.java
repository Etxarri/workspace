package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.model.Usuario;
import com.ejemplo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller  // Indica que esta clase es un controlador de Spring
@RequestMapping("/users")  // Define la URL base para las solicitudes de este controlador
public class UserController {

    @Autowired  // Inyección automática de UserService
    private UserService userService;

    @GetMapping()  // Mapea la solicitud GET a este método
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();  // Llamada al servicio para obtener los usuarios
        model.addAttribute("users", users);  // Agrega la lista de usuarios al modelo para pasarlo a la vista
        return "userList";  // Retorna el nombre de la vista (JSP o HTML)
    }
}

