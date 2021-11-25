package com.josgongon9.tfgwebbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.josgongon9.tfgwebbackend.model.TimeEntry;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
import com.josgongon9.tfgwebbackend.security.jwt.JwtUtils;
import com.josgongon9.tfgwebbackend.security.services.UserDetailsImpl;
import com.josgongon9.tfgwebbackend.service.TimeEntryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TimeEntryController {

	@Autowired
	TimeEntryService timeEntryService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtUtils jwtUtils;

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/timeEntries")
	public ResponseEntity<List<TimeEntry>> getAllTimeEntrys(@RequestHeader String authorization) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

			List<TimeEntry> timeEntries = user.getTimeEntries();

			if (timeEntries.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(timeEntries, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping("/timeEntries")
	public ResponseEntity<TimeEntry> createTimeEntry(@RequestBody TimeEntry timeEntry) {
		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();

			TimeEntry _timeEntry = timeEntryService.save(new TimeEntry(timeEntry.getDate(), timeEntry.getTotalTime(),
					timeEntry.getComment()/* , timeEntry.getUser() */));

			User user = userRepository.findByUsername((String) username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

			user.getTimeEntries().add(_timeEntry);
			userRepository.save(user);
			return new ResponseEntity<>(_timeEntry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}