package com.example.demo.model;
import jakarta.persistence.*;
@Entity
@Table(name = "userbase")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int userId;
	
	@Column(name = "name")
	private String userName;
	
	@Column(name="age")
	private int age;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int userId, String userName, int age, String email, String password) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.age = age;
		this.email = email;
		this.password = password;
	}
	
	
	public User(int userId, String userName, int age, String email) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.age = age;
		this.email = email;
	}
	public User(String userName, int age, String email, String password) {
		// TODO Auto-generated constructor stub
		this.userName = userName;
		this.age = age;
		this.email = email;
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", age=" + age + ", email=" + email + ", password="
				+ password + "]";
	}
	
	
	
	
	

}
