package com.inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventario.dto.OrdenPorEstadoDTO;
import com.inventario.model.OrdenCompra;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    List<OrdenCompra> findByEstado(String estado);
	
	/*@Query("SELECT new com.inventario.dto.OrdenPorEstadoDTO(" +
	           "oc.id, oc.estado, oc.fecha, d.cantidad, pd.descripcion, d.precioUnitario, pr.contacto, pr.direccion, pr.nombre) " +
	           "FROM OrdenCompra oc " +
	           "JOIN oc.detalles d " +
	           "JOIN oc.proveedor pr " +
	           "JOIN d.producto pd " +
	           "WHERE oc.estado = :estado")
		List<OrdenPorEstadoDTO> buscarOrdenesPorEstado(@Param("estado") String estado);*/
	
}
