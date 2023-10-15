package br.com.julialimp.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    // public UserModel create(@RequestBody UserModel userModel) {
    public ResponseEntity create(@RequestBody UserModel userModel) {
        // System.out.println(userModel.getUsername());
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null) {
            // System.out.println("usuário já existe");
            return ResponseEntity.status(400).body("Usuário já existe");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);
        // return userCreated;
        return ResponseEntity.status(201).body(userCreated);
    }
}
