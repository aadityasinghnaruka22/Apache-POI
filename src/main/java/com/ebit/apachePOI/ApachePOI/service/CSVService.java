package com.ebit.apachePOI.ApachePOI.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ebit.apachePOI.ApachePOI.helper.CSVHelper;
import com.ebit.apachePOI.ApachePOI.model.Tutorial;
import com.ebit.apachePOI.ApachePOI.repository.TutorialRepository;


@Service
public class CSVService {
	
  @Autowired
  TutorialRepository repository;

  public void save(MultipartFile file) {
    try {
      List<Tutorial> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
      repository.saveAll(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public List<Tutorial> getAllTutorials() {
    return repository.findAll();
  }
  
  public ByteArrayInputStream load() {
	    List<Tutorial> tutorials = repository.findAll();

	    ByteArrayInputStream in = CSVHelper.tutorialsToCSV(tutorials);
	    return in;
	  }
}