package com.vivero.repositorios;

import com.vivero.entidades.Foto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

public interface FotoRepositorio extends JpaRepository<Foto, String> {

    public Foto save(List<MultipartFile> galeria);

    //cambios introducidos por JG para traer lista de fotos
    @Query("select c from Foto c where c.producto.id like :id ")
    public List<Foto> listaFotosDeProducto(String id);
}
