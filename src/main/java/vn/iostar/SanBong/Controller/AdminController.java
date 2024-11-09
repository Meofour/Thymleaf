package vn.iostar.SanBong.Controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.iostar.SanBong.Entity.UserEntity;
import vn.iostar.SanBong.Model.UserModel;
import vn.iostar.SanBong.Repository.UserRepository;

@Controller
@RequestMapping("/users")
public class AdminController {
	
	
@Autowired
private UserRepository repo;

@GetMapping({"","/"})
public String showUserList(Model model) {
	List<UserEntity> users = repo.findAll();
	model.addAttribute("users",users);
	return "users/index";
}

//GỌI TRANG CREATE + HIỂN THỊ USER
@GetMapping("/create")
public String showCreatePage(Model model) {
	UserModel userModel = new UserModel();
	model.addAttribute("userModel", userModel);
	return "users/CreateUser";
}
//METHOD LƯU USER KHI TẠO
@PostMapping("/create")
public String createUser(


		

		@Valid @ModelAttribute UserModel userModel,
		BindingResult result
		) {
	if(userModel.getImageFile().isEmpty()) {
		result.addError(new FieldError("userModel","imageFile","The image's emty"));
	}
	if(result.hasErrors()) {
		return "users/CreateUser";
	}
	
	// LƯU ẢNH VÀO THƯ MỤC
	MultipartFile image = userModel.getImageFile();
	String storageFileName = image.getOriginalFilename();

	try {
	String uploadDir = "public/images/";
	Path uploadPath = Paths.get (uploadDir);

	if (!Files.exists (uploadPath) ) {
	Files.createDirectories (uploadPath);

	image.getOriginalFilename ();

	}

	try(InputStream inputStream = image.getInputStream () ) {
	Files. copy (inputStream, Paths.get (uploadDir + storageFileName),
	StandardCopyOption. REPLACE_EXISTING) ;
	}
	} catch (Exception ex) {
	System. out.println ("Exception: " + ex.getMessage () );
}
	
	//GẮN MODEL VỚI ENTITY LƯU VÀO DATABASE
	UserEntity user = new UserEntity();
	user.setName(userModel.getName());
	user.setPhonenumber(userModel.getPhonenumber());
	user.setRole(userModel.getRole());
	user.setPassword(userModel.getPassword());
	user.setEmail(userModel.getEmail());
	user.setImageFileName(storageFileName);
	repo.save(user);
	
	
	return "redirect:/users";
}
//GỌI TRANG EDIT
@GetMapping("/edit")

public String showEditPage(Model model,@RequestParam int id) {
	try {
		UserEntity user = repo.findById(id).get();
		model.addAttribute("user",user);
		
		UserModel userModel = new UserModel();
		
		
		
		userModel.setName(user.getName());
		userModel.setPhonenumber(user.getPhonenumber());
		userModel.setRole(user.getRole());
		userModel.setEmail(user.getEmail());
//		userModel.setImageFile(storageFileName);
		model.addAttribute("userModel",userModel);
		
	}
	catch(Exception ex)
	{
	System.out.println("Exception"+ex.getMessage());
	return "redirect:/users";
	
}
return "users/EditUser";
}
//METHOD LƯU TRANG EDIT
@PostMapping("/edit")
public String saveEdit(Model model,@RequestParam int id,@Valid @ModelAttribute UserModel userModel, BindingResult result) {
	try {
		UserEntity user = repo.findById(id).get();
		model.addAttribute("user",user);
		//check kết quả binding
		if(result.hasErrors()){
			return "users/EditUser";
	}
	
		if (!userModel.getImageFile().isEmpty()) {
			// delete old image
			String uploadDir = "public/images/";
			Path oldImagePath = Paths.get(uploadDir + user.getImageFileName() );

			try {
			Files.delete(oldImagePath);
			}
			catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage()) ;
			}

			// save new image file
			MultipartFile image = userModel.getImageFile();
			String storageFileName = image.getOriginalFilename();

			try (InputStream inputStream = image.getInputStream() ) {
			Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
			StandardCopyOption.REPLACE_EXISTING);
			}
			user.setImageFileName (storageFileName);
		}
		
		user.setName(userModel.getName());
		user.setPhonenumber(userModel.getPhonenumber());
		user.setRole(userModel.getRole());
		user.setPassword(userModel.getPassword());
		user.setEmail(userModel.getEmail()); 
		repo.save(user);
		
		
		
	}
	catch(Exception ex) {
		System.out.println("Exception"+ex.getMessage());
		
	}
return "redirect:/users";
    }
//GỌI TRANG DELETE
@GetMapping("/delete")
public String deleUser(@RequestParam int id) {
	try {
		UserEntity user = repo.findById(id).get();
		//xóa ảnh 
		Path imagePath = Paths.get("public/images/"+ user.getImageFileName());
		try {
			Files.delete(imagePath);
		}
		catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage()) ;
			}
		//xóa user trong database
		repo.delete(user);
	}
	catch (Exception ex) {
		System.out.println("Exception: " + ex.getMessage()) ;
		}
	
	return "redirect:/users";
}
}
