package com.example.LibraryMngmnt.controller;

import com.example.LibraryMngmnt.model.User;
import com.example.LibraryMngmnt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> userList = new ArrayList<>();
            userRepository.findAll().forEach(userList::add);  // retrieves all the books available in the bookRepository and collects them into the bookList.

            if (userList.isEmpty()) {
                String msg="Currently there is no user !";
                return new ResponseEntity<>(msg, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        Optional<User> userObj = userRepository.findById(id); //used to avoid null pointer exceptions by explicitly indicating that a value might be absent
        if (userObj.isPresent()) {
            return new ResponseEntity<>(userObj.get(), HttpStatus.OK);
        } else {
            String msg="User with id " +id+" is not there in library!";
            return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            User userObj = userRepository.save(user);
            return new ResponseEntity<>(userObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody User user) {
        try {
            Optional<User> userData = userRepository.findById(id);
            if (userData.isPresent()) {
                User updatedUserData = userData.get();

                updatedUserData.setFirstName(user.getFirstName());
                updatedUserData.setLastName(user.getLastName());
                updatedUserData.setRollNo(user.getRollNo());
                User userObj = userRepository.save(updatedUserData);
                return new ResponseEntity<>(userObj, HttpStatus.CREATED);
            }
            String msg="User with id " +id+" is not there !";
            return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            Optional<User> userData = userRepository.findById(id);
            if(userData.isPresent()) {
                userRepository.deleteById(id);

                String msg = "User with ID " + id + " is deleted successfully!";
                return new ResponseEntity<>(msg, HttpStatus.OK);
            }
            else{
                String msg = "There is no user with " + id;
                return new ResponseEntity<>(msg, HttpStatus.OK);

            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<?> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            String msg = "All users are deleted successfully!";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}