package com.mathis.app.rest.Controller;

import com.mathis.app.rest.Models.User;
import com.mathis.app.rest.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ApiControllers {

    @Autowired
    private final UserRepo userRepo;

    public ApiControllers(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String getPage() {
        return "Welcome";
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @PostMapping(value = "/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    @PutMapping(value = "/update/{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user){
        User updatedUser = userRepo.findById(id).get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setOccupation(user.getOccupation());
        updatedUser.setAge(user.getAge());
        userRepo.save(updatedUser);
        return "Updated...";
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id){
        User deleteUser = userRepo.findById(id).orElse(null);
        if (deleteUser != null) {
            userRepo.delete(deleteUser);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}