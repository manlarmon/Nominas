CREATE TABLE nominas (
    empleado_dni varchar(10) PRIMARY KEY,
    mes INT,
    anio INT,
    sueldo DECIMAL(10, 2),
    FOREIGN KEY (empleado_dni) REFERENCES empleados(dni)
);
