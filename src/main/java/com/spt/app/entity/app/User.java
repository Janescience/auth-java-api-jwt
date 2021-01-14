package com.spt.app.entity.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spt.app.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity{

	@NotNull
	@Column(unique = true)
	private String username;

	@NotNull
	@JsonIgnore
	private String password;

	@Column(length = 1000)
	private String address;

	@NotNull
	@Column(unique = true,length = 10)
	private String phoneNumber;

	@NotNull
	@Column(unique = true,length = 12)
	private String refCode;

	@Positive
	@NotNull
	private Double salary;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberType")
	private MemberType memberType;

}