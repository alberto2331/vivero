package com.vivero.repositorios;

import com.vivero.entidades.Foto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

public interface FotoRepositorio extends JpaRepository<Foto, String> {

    public Foto save(List<MultipartFile> galeria);

<<<<<<< HEAD
    //Antes de hacer el push preguntar si dejamos este o el de juan
    //cambios introducidos por JG para traer lista de fotos Dejar los cambios de JG
=======
    //cambios introducidos por JG para traer lista de fotos
>>>>>>> frontend
    @Query("select c from Foto c where c.producto.id like :id ")
    public List<Foto> listaFotosDeProducto(String id);
}
