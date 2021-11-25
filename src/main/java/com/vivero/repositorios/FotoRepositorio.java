package com.vivero.repositorios;

import com.vivero.entidades.Foto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

public interface FotoRepositorio extends JpaRepository <Foto, String>{

    public Foto save(List<MultipartFile> galeria);

}
