package com.bhanu.psychiba.controller;

import java.util.List;

import javax.validation.Valid;

import com.bhanu.psychiba.model.Admin;
import com.bhanu.psychiba.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admins")
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @PostMapping("/admins")
    public Admin createAdmin(@Valid @RequestBody Admin admin) {
        return adminRepository.save(admin);
    }

    @GetMapping("/admins/{id}")
    public Admin getAdminById(@PathVariable(value = "id") Long id) throws Exception {
        return adminRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
    }

    @PutMapping("/admins/{id}")
    public Admin updateAdmin(@PathVariable(value = "id") Long id, @Valid @RequestBody Admin admin) throws Exception {
        Admin p = adminRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        p.setName(admin.getName());
        return adminRepository.save(p);
    }

    @DeleteMapping("/admins/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable(value = "id") Long id) throws Exception {
        Admin p = adminRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        adminRepository.delete(p);
        return ResponseEntity.ok().build();
    }
}