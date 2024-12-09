-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-12-2024 a las 21:09:20
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `plasticosdb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `almacenes`
--

CREATE TABLE `almacenes` (
  `id_almacen` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  `tipo` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `almacenes`
--

INSERT INTO `almacenes` (`id_almacen`, `nombre`, `ubicacion`, `tipo`) VALUES
(1, 'Almacen Sellado', 'Planta Inferior', 'B'),
(2, 'Almacen Produccion', 'Planta Superior', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `nombre`, `apellido`, `dni`, `correo`, `telefono`, `direccion`) VALUES
(1, 'JORGE', 'CHOQUE', '75974046', 'jorge@gmail.com', '928278875', 'AA HH VIV Taller buena vista mz L lote 4'),
(2, 'Osito', 'Puma', '98657541', 'Osito@gmail.com', '786158742', 'AA HH VIV Taller buena vista mz L lote 4'),
(3, 'carlos', 'Huanca', '11111111', 'carlitos@gmail.com', '928278875', 'AA HH VIV Taller buena vista mz L lote 4'),
(4, 'Maya', 'Puma', '22222222', 'maya@gmail.com', '928278875', 'AA HH VIV Taller buena vista mz L lote 4'),
(5, 'siruis', 'black', '33333333', 'siruis@gmail.com', '333333333', 'AA HH VIV Taller buena vista mz L lote 4'),
(6, 'Jorge', 'Martínez', '75974041', 'jorge.martinez@example.com', '928278875', 'Calle Falsa 123, Lima'),
(7, 'Ana', 'Gómez', '75974047', 'ana.gomez@example.com', '928278876', 'Av. Siempreviva 742, Arequipa'),
(8, 'Carlos', 'Pérez', '75974048', 'carlos.perez@example.com', '928278877', 'Jr. Puno 512, Cusco'),
(9, 'Lucía', 'Fernández', '75974049', 'lucia.fernandez@example.com', '928278878', 'Av. Los Rosales 856, Trujillo'),
(10, 'Pedro', 'Ramírez', '75974050', 'pedro.ramirez@example.com', '928278879', 'Calle Las Flores 321, Chiclayo'),
(11, 'María', 'López', '75974051', 'maria.lopez@example.com', '928278880', 'Av. Perú 123, Lima'),
(12, 'Juan', 'Torres', '75974052', 'juan.torres@example.com', '928278881', 'Jr. Huancavelica 456, Arequipa'),
(13, 'Laura', 'Mendoza', '75974053', 'laura.mendoza@example.com', '928278882', 'Calle Primavera 789, Cusco'),
(14, 'José', 'Cruz', '75974054', 'jose.cruz@example.com', '928278883', 'Av. Libertad 101, Trujillo'),
(15, 'Sandra', 'Ruiz', '75974055', 'sandra.ruiz@example.com', '928278884', 'Jr. San Martín 202, Chiclayo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallesventas`
--

