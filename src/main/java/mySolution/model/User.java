package mySolution.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_member")
public class User implements Serializable {
	@Id
	@Column(name="user_id")
	private String userId;
	@Column(name="user_password")
	private String userPassword;
	@Column(name="mail_address")
	private String mailAddress;
	@Column(name="user_role")
	private String userRole;
	@Column(name="date_time")
	private Timestamp dataTime;
}