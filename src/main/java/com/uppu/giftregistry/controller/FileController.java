package com.uppu.giftregistry.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uppu.giftregistry.model.Events;
import com.uppu.giftregistry.service.FileTestService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Controller
@RequestMapping({ "/filetest" })

public class FileController {
	@Autowired
	FileTestService service;
	@PostMapping
	public String upload(@RequestParam("myFile") MultipartFile file) {
		
		try {
			
			//System.out.println(file.getBytes());
			//System.out.println(file.getOriginalFilename());
			service.fileUpload(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//
		return "here";
	}
	
	@GetMapping(path = { "/getImage" })
	public String getImage() {
		//System.out.println("here");
		return service.getImage();
	}
	@GetMapping(path = { "/sendMail" })
	public String sendMail() {
		System.out.println("here in send email");
		//return "sending";
		return service.sendEmail();
	}
}
