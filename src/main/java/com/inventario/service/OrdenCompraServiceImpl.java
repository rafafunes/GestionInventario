package com.inventario.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.dto.OrdenCompraRequestDTO;
import com.inventario.dto.OrdenCompraResponseDTO;
import com.inventario.dto.OrdenCompraResponseDTO.DetalleDTO;
import com.inventario.dto.OrdenCompraResponseDTO.DetalleDTO.ProductoDTO;
import com.inventario.dto.OrdenCompraResponseDTO.ProveedorDTO;
import com.inventario.dto.OrdenPorEstadoDTO;
import com.inventario.model.Inventario;
import com.inventario.model.OrdenCompra;
import com.inventario.model.OrdenCompraDetalle;
import com.inventario.model.Producto;
import com.inventario.model.Proveedor;
import com.inventario.repository.InventarioRepository;
import com.inventario.repository.OrdenCompraRepository;
import com.inventario.repository.ProductoRepository;
import com.inventario.repository.ProveedorRepository;

@Service
public class OrdenCompraServiceImpl implements OrdenCompraService {
    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    
    private ProveedorRepository proveedorRepository;
    
    private final ProductoRepository productoRepository;
    
    private final InventarioRepository inventarioRepository; 

    /*@Override
    public List<OrdenCompra> listarPorEstado(String estado) {
        return ordenCompraRepository.findByEstado(estado);
    }*/
    public OrdenCompraServiceImpl(OrdenCompraRepository ordenCompraRepository,
            ProveedorRepository proveedorRepository,
            ProductoRepository productoRepository,
            InventarioRepository inventarioRepository) {
    	this.ordenCompraRepository = ordenCompraRepository;
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
        this.inventarioRepository = inventarioRepository;
    }

    /*@Override
    public List<OrdenPorEstadoDTO> buscarOrdenesPorEstado(String estado) {
        return ordenCompraRepository.buscarOrdenesPorEstado(estado);
    }*/
    
    @Override
    public List<OrdenCompraResponseDTO> buscarOrdenesPorEstado(String estado) {
        return ordenCompraRepository.findByEstado(estado).stream().map(oc ->
            new OrdenCompraResponseDTO(
                oc.getId(),
                oc.getEstado(),
                oc.getFecha(),
                new ProveedorDTO(
                    oc.getProveedor().getNombre(),
                    oc.getProveedor().getContacto(),
                    oc.getProveedor().getDireccion()
                ),
                oc.getDetalles().stream().map(detalle ->
                    new DetalleDTO(
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario(),
                        new ProductoDTO(detalle.getProducto().getDescripcion())
                    )
                ).collect(Collectors.toList())
            )
        ).collect(Collectors.toList());
    }
    
    // Crear orden de compra
    @Override
    public OrdenCompra crearOrdenCompra(OrdenCompraRequestDTO dto) {
    	
    	// Validar que orden de compra no sea ingresada sin productos
    	if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La orden de compra debe contener al menos un producto.");
        }
    	
        Proveedor proveedor = proveedorRepository.findById(dto.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        OrdenCompra orden = new OrdenCompra();
        orden.setProveedor(proveedor);
        orden.setEstado(dto.getEstado());
        orden.setFecha(LocalDate.now());

        List<OrdenCompraDetalle> detalles = new ArrayList<>();
        for (OrdenCompraRequestDTO.DetalleDTO d : dto.getDetalles()) {
            Producto producto = productoRepository.findById(d.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            OrdenCompraDetalle detalle = new OrdenCompraDetalle();
            detalle.setProducto(producto);
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnitario());
            detalle.setOrden(orden); // Asignar orden al detalle

            detalles.add(detalle);
        }

        orden.setDetalles(detalles);

        return ordenCompraRepository.save(orden);
    }
    
    // Marcar orden como RECIBIDA y actualizar inventario
    @Override
    public void recibirOrdenCompra(Long id) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con id: " + id));

        if ("RECIBIDA".equalsIgnoreCase(orden.getEstado())) {
            throw new IllegalStateException("La orden ya fue marcada como recibida.");
        }

        orden.setEstado("RECIBIDA");

        for (OrdenCompraDetalle detalle : orden.getDetalles()) {
            Producto producto = detalle.getProducto();
            int cantidad = detalle.getCantidad();

            Inventario inventario = inventarioRepository.findByProductoId(producto.getId())
                    .orElseGet(() -> {
                        Inventario nuevoInventario = new Inventario();
                        nuevoInventario.setProducto(producto);
                        nuevoInventario.setCantidadDisponible(0);
                        return nuevoInventario;
                    });

            inventario.setCantidadDisponible(inventario.getCantidadDisponible() + cantidad);
            inventarioRepository.save(inventario);
        }

        ordenCompraRepository.save(orden);
    }

    
}
