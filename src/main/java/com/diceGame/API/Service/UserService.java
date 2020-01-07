package com.diceGame.API.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diceGame.API.Domain.Game;
import com.diceGame.API.Domain.User;
import com.diceGame.API.Repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GameService gameService;
	
	// get all users
	public ArrayList<User> getUsers(){
		ArrayList<User> users = new ArrayList<User>();
		//checking all the users in db
		userRepository.findAll().forEach(user -> {
			//checking user's games
			int gamesNumber = gameService.getGamesByUserid(user.getId()).size();
			if( gamesNumber > 0) {
				double gamesWon = 0;
				for(Game game : gameService.getGamesByUserid(user.getId())) {
					System.out.println(game.getIsGameWon()+" corresponding to player "+user.getId());
					if(game.getIsGameWon() == true) {
						gamesWon += 1;
					}
				}
				//calculating the victory percentage
				user.setVictoryPercentage(gamesWon/gamesNumber);
				//in order to update the player in db
				userRepository.save(user);
				users.add(user);
				gamesWon = 0;
			}else {
				users.add(user);
			}
		
		});
		
		return users;
		
	}
	
	//get ranking 
	public ArrayList<User> getRanking(){
		//return new list sorted by getVictoryPercentage descending
		ArrayList<User> usersRanking = (ArrayList<User>) this.getUsers().stream()
		.sorted(Comparator.comparing(User::getVictoryPercentage).reversed())
		.collect(Collectors.toList());	
		
		return usersRanking;
	}
	
	// get ranking winner
	public User getWinner(){ 
		//returning player with the higher score
		return this.getUsers().stream().max(Comparator.comparing(User::getVictoryPercentage)).get();
	}
	
	//get ranking loser
	
	public ArrayList<User> getLoser(){
		ArrayList<User> worstScores = (ArrayList<User>) this.getUsers().stream()
				.filter(user -> user.getVictoryPercentage() == 0)
				.collect(Collectors.toList());
		
		return worstScores;
	}
	
	//saving user into db
	public boolean saveUser(User user) {
		
		if(user.getName() != "") {
			
			if(!userRepository.existsByName(user.getName())){
				user.setDate();
				userRepository.save(user);
				
				return true;
			}else {
				
				return false;
			}
			
		}else {
			
			user.setName("Anonymous");
			user.setDate();
			userRepository.save(user);
			
			return true;
		}
	}
		
	//updating user in db 
	public void updateUserDB(User user) {
		userRepository.save(user);
	}
	
	public boolean deleteUser(int userId) {
		
		if(userRepository.existsById(userId)) {
			gameService.deleteGameByUserId(userId);
			userRepository.deleteById(userId);
			return false;
		} else {
			return true;
		}
		
	}
	
}
