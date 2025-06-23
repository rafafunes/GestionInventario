package com.inventario.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.dto.InventarioDTO;
import com.inventario.model.Inventario;
import com.inventario.model.Producto;
import com.inventario.repository.InventarioRepository;

@Service
public class InventarioServiceImpl implements InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    /*public InventarioServiceImpl(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }*/

    //@Transactional(readOnly = true)
    /*@Override
    public List<InventarioDTO> obtenerInventario() {
        List<Inventario> inventarios = inventarioRepository.findAll();
        System.out.println("Inventarios encontrados: " + inventarios.size());
        inventarios.forEach(i -> System.out.println(i.getProducto()));
        return inventarios.stream()
            .map(i -> new InventarioDTO(
                i.getProducto().getCodigo(),
                i.getProducto().getDescripcion(),
                i.getProducto().getPrecioUnitario(),
                i.getCantidadDisponible()))
            .collect(Collectors.toList());
    }*/
    
    /*@Override
    public List<InventarioDTO> obtenerInventario() {
        return inventarioRepository.buscarStockConDTO();
    }*/
    
    @Override
    public List<InventarioDTO> obtenerInventario() {
        List<InventarioDTO> lista = new ArrayList<>();
        for (Inventario i : inventarioRepository.findAll()) {
            try {
                Producto p = i.getProducto();
                lista.add(new InventarioDTO(
                    p.getCodigo(),
                    p.getDescripcion(),
                    p.getPrecioUnitario(),
                    i.getCantidadDisponible()
                ));
            } catch (Exception e) {
                System.out.println("ERROR construyendo DTO: " + e.getMessage());
            }
        }
        return lista;
    }
    
}
