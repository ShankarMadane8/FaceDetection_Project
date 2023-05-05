package com.crud.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.crud.entity.Student;
import com.crud.service.StudentService;

@Controller
public class ImageCaptureController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

    @GetMapping("/face2")
    public String index(Model model) {
    	model.addAttribute("msg", "please capture image" );
    	model.addAttribute("student",new Student());
		model.addAttribute("url","signup2");
		model.addAttribute("title","Signup Form");
        return "face2";
    }
    
    

    @PostMapping("/save-image")
    public String saveImage(@RequestBody Map<String, String> data,Model model) {
        try {
        	System.out.println("workin post methode url(/save-image)");
            String dataUrl = data.get("dataUrl");
            
            String firstName = data.get("firstName");
            System.out.println("firstName: "+firstName);
            String base64Image = dataUrl.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            

          // String fileName = UUID.randomUUID().toString() + ".png";
           String fileName = firstName + ".jpg";
					// C:\Users\madan\OneDrive\Documents\iSmart Shankar\SpringBootProject\springCRUD\src\main\resources\static\newface\images
          //C:\Users\madan\OneDrive\Documents\iSmart Shankar\SpringBootProject\springCRUD\src\main\resources\static\newface\images

           Path imagePath = Paths.get("C:\\Users\\madan\\OneDrive\\Documents\\iSmart Shankar\\SpringBootProject\\springCRUD\\src\\main\\resources\\static\\newface\\images", fileName);
           System.out.println(imagePath);
            Files.write(imagePath, imageBytes);
            
            model.addAttribute("msg", "Image saved successfully: " +fileName);

            return"redirect:/face2";
        } catch (Exception e) {
        	model.addAttribute("msg", "Failed to save image: " + e.getMessage());
        	return"redirect:/face2";
        }
    }
    
    
    
    

	@PostMapping("signup2")
	public String signupPost(@ModelAttribute Student stu,Model model,HttpServletRequest req){
		
       
		
	    if(stu.getEmail().equals("") || stu.getPassword().equals("")) {
	    	model.addAttribute("msg","Enter Complete Information !!!");
	    }
		
	    else if(stu.getPassword().equals(req.getParameter("confirm_password"))) {
			try {
				stu.setPassword(passwordEncoder.encode(stu.getPassword()));
				studentService.insertStudent(stu);
				model.addAttribute("msg","Congratulations "+stu.getName()+", your account has been successfully created.");
			} catch (Exception e) {
				model.addAttribute("msg","Already Registered User?");
			}
			
			
		}else {
			model.addAttribute("msg","passwords do not match.");
		}
		
		model.addAttribute("url","signup2");
		model.addAttribute("student",new Student());
		model.addAttribute("title","Signup Form");
		
		System.out.println("Student Save Successfully (url: /signup2).........");
		return "face2";
	}
    
    
}

