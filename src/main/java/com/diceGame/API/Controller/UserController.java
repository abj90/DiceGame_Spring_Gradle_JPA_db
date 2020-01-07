package com.diceGame.API.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diceGame.API.Domain.User;
import com.diceGame.API.Service.UserService;

@RestController
@RequestMapping(path="/players")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	 //Post players request
	 @PostMapping()
	 public ResponseEntity<String> addUser(@RequestBody User user) {
		 	
		 	if(!userService.saveUser(user)) {
		 		return new ResponseEntity<>("User name has been already taken", HttpStatus.CONFLICT);
		 	}else {
		 		return new ResponseEntity<>("status ok", HttpStatus.OK);
		 	}
		 	
	 }
	 
	 //Put players Request
	 @PutMapping(value="/{userId}")
	 public void updateUser(@RequestBody User user, @PathVariable Integer userId) {
		 userService.updateUserDB(user);
	 }
	 
	//Delete players request
	 @DeleteMapping(value="/{userId}")
	 public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
		 
		 boolean isPlayerFound = userService.deleteUser(userId);
		 
		 if(!isPlayerFound) {
			 return new ResponseEntity<>("User was not found",HttpStatus.NOT_FOUND);
		 } else {
			 return new ResponseEntity<>("User "+userId+ " has been delected Properly", HttpStatus.OK);
		 }
	 }
	 
	 //Get players 
	@GetMapping(path="/all")
	 public ArrayList<User> getAllUsers(){
		 return userService.getUsers();
	 }
	
	//Get ranking 
	@GetMapping(path="/ranking")
	public ArrayList<User> getPlayerRanking(){
		return userService.getRanking();
	}
	
	//Get ranking Winner
	@GetMapping(path="/ranking/winner")
	public User getRankingWinner(){
		return userService.getWinner();
	}
	
	//Get raning with loser
	@GetMapping(path="/ranking/loser")
	public ArrayList<User> getRankingLoser(){
		return userService.getLoser();
	}
	
	
	
}

