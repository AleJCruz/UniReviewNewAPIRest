package com.upc.unireview.service;

import com.upc.unireview.entities.Course;
import com.upc.unireview.entities.Rigurosity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upc.unireview.repository.RigurosityRepository;
import com.upc.unireview.interfaceservice.RigurosityService;

import java.util.List;

@Service
public class RigurosityServiceImpl implements RigurosityService{
    @Autowired
    private RigurosityRepository rigurosityRepository;

    public Rigurosity registerRigurosity(Rigurosity rigurosity){
        return rigurosityRepository.save(rigurosity);
    }
    public Rigurosity updateRigurosity(Rigurosity rigurosity) throws Exception{
        rigurosityRepository.findById(rigurosity.getId()).
                orElseThrow(()->new Exception("No se encontró la entidad"));
        return rigurosityRepository.save(rigurosity);
    }

    public Rigurosity deleteRigurosity(Long id) throws Exception{
        Rigurosity rigurosity = rigurosityRepository.findById(id).orElseThrow(()->new Exception("No se encontro la rigurosidad"));
        rigurosityRepository.delete(rigurosity);
        return rigurosity;
    }
    public List<Rigurosity> listRigurosities(){
        return rigurosityRepository.findAll();
    }
}
