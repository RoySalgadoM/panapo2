package mx.edu.utez.panapo.user.controller;

import mx.edu.utez.panapo.person.model.Person;
import mx.edu.utez.panapo.person.model.PersonRepository;
import mx.edu.utez.panapo.status.Status;
import mx.edu.utez.panapo.status.StatusRepository;
import mx.edu.utez.panapo.user.model.User;
import mx.edu.utez.panapo.user.model.UserRepository;
import mx.edu.utez.panapo.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    StatusRepository statusRepository;


    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll(){
        return  new ResponseEntity<>(new Message("OK",false,userRepository.findAll()), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(long id){
        if( userRepository.existsById(id)){
            return new ResponseEntity<>(new Message("OK", false, userRepository.findById(id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("El usuario ya existe", true, userRepository.findById(id)), HttpStatus.BAD_REQUEST);
    }


    @Transactional(rollbackFor = {SQLException.class}) // si encuenra un error lo vuelve a hacer
    public ResponseEntity<Message> save(User user){
        if(userRepository.existsByUsername(user.getUsername()))
            return new ResponseEntity<>(new Message("El usuario ya existe", true, null), HttpStatus.BAD_REQUEST);
        if(userRepository.existsByPersonEmail(user.getPerson().getEmail())) {
            return new ResponseEntity<>(new Message("La persona ya cuenta con un usuario", true, null), HttpStatus.BAD_REQUEST);
        }
        Person personTemp = user.getPerson();
        personTemp.setStatus(getByStatus(1).get());
        personTemp = personRepository.saveAndFlush(personTemp);
        user.setPerson(personTemp);
        user.setUsername(personTemp.getEmail());
        user.setStatus(getByStatus(1).get());
        return new ResponseEntity<>(new Message("OK", false, userRepository.saveAndFlush(user)),
                HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class}) // si encuenra un error lo vuelve a hacer
    public ResponseEntity<Message> save2(User user){
        if(userRepository.existsByUsername(user.getUsername())) {
            User usertemp = getByUsername(user.getUsername()).get();
            usertemp.setStatus(user.getStatus());
            return new ResponseEntity<>(new Message("El usuario ya existe", true, userRepository.saveAndFlush(usertemp)), HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByPersonEmail(user.getPerson().getEmail())) {
            return new ResponseEntity<>(new Message("La persona ya cuenta con un usuario", true, null), HttpStatus.BAD_REQUEST);
        }
        Person personTemp = getByPerson(user.getUsername()).get();
        personTemp.setStatus(getByStatus(1).get());
        personTemp = personRepository.saveAndFlush(personTemp);
        user.setPerson(personTemp);
        user.setUsername(personTemp.getEmail());
        user.setStatus(getByStatus(1).get());
        return new ResponseEntity<>(new Message("OK", false, userRepository.saveAndFlush(user)),
                HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class}) // si encuenra un error lo vuelve a hacer
    public ResponseEntity<Message> update(User user){
        if(userRepository.existsById(user.getId())){

            User usertemp = getByUsername(user.getPerson().getEmail()).get();
            Person personTemp = user.getPerson();
            personTemp = personRepository.saveAndFlush(personTemp);
            usertemp.setPerson(personTemp);
            usertemp.setUsername(personTemp.getEmail());

            return new ResponseEntity<>(new Message("OK", false, userRepository.saveAndFlush(usertemp)), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Message("El Usuario no existe", true, null), HttpStatus.BAD_REQUEST);
    }

    @Transactional(rollbackFor = {SQLException.class}) // si encuenra un error lo vuelve a hacer
    public ResponseEntity<Message> updatePassword(User user){
        if(userRepository.existsById(user.getId())){
            User user1 =  getById(user.getId()).get();
            user1.setPassword(user.getPassword());
            return new ResponseEntity<>(new Message("OK", false, userRepository.saveAndFlush(user1)), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Message("El Usuario no existe", true, null), HttpStatus.BAD_REQUEST);
    }


    @Transactional(rollbackFor = {SQLException.class}) // si encuenra un error lo vuelve a hacer
    public ResponseEntity<Message> savePassword(User user){
        System.out.println(user.getCode());
        if(userRepository.existsByCode(user.getCode())){
            User user1 =  getByCode(user.getCode()).get();
            user1.setPassword(user.getPassword());
            return new ResponseEntity<>(new Message("OK", false, userRepository.saveAndFlush(user1)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("El codigo no existe", true, null), HttpStatus.BAD_REQUEST);
    }

    @Transactional(rollbackFor = {SQLException.class}) // si encuenra un error lo vuelve a hacer
    public ResponseEntity<Message> updatepassword(User user,String code){
        if(userRepository.existsByUsername(user.getUsername())){
            user.setCode(code);
            return new ResponseEntity<>(new Message("OK", false, userRepository.saveAndFlush(user)), HttpStatus.OK);
        }return new ResponseEntity<>(new Message("La Usuario no existe", true, null), HttpStatus.BAD_REQUEST);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> deletebyid(long id){
        if( userRepository.existsById(id)){
            System.out.println(id);
            userRepository.deleteById(id);
            return new ResponseEntity<>(new Message("Usuario eliminado", false,null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("El usuario no existe", true, null), HttpStatus.BAD_REQUEST);
    }

    @Transactional(readOnly = true)
    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getByCode(String code){
        return userRepository.findByCode(code);
    }


    @Transactional(readOnly = true)
    public Optional<User> getById(long id){
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Status> getByStatus(long id){
        return statusRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getByPerson(String email){
        return personRepository.findByEmail(email);
    }


}
