package com.inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.dto.InventarioDTO;
import com.inventario.dto.OrdenCompraRequestDTO;
import com.inventario.dto.OrdenCompraResponseDTO;
import com.inventario.dto.OrdenPorEstadoDTO;
import com.inventario.model.Inventario;
import com.inventario.model.OrdenCompra;
import com.inventario.model.Producto;
import com.inventario.model.Proveedor;
import com.inventario.service.InventarioService;
import com.inventario.service.OrdenCompraService;
import com.inventario.service.ProductoService;
import com.inventario.service.ProveedorService;

@RestController
@RequestMapping("/api")
public class InventarioOrdenCompraController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private OrdenCompraService ordenCompraService;
    
    @Autowired
    private ProveedorService proveedorService;
    
    @Autowired
    private ProductoService productoService;
    
    /*public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }*/
    
    /*public OrdenCompraController(OrdenCompraService ordenCompraService) {
        this.ordenCompraService = ordenCompraService;
    }*/

    @GetMapping("/inventario")
    public List<InventarioDTO> getInventario() {
        //return inventarioService.obtenerInventario();
    	List<InventarioDTO> lista = inventarioService.obtenerInventario();
        System.out.println("Respuesta inventario: " + lista);
        return lista;
    }

    /*@GetMapping("/ordenes-compra")
    public List<OrdenPorEstadoDTO> getOrdenesPorEstado(@RequestParam String estado) {
        return ordenCompraService.buscarOrdenesPorEstado(estado);
    }*/
    
    @GetMapping("/ordenes-compra")
    public List<OrdenCompraResponseDTO> getOrdenesPorEstado(@RequestParam String estado) {
        return ordenCompraService.buscarOrdenesPorEstado(estado);
    }
    
 // POST /api/proveedores
    @PostMapping("/crear-proveedor")
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor) {
        Proveedor nuevo = proveedorService.crearProveedor(proveedor);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }
    
 // POST /api/productos
    @PostMapping("/crear-producto")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevo = productoService.crearProducto(producto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }
    
    // Crear orden de compra
    @PostMapping("/crear-orden-compra")
    public ResponseEntity<OrdenCompra> crearOrdenCompra(@RequestBody OrdenCompraRequestDTO dto) {
        OrdenCompra orden = ordenCompraService.crearOrdenCompra(dto);
        return new ResponseEntity<>(orden, HttpStatus.CREATED);
    }
    
    // Marcar orden como RECIBIDA y actualizar inventario
    @PostMapping("/ordenes-compra/{id}/recibir")
    public ResponseEntity<String> recibirOrden(@PathVariable Long id) {
        ordenCompraService.recibirOrdenCompra(id);
        return ResponseEntity.ok("Orden marcada como RECIBIDA y stock actualizado.");
    }

    
}
