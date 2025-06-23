package com.inventario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventario.dto.InventarioDTO;
import com.inventario.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

	@Query("SELECT new com.inventario.dto.InventarioDTO(p.codigo, p.descripcion, p.precioUnitario, i.cantidadDisponible) " +
	           "FROM Inventario i JOIN i.producto p")
	    List<InventarioDTO> buscarStockConDTO();
	
	Optional<Inventario> findByProductoId(Long productoId);
	
}
