package com.rishi.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//@Column(name="f_name")
	private String firstName;
	private String lastName;
	@Column(unique=true)
	private String email;
	private String password;
	private String gender;
	private List<Integer> followers=new ArrayList<Integer>();
	private List<Integer> followings=new ArrayList<Integer>();
	@ManyToMany
	@JsonIgnore
	private List<Post> savedPosts =new ArrayList<Post>();
}
