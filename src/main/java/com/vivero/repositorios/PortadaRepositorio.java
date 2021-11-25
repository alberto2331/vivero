package com.vivero.repositorios;


import com.vivero.entidades.Portada;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

public interface PortadaRepositorio extends JpaRepository <Portada, String>{

    public Portada save(List<MultipartFile> foto);

}