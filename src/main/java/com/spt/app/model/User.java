package com.spt.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(of={"id"})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String username;

	@Column
	@JsonIgnore
	private String password;

	@Column(length = 1000)
	private String address;

	@Column(length = 10)
	private String phone;

	@Column(length = 12)
	private String refCode;

	private Double salary;

	private String memberType;

}