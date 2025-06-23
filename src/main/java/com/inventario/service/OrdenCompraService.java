package com.inventario.service;

import java.util.List;

import com.inventario.dto.OrdenCompraRequestDTO;
import com.inventario.dto.OrdenCompraResponseDTO;
import com.inventario.dto.OrdenPorEstadoDTO;
import com.inventario.model.OrdenCompra;

public interface OrdenCompraService {
	//List<OrdenCompra> listarPorEstado(String estado);
	//List<OrdenPorEstadoDTO> buscarOrdenesPorEstado(String estado);
	List<OrdenCompraResponseDTO> buscarOrdenesPorEstado(String estado);
	
	// Creacion de orden de compra
	OrdenCompra crearOrdenCompra(OrdenCompraRequestDTO dto);
	
	void recibirOrdenCompra(Long id);

}
