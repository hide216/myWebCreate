package mySolution.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_member")
public class UserSmallData implements Serializable {
	@Id
	@Column(name="user_id")
	private String userId;
	@Column(name="user_password")
	private String userPassword;
}