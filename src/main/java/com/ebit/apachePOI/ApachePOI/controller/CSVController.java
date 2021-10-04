package com.ebit.apachePOI.ApachePOI.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



//import org.springframework.core.io.InputStreamResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;

import org.springframework.web.multipart.MultipartFile;

import com.ebit.apachePOI.ApachePOI.helper.CSVHelper;
import com.ebit.apachePOI.ApachePOI.message.ResponseMessage;
import com.ebit.apachePOI.ApachePOI.model.Tutorial;
import com.ebit.apachePOI.ApachePOI.service.CSVService;

@CrossOrigin("http://localhost:8081")
@RestController()
public class CSVController {

	  @Autowired
	  CSVService fileService;

	  @PostMapping("/upload")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";

	    if (CSVHelper.hasCSVFormat(file)) {
	      try {
	        fileService.save(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	      } catch (Exception e) {
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	      }
	    }

	    message = "Please upload a csv file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	  }

	  @GetMapping("/tutorials")
	  public ResponseEntity<List<Tutorial>> getAllTutorials() {
	    try {
	      List<Tutorial> tutorials = fileService.getAllTutorials();

	      if (tutorials.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(tutorials, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  
	  @GetMapping("/download")
	  public ResponseEntity<InputStreamResource> getFile() {
	    String filename = "tutorials.csv";
	    InputStreamResource file = new InputStreamResource(fileService.load());

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/csv"))
	        .body(file);
	  }

	}
