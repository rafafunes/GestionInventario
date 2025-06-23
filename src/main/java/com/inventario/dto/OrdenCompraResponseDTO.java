package com.inventario.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrdenCompraResponseDTO {
	private Long id;
    private String estado;
    private LocalDate fecha;
    private ProveedorDTO proveedor;
    private List<DetalleDTO> detalles;

    public OrdenCompraResponseDTO(Long id, String estado, LocalDate fecha, ProveedorDTO proveedor, List<DetalleDTO> detalles) {
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.detalles = detalles;
    }

    public static class ProveedorDTO {
        private String nombre;
        private String contacto;
        private String direccion;

        public ProveedorDTO(String nombre, String contacto, String direccion) {
            this.nombre = nombre;
            this.contacto = contacto;
            this.direccion = direccion;
        }

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
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

    }

    public static class DetalleDTO {
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private ProductoDTO producto;

        public DetalleDTO(Integer cantidad, BigDecimal precioUnitario, ProductoDTO producto) {
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.producto = producto;
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

		public ProductoDTO getProducto() {
			return producto;
		}

		public void setProducto(ProductoDTO producto) {
			this.producto = producto;
		}

		public static class ProductoDTO {
            private String descripcion;

            public ProductoDTO(String descripcion) {
                this.descripcion = descripcion;
            }

			public String getDescripcion() {
				return descripcion;
			}

			public void setDescripcion(String descripcion) {
				this.descripcion = descripcion;
			}
            
        }
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

	public ProveedorDTO getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedorDTO proveedor) {
		this.proveedor = proveedor;
	}

	public List<DetalleDTO> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleDTO> detalles) {
		this.detalles = detalles;
	}

}
