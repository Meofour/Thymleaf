package vn.iostar.SanBong.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table( name = "users")
public class UserEntity {
	
	
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;

private String name;
private String password;
private String email;
private String phonenumber;
private String role;

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

public String getPhonenumber() {
	return phonenumber;
}

public void setPhonenumber(String phonenumber) {
	this.phonenumber = phonenumber;
}

@Column(columnDefinition = "TEXT")
private String imageFileName;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getImageFileName() {
	return imageFileName;
}

public void setImageFileName(String imageFileName) {
	this.imageFileName = imageFileName;
}


}
