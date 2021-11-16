package com.vivero.controladores;

import com.vivero.repositorios.PlantaRepositorio;
import com.vivero.servicios.PlantaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class PlantaControlador {
    
    @Autowired
    private PlantaServicio plantaServicio;
    
    @Autowired
    private PlantaRepositorio plantaRepositorio;
    
    
}
