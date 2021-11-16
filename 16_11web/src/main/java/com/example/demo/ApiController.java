package com.example.demo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
public class ApiController {

    private List<User> users = new ArrayList<>();
    @GetMapping("users/{age}")
    public List<User> getUsers(@PathVariable("age") int age) {
        List<User> users1=new ArrayList<>();
        for(int i=0;i<users.size();i++){
            if(users.get(i).age>=age-5&&users.get(i).age<=age+5){
                users1.add(users.get(i));
            }
        }
        return users;
    }
    // curl -X GET http://localhost:8080/users/17
    @GetMapping("users/{direction}")
    public List<User> getUsersSorted(@PathVariable("direction") String direction) {
        List<User> users1=new ArrayList<>();
        if(direction.equals("up")){
            for(int i=0;i< users.size();i++){

            }
        }
        return users;
    }
    @GetMapping("users/{name}")
    public String getUser(@PathVariable("name") String name) {
        User user= null;
        boolean doesntExist= true;
        try{
            for(int i=0;i<users.size();i++) {
                if(name.equals(users.get(i).getName())) {
                    doesntExist= false;
                    user= users.get(i);
                }
            }
            if(doesntExist) {
                throw new DoesntExistThisWayException();
            }
        }
        catch (DoesntExistThisWayException e) {
            System.out.println("404");
        }
        String s="name="+user.name+"age="+user.age;
        return s;
    }
    // curl -X GET http://localhost:8080/users/Anton
    @PostMapping("users")
    public void addUser(@RequestBody User user, String repeatPassword) {
        try {
            for (int i=0;i<users.size();i++) {
                if(user.name.equals(users.get(i).name)) {
                    throw new AlreadyExistsThisWayException();
                }
            }
            for (int i=0;i<user.name.length();i++) {
                if((int) user.name.charAt(i)<48||((int) user.name.charAt(i)>90&&(int) user.name.charAt(i)!=95&&user.name.charAt(i)<97)||user.name.charAt(i)>122){
                    throw new WrongSyntException();
                }
            }
            if(!user.password.equals(repeatPassword)){
                throw new WrongSyntException();
            }
            users.add(user);
        }catch (AlreadyExistsThisWayException e) {
            System.out.println("409");
        }catch (WrongSyntException e) {
            System.out.println("400");
        }
    }
    // curl -X POST http://localhost:8080/users -H 'Content-Type:application/json' -d '{"name" : "Kate Kadnikova", "password" : "idk", "age" : "17"}, "abcdef"'
    @DeleteMapping("users/{name}")
    public void deleteUser(@PathVariable("name") String name, @RequestBody String repeatPassword) {
        try {
            if (!name.substring(0,4).equals("admin")) {
                throw new WrongAccessException();
            }
            int index= 0;
            boolean doesntExist= true;
            for (int i=0;i<users.size();i++) {
                if (name.equals(users.get(i).name)) {
                    index= i;
                    doesntExist= false;
                }
            }
            if(doesntExist) {
                throw new DoesntExistThisWayException();
            } else {
                if(!users.get(index).password.equals(repeatPassword)){
                    throw new WrongSyntException();
                }
                users.remove(index);
            }
        } catch (WrongAccessException e ) {
            System.out.println("403");
        }
        catch (WrongSyntException e){
            System.out.println("400");
        }
    }
    // curl -X DELETE http://localhost:8080/users/Anton
    @PutMapping("users/{name}")
    public void updateUserPassword(@PathVariable("name") String name, @RequestBody String password){
        boolean doesntExist= true;
        try{
            for(int i=0;i<users.size();i++) {
                if(name.equals(users.get(i).getName())) {
                    doesntExist= false;
                }
            }
            if(doesntExist) {
                throw new DoesntExistThisWayException();
            }
            if (!name.substring(0,4).equals("update")) {
                throw new WrongAccessException();
            }
            for (int i=0;i<name.length();i++) {
                if((int) name.charAt(i)<48||((int) name.charAt(i)>90&&(int) name.charAt(i)!=95&&name.charAt(i)<97)||name.charAt(i)>122){
                    throw new WrongSyntException();
                }
            }
        }
        catch (DoesntExistThisWayException e) {
            System.out.println("404");
        }
        catch (WrongAccessException e ) {
            System.out.println("403");
        }
        catch (WrongSyntException e){
            System.out.println("400");
        }
    }
    // curl -X PUT http://localhost:8080/users/Anton -H 'Content-Type: application/json' -d '1918year'

    class AlreadyExistsThisWayException extends ResponseStatusException {
        public AlreadyExistsThisWayException() {
            super(HttpStatus.CONFLICT, "conflict");
        }
    }
    class WrongSyntException extends ResponseStatusException {
        public WrongSyntException() {
            super(HttpStatus.BAD_REQUEST, "bad request");
        }
    }
    class WrongAccessException extends ResponseStatusException {
        public WrongAccessException() {
            super(HttpStatus.FORBIDDEN);
        }
    }
    class DoesntExistThisWayException extends ResponseStatusException {
        public DoesntExistThisWayException() {
            super(HttpStatus.NOT_FOUND, "not found");
        }
    }

}