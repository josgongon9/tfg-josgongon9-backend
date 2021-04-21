package com.josgongon9.tfgwebbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josgongon9.tfgwebbackend.exception.EntityNotFoundException;
import com.josgongon9.tfgwebbackend.model.ToDo;
import com.josgongon9.tfgwebbackend.repository.ToDoRepository;

@Service
public class ToDoService {
	@Autowired
	private ToDoRepository toDoRepository;

	public List<ToDo> findAll() {
		return toDoRepository.findAll();
	}

	public ToDo findById(String id) {
		return toDoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	public ToDo save(ToDo toDo) {
		return toDoRepository.save(toDo);
	}

	public void deleteById(String id) {
		toDoRepository.deleteById(id);
	}

}
