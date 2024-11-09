package vn.iostar.SanBong.Model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;

public class UserModel {
@NotEmpty(message = "the name is required")
private String name;

@NotEmpty(message = "the password is required")
private String password;

@NotEmpty(message = "the email is required")
private String email;

@NotEmpty(message = "the phonenumber is required")
private String phonenumber;

@NotEmpty(message = "the role is required")
private String role;

private MultipartFile imageFile;

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

public String getPhonenumber() {
	return phonenumber;
}

public void setPhonenumber(String phonenumber) {
	this.phonenumber = phonenumber;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

public MultipartFile getImageFile() {
	return imageFile;
}

public void setImageFile(MultipartFile imageFile) {
	this.imageFile = imageFile;
}

}