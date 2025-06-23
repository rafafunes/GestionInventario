package com.inventario.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inventario.model.Inventario;
import com.inventario.model.Producto;
import com.inventario.repository.InventarioRepository;
import com.inventario.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {
	private final ProductoRepository productoRepository;
	private final InventarioRepository inventarioRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, InventarioRepository inventarioRepository) {
        this.productoRepository = productoRepository;
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public Producto crearProducto(Producto producto) {
        int cantidad = producto.getCantidadInicial() != null ? producto.getCantidadInicial() : 0;

        // Buscar si ya existe el producto por código
        Optional<Producto> productoExistenteOpt = productoRepository.findByCodigo(producto.getCodigo());

        Producto productoGuardado;

        if (productoExistenteOpt.isPresent()) {
            productoGuardado = productoExistenteOpt.get();

            // Ya existe, actualizar inventario
            Inventario inventario = inventarioRepository.findByProductoId(productoGuardado.getId())
                    .orElseGet(() -> {
                        Inventario nuevoInventario = new Inventario();
                        nuevoInventario.setProducto(productoGuardado);
                        nuevoInventario.setCantidadDisponible(0); // inicia en 0
                        return nuevoInventario;
                    });

            inventario.setCantidadDisponible(inventario.getCantidadDisponible() + cantidad);
            inventarioRepository.save(inventario);

        } else {
            // Producto nuevo, guardarlo
            productoGuardado = productoRepository.save(producto);

            // Crear inventario
            Inventario nuevoInventario = new Inventario();
            nuevoInventario.setProducto(productoGuardado);
            nuevoInventario.setCantidadDisponible(cantidad);
            inventarioRepository.save(nuevoInventario);
        }

        return productoGuardado;
    }
}