CREATE TABLE `detallesventas` (
  `id_detalle` int(11) NOT NULL,
  `id_venta` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `producto` varchar(100) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `tiempo` int(11) NOT NULL,
  `precio` double NOT NULL,
  `estado` varchar(20) DEFAULT 'En Proceso'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detallesventas`
--

INSERT INTO `detallesventas` (`id_detalle`, `id_venta`, `id_producto`, `producto`, `cantidad`, `tiempo`, `precio`, `estado`) VALUES
(1, 1, 1, 'BOLSAS DE BASURA', 500, 7, 30, 'Terminado'),
(8, 9, 1, 'BOLSAS DE BASURA', 200, 7, 10, 'Terminado'),
(9, 10, 1, 'BOLSAS DE BASURAS', 100, 7, 20, 'Terminado'),
(10, 11, 1, 'MANGUERAS', 100, 10, 50, 'Terminado'),
(15, 3, 2, 'CARACOLES', 500, 7, 30, 'Terminado'),
(16, 15, 2, 'caracoles', 10, 7, 10, 'Terminado'),
(17, 16, 2, 'caracoles', 10, 7, 10, 'Terminado'),
(18, 17, 6, 'Botellas PET', 1000, 7, 50, 'Terminado'),
(19, 18, 7, 'Film Plástico', 1000, 7, 20, 'Terminado'),
(20, 19, 3, 'Cajas Plásticas', 1000, 10, 10, 'Terminado'),
(21, 20, 5, 'Envases', 1000, 10, 12, 'Terminado'),
(22, 21, 12, 'Tubos de PVC', 100, 10, 12, 'Terminado'),
(23, 22, 10, 'Tazas Plásticas', 50, 3, 10, 'Terminado'),
(24, 23, 1, 'BOLSAS DE BASURA', 100, 10, 20, 'Terminado'),
(25, 24, 12, 'Tubos de PVC', 1000, 20, 10, 'Terminado'),
(26, 25, 2, 'caracoles', 1000, 10, 20, 'Terminado'),
(27, 26, 3, 'Cajas Plásticas', 1000, 10, 20, 'Terminado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materiasprimas`
--

CREATE TABLE `materiasprimas` (
  `id_materia_prima` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `cantidad` double NOT NULL,
  `tipo_materia` enum('reciclada','comprada') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `materiasprimas`
--

INSERT INTO `materiasprimas` (`id_materia_prima`, `nombre`, `tipo`, `cantidad`, `tipo_materia`) VALUES
(1, 'Granito azul', 'A', 500, 'comprada'),
(2, 'Granito Verde', 'B', 1500, 'comprada'),
(3, 'Granito rojo', 'C', 2000, 'comprada'),
(5, 'celeste', 'E', 2000, 'comprada'),
(6, 'Granito blanco', 'X', 200, 'comprada'),
(7, 'Nylon (Poliamida)', 'Gránulo', 1500, 'reciclada'),
(8, 'Granito T', 'T', 650, 'reciclada'),
(9, 'Policarbonato (PC)', 'Gránulo', 2200, 'reciclada'),
(10, 'Poliacetal (POM)', 'Gránulo', 2800, 'reciclada'),
(11, 'Polietileno de Alta Densidad (HDPE)', 'Gránulo', 5000, 'comprada'),
(12, 'Polipropileno (PP)', 'Gránulo', 3000, 'comprada'),
(13, 'Poliestireno (PS)', 'Gránulo', 2000, 'comprada'),
(14, 'Polietileno Tereftalato (PET)', 'Gránulo', 4000, 'comprada'),
(15, 'Polietileno de Baja Densidad (LDPE)', 'Gránulo', 3500, 'comprada'),
(16, 'PVC (Cloruro de Polivinilo)', 'Gránulo', 2500, 'reciclada'),
(17, 'ABS (Acrilonitrilo Butadieno Estireno)', 'Gránulo', 1800, 'reciclada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos_almacen`
--

CREATE TABLE `movimientos_almacen` (
  `id_movimiento` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad` double NOT NULL,
  `fecha_movimiento` varchar(50) NOT NULL,
  `id_almacen` int(11) NOT NULL,
  `estado` varchar(20) DEFAULT 'Pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `movimientos_almacen`
--

INSERT INTO `movimientos_almacen` (`id_movimiento`, `id_producto`, `cantidad`, `fecha_movimiento`, `id_almacen`, `estado`) VALUES
(1, 2, 1000, '2024-12-05', 1, 'Completada'),
(2, 2, 10000, '2024-12-05', 1, 'Pendiente'),
(3, 1, 5000, '2024-12-13', 2, 'Completada'),
(4, 2, 1000, '2024-12-05', 2, 'Completada'),
(5, 1, 9999, '2024-12-05', 2, 'Completada'),
(6, 1, 1000, '2024-12-05', 2, 'Completada'),
(7, 7, 1000, '2024-12-07', 2, 'Completada'),
(8, 9, 10000, '2024-12-07', 2, 'Completada'),
(9, 10, 1000, '2024-12-07', 1, 'Completada'),
(10, 6, 1000, '2024-12-07', 2, 'Completada'),
(11, 6, 1000, '2024-12-07', 1, 'Pendiente'),
(12, 2, 1000, '2024-12-07', 1, 'Completada'),
(13, 12, 1000, '2024-12-09', 2, 'Completada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenesproduccion`
--

CREATE TABLE `ordenesproduccion` (
  `id_orden` int(11) NOT NULL,
  `tipo_material` int(11) NOT NULL,
  `trabajador` int(11) NOT NULL,
  `tiempo_estimado` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `estado` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ordenesproduccion`
--

INSERT INTO `ordenesproduccion` (`id_orden`, `tipo_material`, `trabajador`, `tiempo_estimado`, `fecha`, `estado`) VALUES
(1, 3, 6, 4, '2024-12-09', 'Completada'),
(2, 2, 6, 7, '2024-12-05', 'Completada'),
(3, 2, 6, 7, '2024-12-05', 'Completada'),
(4, 1, 7, 8, '2024-12-07', 'Completada'),
(5, 2, 9, 7, '2024-12-03', 'Completada'),
(6, 8, 9, 8, '2024-12-03', 'Completada'),
(7, 5, 9, 5, '2024-12-02', 'Completada'),
(8, 2, 7, 7, '2024-12-07', 'Completada'),
(9, 2, 10, 8, '2024-12-07', 'Completada'),
(10, 15, 9, 7, '2024-12-07', 'Completada'),
(11, 13, 9, 7, '2024-12-07', 'Completada'),
(12, 15, 10, 10, '2024-12-07', 'Completada'),
(13, 13, 7, 10, '2024-12-07', 'Completada'),
(14, 6, 10, 10, '2024-12-07', 'Pendiente'),
(15, 3, 10, 10, '2024-12-07', 'Completada'),
(16, 12, 9, 10, '2024-12-09', 'Completada'),
(17, 3, 7, 10, '2024-12-09', 'Pendiente'),
(18, 8, 7, 10, '2024-12-09', 'Pendiente'),
(19, 5, 7, 10, '2024-12-09', 'Pendiente'),
(20, 7, 9, 10, '2024-12-09', 'Completada'),
(21, 2, 7, 10, '2024-12-09', 'Pendiente'),
(22, 16, 6, 10, '2024-12-09', 'Pendiente'),
(23, 17, 6, 10, '2024-12-09', 'Pendiente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenessellado`
--

CREATE TABLE `ordenessellado` (
  `id_orden_sellado` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `medida_bolsa` varchar(50) NOT NULL,
  `cantidad_a_sellar` int(11) NOT NULL,
  `fecha_orden` varchar(50) NOT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'Pendiente',
  `tiempo_estimado` int(11) DEFAULT NULL,
  `id_orden` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ordenessellado`
--

INSERT INTO `ordenessellado` (`id_orden_sellado`, `id_usuario`, `medida_bolsa`, `cantidad_a_sellar`, `fecha_orden`, `estado`, `tiempo_estimado`, `id_orden`) VALUES
(2, 9, '20 x 30', 1000, '2024-12-05', 'Completada', 7, 6),
(5, 9, '20 x 30', 1000, '2024-12-05', 'Completada', 7, 3),
(7, 9, '20 x 30', 1000, '2024-12-05', 'Completada', 7, 3),
(8, 9, '20 x 45', 500, '2024-12-20', 'Completada', 10, 6),
(9, 6, '70 X 90', 1000, '2024-12-05', 'Completada', 7, 1),
(10, 6, '70 X 90', 1000, '2024-12-07', 'Completada', 10, 11),
(11, 6, '20 x 45', 500, '2024-12-07', 'Pendiente', 10, 11),
(12, 9, '20 x 30', 500, '2024-12-07', 'Completada', 10, 12),
(13, 9, '20 x 45', 500, '2024-12-07', 'Completada', 10, 13),
(14, 6, '20 x 45', 500, '2024-12-07', 'Pendiente', 10, 8),
(15, 9, '20 x 45', 500, '2024-12-07', 'Pendiente', 10, 13),
(16, 10, '20 x 45', 500, '2024-12-07', 'Completada', 10, 15),
(17, 9, '20 x 45', 500, '2024-12-09', 'Pendiente', 10, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_producto` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `tipo` varchar(50) NOT NULL,
  `cantidad` double NOT NULL,
  `almacen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id_producto`, `nombre`, `descripcion`, `tipo`, `cantidad`, `almacen`) VALUES
(1, 'BOLSAS DE BASURA', 'BOLSAS RECICLADAS', 'A', 500, 1),
(2, 'caracoles', 'caracoles muertos', 'C', 2000, 1),
(3, 'Cajas Plásticas', 'Cajas plásticas para almacenamiento', 'C', 1500, 1),
(4, 'Tapas de Botellas', 'Tapas plásticas para botellas', 'A', 2000, 1),
(5, 'Envases', 'Envases plásticos para alimentos', 'B', 1200, 2),
(6, 'Botellas PET', 'Botellas plásticas para bebidas', 'C', 2500, 2),
(7, 'Film Plástico', 'Film plástico para embalaje', 'A', 3000, 1),
(8, 'Tuberías de Polietileno', 'Tuberías para conducciones de gas', 'B', 1800, 2),
(9, 'Bidones', 'Bidones plásticos para líquidos', 'C', 2200, 2),
(10, 'Tazas Plásticas', 'Tazas plásticas para el hogar', 'A', 1700, 1),
(11, 'Bolsas de Basura', 'Bolsas recicladas de alta resistencia', 'A', 500, 1),
(12, 'Tubos de PVC', 'Tubos de PVC para instalaciones sanitarias', 'B', 1000, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos_materiasprimas`
--

CREATE TABLE `productos_materiasprimas` (
  `id` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `id_materia_prima` int(11) NOT NULL,
  `cantidad_requerida` double NOT NULL,
  `tipo_materia` enum('reciclada','comprada') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `id_proveedor` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `contacto` varchar(100) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`id_proveedor`, `nombre`, `contacto`, `telefono`, `direccion`) VALUES
(1, 'Monolitos S.A.C', 'Gianela', '987654321', 'AA HH VIV Taller buena vista mz L lote 4'),
(2, 'La neta S.A.C', 'carlos', '928278875', 'AA HH VIV Taller buena vista mz L lote 4'),
(3, 'Nac S.A.C', 'KELVIN', '928278875', 'AA HH VIV Taller buena vista mz L lote 4'),
(4, 'Polímeros del Sur', 'Lucía Torres', '934567890', 'Av. Bolívar 654, Trujillo'),
(5, 'Materias Primas S.R.L.', 'Jorge Fernández', '945678901', 'Jr. Tarapacá 987, Chiclayo'),
(6, 'Fábrica de Plásticos Andes', 'Daniela Castro', '935678901', 'Av. Los Incas 123, Lima'),
(7, 'Distribuidora de Resinas', 'Miguel Rojas', '936789012', 'Jr. Ayacucho 456, Arequipa'),
(8, 'Química del Sur', 'Verónica Salazar', '937890123', 'Av. Universitaria 789, Cusco'),
(9, 'Plásticos del Norte', 'Eduardo Paredes', '938901234', 'Jr. Moquegua 101, Trujillo'),
(10, 'PoliPlastic S.A.', 'Beatriz Silva', '939012345', 'Av. Aviación 202, Chiclayo'),
(11, 'Plásticos S.A.', 'Carlos Sánchez', '987654321', 'Av. Industrial 456, Lima'),
(12, 'Reciclajes Peruanos', 'Ana López', '912345678', 'Jr. Amazonas 789, Arequipa'),
(13, 'Químicos Industriales', 'Fernando Gómez', '923456789', 'Av. Tupac Amaru 321, Cusco');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores_materiasprimas`
--

CREATE TABLE `proveedores_materiasprimas` (
  `id` int(11) NOT NULL,
  `id_proveedor` int(11) NOT NULL,
  `id_materia_prima` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `tiempo_entrega` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedores_materiasprimas`
--

INSERT INTO `proveedores_materiasprimas` (`id`, `id_proveedor`, `id_materia_prima`, `precio`, `tiempo_entrega`) VALUES
(1, 1, 3, 10.00, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id_rol` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id_rol`, `nombre`) VALUES
(1, 'Administrador'),
(2, 'Trabajador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `id_rol` int(11) NOT NULL,
  `contrasena` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `apellido`, `correo`, `telefono`, `id_rol`, `contrasena`) VALUES
(5, 'KELVIN', 'CHAMBILLA PUMA', 'fridman45y@gmail.com', '928278875', 1, '123'),
(6, 'DARWIN', 'CHAMBILLA PUMA', 'darwin@gmail.com', '928278877', 2, '123'),
(7, 'ken', 'Huaman', 'ken@gmail.com', '928278875', 2, '123'),
(8, 'ada', 'hugarte', 'ada@gmail.com', '928278875', 1, '123'),
(9, 'brenda', 'x', 'brenda@gmail.com', '928278875', 2, '123'),
(10, 'pedro', 'huanca', 'pedro@gmail.com', '789456123', 2, '123');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_venta` int(11) NOT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `dni_cliente` varchar(15) NOT NULL,
  `fecha_venta` varchar(255) DEFAULT NULL,
  `total` double NOT NULL,
  `subtotal` double NOT NULL,
  `igv` double NOT NULL,
  `fecha_registro` date NOT NULL,
  `estado` varchar(20) DEFAULT 'Pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id_venta`, `id_cliente`, `dni_cliente`, `fecha_venta`, `total`, `subtotal`, `igv`, `fecha_registro`, `estado`) VALUES
(1, NULL, '98657541', '2024-11-26', 2360, 2000, 360, '2024-11-26', 'En Proceso'),
(3, NULL, '98657541', '2024-11-27', 5900, 5000, 900, '2024-11-27', 'En Proceso'),
(9, 1, '75974046', '2024-11-27', 2360, 2000, 360, '2024-11-27', 'En Proceso'),
(10, 2, '98657541', '2024-11-27', 2360, 2000, 360, '2024-11-27', 'En Proceso'),
(11, 2, '98657541', '2024-11-27', 5900, 5000, 900, '2024-11-27', 'En Proceso'),
(15, 5, '33333333', '2024-12-02', 118, 100, 18, '2024-12-02', 'En Proceso'),
(16, 3, '11111111', '2024-12-03', 118, 100, 18, '2024-12-02', 'En Proceso'),
(17, 7, '75974047', '2024-12-05', 59000, 50000, 9000, '2024-12-05', 'En Proceso'),
(18, 12, '75974052', '2024-12-07', 23600, 20000, 3600, '2024-12-07', 'En Proceso'),
(19, 15, '75974055', '2024-12-07', 11800, 10000, 1800, '2024-12-07', 'En Proceso'),
(20, 14, '75974054', '2024-12-07', 14160, 12000, 2160, '2024-12-07', 'En Proceso'),
(21, 13, '75974053', '2024-12-07', 1416, 1200, 216, '2024-12-07', 'En Proceso'),
(22, 7, '75974047', '2024-12-07', 590, 500, 90, '2024-12-07', 'En Proceso'),
(23, 10, '75974050', '2024-12-07', 2360, 2000, 360, '2024-12-07', 'En Proceso'),
(24, 12, '75974052', '2024-12-07', 11800, 10000, 1800, '2024-12-07', 'En Proceso'),
(25, 15, '75974055', '2024-12-07', 23600, 20000, 3600, '2024-12-07', 'Completada'),
(26, 4, '22222222', '2024-12-09', 23600, 20000, 3600, '2024-12-09', 'En Proceso');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `almacenes`
--
ALTER TABLE `almacenes`
  ADD PRIMARY KEY (`id_almacen`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `detallesventas`
--
ALTER TABLE `detallesventas`
  ADD PRIMARY KEY (`id_detalle`),
  ADD KEY `fk_detalles_ventas_producto` (`id_producto`),
  ADD KEY `fk_detalles_ventas_venta` (`id_venta`);

--
-- Indices de la tabla `materiasprimas`
--
ALTER TABLE `materiasprimas`
  ADD PRIMARY KEY (`id_materia_prima`);

--
-- Indices de la tabla `movimientos_almacen`
--
ALTER TABLE `movimientos_almacen`
  ADD PRIMARY KEY (`id_movimiento`),
  ADD KEY `fk_movimientos_almacen_producto` (`id_producto`),
  ADD KEY `fk_movimientos_almacen_almacen` (`id_almacen`);

--
-- Indices de la tabla `ordenesproduccion`
--
ALTER TABLE `ordenesproduccion`
  ADD PRIMARY KEY (`id_orden`),
  ADD KEY `tipo_material` (`tipo_material`),
  ADD KEY `trabajador` (`trabajador`);

--
-- Indices de la tabla `ordenessellado`
--
ALTER TABLE `ordenessellado`
  ADD PRIMARY KEY (`id_orden_sellado`),
  ADD KEY `fk_ordenessellado_usuario` (`id_usuario`),
  ADD KEY `fk_ordenessellado_ordenproduccion` (`id_orden`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `fk_productos_almacen` (`almacen`);

--
-- Indices de la tabla `productos_materiasprimas`
--
ALTER TABLE `productos_materiasprimas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_productos_materiasprimas_producto` (`id_producto`),
  ADD KEY `fk_productos_materiasprimas_materia_prima` (`id_materia_prima`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`id_proveedor`);

--
-- Indices de la tabla `proveedores_materiasprimas`
--
ALTER TABLE `proveedores_materiasprimas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_proveedores_materiasprimas_proveedor` (`id_proveedor`),
  ADD KEY `fk_proveedores_materiasprimas_materia_prima` (`id_materia_prima`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `fk_usuarios_rol` (`id_rol`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id_venta`),
  ADD KEY `fk_ventas_cliente` (`id_cliente`),
  ADD KEY `fk_ventas_dni_cliente` (`dni_cliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `almacenes`
--
ALTER TABLE `almacenes`
  MODIFY `id_almacen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `detallesventas`
--
ALTER TABLE `detallesventas`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `materiasprimas`
--
ALTER TABLE `materiasprimas`
  MODIFY `id_materia_prima` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `movimientos_almacen`
--
ALTER TABLE `movimientos_almacen`
  MODIFY `id_movimiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `ordenesproduccion`
--
ALTER TABLE `ordenesproduccion`
  MODIFY `id_orden` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `ordenessellado`
--
ALTER TABLE `ordenessellado`
  MODIFY `id_orden_sellado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `productos_materiasprimas`
--
ALTER TABLE `productos_materiasprimas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id_proveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `proveedores_materiasprimas`
--
ALTER TABLE `proveedores_materiasprimas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id_rol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detallesventas`
--
ALTER TABLE `detallesventas`
  ADD CONSTRAINT `fk_detalles_ventas_producto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`),
  ADD CONSTRAINT `fk_detalles_ventas_venta` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id_venta`);

--
-- Filtros para la tabla `movimientos_almacen`
--
ALTER TABLE `movimientos_almacen`
  ADD CONSTRAINT `fk_movimientos_almacen_almacen` FOREIGN KEY (`id_almacen`) REFERENCES `almacenes` (`id_almacen`),
  ADD CONSTRAINT `fk_movimientos_almacen_producto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`);

--
-- Filtros para la tabla `ordenesproduccion`
--
ALTER TABLE `ordenesproduccion`
  ADD CONSTRAINT `ordenesproduccion_ibfk_1` FOREIGN KEY (`tipo_material`) REFERENCES `materiasprimas` (`id_materia_prima`),
  ADD CONSTRAINT `ordenesproduccion_ibfk_2` FOREIGN KEY (`trabajador`) REFERENCES `usuarios` (`id_usuario`);

--
-- Filtros para la tabla `ordenessellado`
--
ALTER TABLE `ordenessellado`
  ADD CONSTRAINT `fk_ordenessellado_ordenproduccion` FOREIGN KEY (`id_orden`) REFERENCES `ordenesproduccion` (`id_orden`),
  ADD CONSTRAINT `fk_ordenessellado_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_productos_almacen` FOREIGN KEY (`almacen`) REFERENCES `almacenes` (`id_almacen`);

--
-- Filtros para la tabla `productos_materiasprimas`
--
ALTER TABLE `productos_materiasprimas`
  ADD CONSTRAINT `fk_productos_materiasprimas_materia_prima` FOREIGN KEY (`id_materia_prima`) REFERENCES `materiasprimas` (`id_materia_prima`),
  ADD CONSTRAINT `fk_productos_materiasprimas_producto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`);

--
-- Filtros para la tabla `proveedores_materiasprimas`
--
ALTER TABLE `proveedores_materiasprimas`
  ADD CONSTRAINT `fk_proveedores_materiasprimas_materia_prima` FOREIGN KEY (`id_materia_prima`) REFERENCES `materiasprimas` (`id_materia_prima`),
  ADD CONSTRAINT `fk_proveedores_materiasprimas_proveedor` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedores` (`id_proveedor`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_usuarios_rol` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `fk_ventas_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`),
  ADD CONSTRAINT `fk_ventas_dni_cliente` FOREIGN KEY (`dni_cliente`) REFERENCES `clientes` (`dni`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
