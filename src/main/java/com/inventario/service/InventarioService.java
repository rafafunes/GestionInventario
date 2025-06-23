package com.inventario.service;

import java.util.List;

import com.inventario.dto.InventarioDTO;

public interface InventarioService {
    List<InventarioDTO> obtenerInventario();
}
