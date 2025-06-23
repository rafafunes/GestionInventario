package com.inventario.service;

import org.springframework.stereotype.Service;

import com.inventario.model.Proveedor;
import com.inventario.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService{
	private final ProveedorRepository proveedorRepository;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public Proveedor crearProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
}
