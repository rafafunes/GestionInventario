package com.inventario.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrdenPorEstadoDTO {
	private Long id;
    private String estado;
    private LocalDate fecha;
    private Integer cantidad;
    private String descripcion;
    private BigDecimal precioUnitario;
    private String contacto;
    private String direccion;
    private String nombre;

    // ✅ ESTE es el constructor que exige la query
    public OrdenPorEstadoDTO(Long id, String estado, LocalDate fecha,
                             Integer cantidad, String descripcion, BigDecimal precioUnitario,
                             String contacto, String direccion, String nombre) {
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.contacto = contacto;
        this.direccion = direccion;
        this.nombre = nombre;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
