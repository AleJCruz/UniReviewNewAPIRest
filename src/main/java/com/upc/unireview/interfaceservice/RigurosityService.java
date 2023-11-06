package com.upc.unireview.interfaceservice;

import com.upc.unireview.entities.Rigurosity;

import java.util.List;

public interface RigurosityService {
    public Rigurosity registerRigurosity(Rigurosity rigurosity);
    public Rigurosity updateRigurosity(Rigurosity rigurosity) throws Exception;
    public Rigurosity deleteRigurosity(Long id) throws Exception;
    public List<Rigurosity> listRigurosities();
}
