package nl.mfarr.supernova.services;

import nl.mfarr.supernova.mappers.AdminMapper;
import nl.mfarr.supernova.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;


}
