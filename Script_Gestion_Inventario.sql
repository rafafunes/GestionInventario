-- Tabla proveedor
CREATE TABLE proveedor (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100),
    direccion TEXT
);

-- Tabla producto
CREATE TABLE producto (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    descripcion TEXT NOT NULL,
    precio_unitario NUMERIC(10, 2) NOT NULL CHECK (precio_unitario >= 0)
);

-- Tabla orden_compra
CREATE TABLE orden_compra (
    id SERIAL PRIMARY KEY,
    proveedor_id INTEGER NOT NULL REFERENCES proveedor(id) ON DELETE RESTRICT,
    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
    estado VARCHAR(20) NOT NULL DEFAULT 'pendiente'
);

-- Tabla orden_compra_detalle
CREATE TABLE orden_compra_detalle (
    id SERIAL PRIMARY KEY,
    orden_id INTEGER NOT NULL REFERENCES orden_compra(id) ON DELETE CASCADE,
    producto_id INTEGER NOT NULL REFERENCES producto(id) ON DELETE RESTRICT,
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(10, 2) NOT NULL CHECK (precio_unitario >= 0)
);

-- Tabla inventario
CREATE TABLE inventario (
    producto_id INTEGER PRIMARY KEY REFERENCES producto(id) ON DELETE CASCADE,
    cantidad_disponible INTEGER NOT NULL DEFAULT 0 CHECK (cantidad_disponible >= 0)
);

-- Evita que el mismo producto se repita en una misma orden
CREATE UNIQUE INDEX idx_orden_producto_unico
ON orden_compra_detalle (orden_id, producto_id);


-- INSERTS DE PRUEBA
-- proveedor
INSERT INTO proveedor (nombre, contacto, direccion) VALUES
('Distribuidora Central', 'Carlos López', 'Calle 5, San Salvador'),
('Importaciones del Norte', 'Ana Pérez', 'Av. Independencia, Santa Ana');

-- producto
INSERT INTO producto (codigo, descripcion, precio_unitario) VALUES
('P001', 'Laptop HP 14 pulg.', 599.99),
('P002', 'Mouse Logitech', 19.95),
('P003', 'Teclado mecánico RedDragon', 45.50);

-- orden_compra
INSERT INTO orden_compra (proveedor_id, fecha, estado) VALUES
(1, '2025-06-20', 'pendiente'),
(2, '2025-06-21', 'aprobada');

-- orden_compra_detalle
INSERT INTO orden_compra_detalle (orden_id, producto_id, cantidad, precio_unitario) VALUES
(1, 1, 5, 580.00),  -- Laptop HP 14 pulg
(1, 2, 10, 18.00),  -- Mouse Logitech
(2, 3, 4, 43.00);   -- Teclado mecánico

-- inventario
INSERT INTO inventario (producto_id, cantidad_disponible) VALUES
(1, 20),
(2, 50),
(3, 15);

-- Funcion para generacion de reporte de compras de un proveedor, entre fechas
CREATE OR REPLACE FUNCTION reporte_compras(
    p_proveedor_id INT,
    p_desde DATE,
    p_hasta DATE
) RETURNS JSONB AS $$
DECLARE
    resultado JSONB;
BEGIN
    SELECT jsonb_agg(orden)
    INTO resultado
    FROM (
        SELECT
            oc.id AS orden_id,
            oc.fecha,
            oc.estado,
            jsonb_agg(
                jsonb_build_object(
                    'codigo_producto', prod.codigo,
                    'descripcion', prod.descripcion,
                    'cantidad', ocd.cantidad,
                    'precio_unitario', ocd.precio_unitario,
                    'subtotal', ocd.cantidad * ocd.precio_unitario
                )
            ) AS productos,
            SUM(ocd.cantidad * ocd.precio_unitario) AS total_orden
        FROM orden_compra oc
        JOIN orden_compra_detalle ocd ON oc.id = ocd.orden_id
        JOIN producto prod ON prod.id = ocd.producto_id
        WHERE oc.proveedor_id = p_proveedor_id
          AND oc.fecha BETWEEN p_desde AND p_hasta
        GROUP BY oc.id, oc.fecha, oc.estado
        ORDER BY oc.fecha
    ) AS orden;

    RETURN COALESCE(resultado, '[]'::jsonb);
END;
$$ LANGUAGE plpgsql;


-- Creacion de funcion para el trigger
CREATE OR REPLACE FUNCTION evitar_modificacion_orden_recibida()
RETURNS TRIGGER AS $$
BEGIN
    -- Verifica si el estado actual en la base de datos es 'RECIBIDA'
    IF (SELECT estado FROM orden_compra WHERE id = NEW.id) = 'RECIBIDA' THEN
        RAISE EXCEPTION 'La orden % ya fue recibida y no puede ser modificada.', NEW.id;
    END IF;

    RETURN NEW; -- Permite la actualización si no es 'RECIBIDA'
END;
$$ LANGUAGE plpgsql;

-- Creacion del trigger
CREATE TRIGGER trg_prevent_update_orden_recibida
BEFORE UPDATE ON orden_compra
FOR EACH ROW
EXECUTE FUNCTION evitar_modificacion_orden_recibida();
