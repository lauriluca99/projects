package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Credentials;
import com.example.demo.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired
	protected CredentialsRepository credentialRepository;
	
	@Autowired
    protected PasswordEncoder passwordEncoder;
	
	@Transactional
	public Credentials getCredentials(Long id) {
		Optional<Credentials> result =this.credentialRepository.findById(id);
		return result.orElse(null);
	}

	
	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result= this.credentialRepository.findByUsername(username);
		return result.orElse(null);
	}
	
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		credentials.setRole(Credentials.DEFAULT_ROLE);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credentialRepository.save(credentials);
	}
	
	@Transactional
    public List<Credentials> getAllCredentials() {
        List<Credentials> result = new ArrayList<>();
        Iterable<Credentials> iterable = this.credentialRepository.findAll();
        for(Credentials credentials : iterable)
            result.add(credentials);
        return result;
    }


	@Transactional
	public void deleteCredentials(String username) {
		Optional<Credentials> result = this.credentialRepository.findByUsername(username);
		this.credentialRepository.delete(result.get());
	}
}
