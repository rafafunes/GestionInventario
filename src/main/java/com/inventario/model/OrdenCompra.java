package com.inventario.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orden_compra")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrdenCompra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String estado;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrdenCompraDetalle> detalles;
    
    @ManyToOne
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;

	public OrdenCompra() {
		super();
	}

	public OrdenCompra(Long id, Proveedor proveedor, LocalDate fecha, String estado,
			List<OrdenCompraDetalle> detalles) {
		super();
		this.id = id;
		this.proveedor = proveedor;
		this.fecha = fecha;
		this.estado = estado;
		this.detalles = detalles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<OrdenCompraDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<OrdenCompraDetalle> detalles) {
		this.detalles = detalles;
	}
    
}
