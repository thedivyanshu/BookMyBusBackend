package com.bookMyBus.pojo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Entity
@Table(name = "admins",schema = "bus")
public class Admin {

	@Id
	private int adminId;
	
	@JsonIgnore
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(nullable = false) //not-null
	private String password;
	
	@Column(nullable = false)
	private String adminName;

	public Admin() {
        this.adminId = adminId;
        this.password = password;
        this.adminName = adminName;
    }
	public Admin(int i, String string, String string2) {
		// TODO Auto-generated constructor stub
	}



	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	
	
}