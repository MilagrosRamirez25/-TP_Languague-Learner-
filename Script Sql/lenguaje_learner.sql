-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-07-2025 a las 05:53:50
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `lenguaje_learner`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `id` int(11) NOT NULL,
  `idCurso` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `administrador`
--

INSERT INTO `administrador` (`id`, `idCurso`) VALUES
(3, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE `alumno` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `dni` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`id`, `nombre`, `apellido`, `dni`) VALUES
(4, 'Luis', 'Fernández', '23456789'),
(5, 'Ana', 'García', '34567890'),
(6, 'Pedro', 'López', '45678901'),
(21, 'brisa', 'baez', '12312');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clase`
--

CREATE TABLE `clase` (
  `id` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `tema` varchar(100) DEFAULT NULL,
  `contenido` text DEFAULT NULL,
  `autor` varchar(100) DEFAULT NULL,
  `fechaCreacion` date DEFAULT NULL,
  `idCurso` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clase`
--

INSERT INTO `clase` (`id`, `titulo`, `tema`, `contenido`, `autor`, `fechaCreacion`, `idCurso`) VALUES
(1, 'Introducción al Inglés', 'Gramática', 'Contenido introductorio del curso', 'Juan Pérez', '2025-07-15', 1),
(2, 'Tiempos Verbales', 'Gramática Avanzada', 'Contenido sobre tiempos verbales', 'María Gómez', '2025-07-20', 2),
(3, 'Vocabulario Francés', 'Vocabulario', 'Lista de palabras comunes en francés', 'Carlos Ruiz', '2025-07-25', 3),
(4, 'Pronunciación Alemana', 'Fonética', 'Cómo pronunciar sonidos difíciles en alemán', 'Laura Schmidt', '2025-08-01', 4),
(5, 'Gramática Italiana Avanzada', 'Sintaxis', 'Estudio de oraciones complejas', 'Mario Rossi', '2025-09-05', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `curso`
--

CREATE TABLE `curso` (
  `id` int(11) NOT NULL,
  `nombreCurso` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `fechaInicio` date DEFAULT NULL,
  `idProfesor` int(11) DEFAULT NULL,
  `capacidadMaxima` int(11) NOT NULL,
  `cantidadAlumnosInscritos` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `curso`
--

INSERT INTO `curso` (`id`, `nombreCurso`, `descripcion`, `fechaInicio`, `idProfesor`, `capacidadMaxima`, `cantidadAlumnosInscritos`) VALUES
(1, 'Inglés Básico', 'Curso para aprender inglés desde cero', '2025-08-01', 1, 30, 10),
(2, 'Español Avanzado', 'Perfeccionamiento en gramática y expresión', '2025-09-01', 2, 25, 5),
(3, 'Francés Intermedio', 'Curso intermedio de francés', '2025-07-15', 7, 20, 7),
(4, 'Alemán Básico', 'Introducción al alemán', '2025-08-15', 8, 15, 3),
(5, 'Italiano Avanzado', 'Perfeccionamiento italiano', '2025-09-10', 9, 10, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ejercicio`
--

CREATE TABLE `ejercicio` (
  `id` int(11) NOT NULL,
  `contenido` text NOT NULL,
  `idExamen` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ejercicio`
--

INSERT INTO `ejercicio` (`id`, `contenido`, `idExamen`) VALUES
(1, 'Selecciona la forma correcta del verbo \"to be\".', 1),
(2, 'Escoge la conjugación correcta en pretérito.', 2),
(3, 'Traduce estas palabras al francés.', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `examen`
--

CREATE TABLE `examen` (
  `id` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `idCurso` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `examen`
--

INSERT INTO `examen` (`id`, `titulo`, `idCurso`) VALUES
(1, 'Examen Inicial Inglés', 1),
(2, 'Examen Final Español', 2),
(3, 'Examen Intermedio Francés', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

CREATE TABLE `profesor` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `especialidad` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `profesor`
--

INSERT INTO `profesor` (`id`, `nombre`, `apellido`, `dni`, `especialidad`) VALUES
(1, 'Juan', 'Pérez', '12345678', 'Inglés'),
(2, 'Maria', 'Gómez', '87654321', 'Español'),
(7, 'Carlos', 'Ruiz', '11223344', 'Francés'),
(8, 'Laura', 'Schmidt', '22334455', 'Alemán'),
(9, 'Mario', 'Rossi', '33445566', 'Italiano');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `respuesta`
--

CREATE TABLE `respuesta` (
  `id` int(11) NOT NULL,
  `idEjercicio` int(11) DEFAULT NULL,
  `respuesta` varchar(255) DEFAULT NULL,
  `esCorrecta` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `respuesta`
--

INSERT INTO `respuesta` (`id`, `idEjercicio`, `respuesta`, `esCorrecta`) VALUES
(1, 1, 'am', 1),
(2, 1, 'is', 1),
(3, 1, 'are', 1),
(4, 1, 'be', 0),
(5, 2, 'went', 1),
(6, 2, 'goed', 0),
(7, 2, 'goes', 0),
(8, 2, 'gone', 0),
(9, 3, 'maison', 1),
(10, 3, 'chat', 1),
(11, 3, 'perro', 0),
(12, 3, 'chien', 1),
(13, 3, 'auto', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `rol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `usuario`, `email`, `pass`, `rol`) VALUES
(1, 'juanp', 'juanp@mail.com', '123', 1),
(2, 'mariag', 'maria@mail.com', '123', 1),
(3, 'admin1', 'admin@mail.com', '123', 0),
(4, 'luisf', 'luisf@mail.com', '123', 2),
(5, 'ana123', 'ana@mail.com', '123', 2),
(6, 'pedro87', 'pedro@mail.com', '123', 2),
(7, 'carlos1', 'carlos@mail.com', '123', 1),
(8, 'laura1', 'laura@mail.com', '123', 1),
(9, 'mario1', 'mario@mail.com', '123', 1),
(21, 'bri10', 'bri@gmailcom', '123', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idCurso` (`idCurso`);

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `clase`
--
ALTER TABLE `clase`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idCurso` (`idCurso`);

--
-- Indices de la tabla `curso`
--
ALTER TABLE `curso`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_curso_profesor` (`idProfesor`);

--
-- Indices de la tabla `ejercicio`
--
ALTER TABLE `ejercicio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idExamen` (`idExamen`);

--
-- Indices de la tabla `examen`
--
ALTER TABLE `examen`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idCurso` (`idCurso`);

--
-- Indices de la tabla `profesor`
--
ALTER TABLE `profesor`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `respuesta`
--
ALTER TABLE `respuesta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idEjercicio` (`idEjercicio`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `usuario` (`usuario`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clase`
--
ALTER TABLE `clase`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `curso`
--
ALTER TABLE `curso`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `ejercicio`
--
ALTER TABLE `ejercicio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `examen`
--
ALTER TABLE `examen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `respuesta`
--
ALTER TABLE `respuesta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD CONSTRAINT `administrador_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `administrador_ibfk_2` FOREIGN KEY (`idCurso`) REFERENCES `curso` (`id`);

--
-- Filtros para la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `clase`
--
ALTER TABLE `clase`
  ADD CONSTRAINT `clase_ibfk_1` FOREIGN KEY (`idCurso`) REFERENCES `curso` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `curso`
--
ALTER TABLE `curso`
  ADD CONSTRAINT `fk_curso_profesor` FOREIGN KEY (`idProfesor`) REFERENCES `profesor` (`id`) ON DELETE SET NULL;

--
-- Filtros para la tabla `ejercicio`
--
ALTER TABLE `ejercicio`
  ADD CONSTRAINT `ejercicio_ibfk_1` FOREIGN KEY (`idExamen`) REFERENCES `examen` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `examen`
--
ALTER TABLE `examen`
  ADD CONSTRAINT `examen_ibfk_1` FOREIGN KEY (`idCurso`) REFERENCES `curso` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `profesor`
--
ALTER TABLE `profesor`
  ADD CONSTRAINT `profesor_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `respuesta`
--
ALTER TABLE `respuesta`
  ADD CONSTRAINT `respuesta_ibfk_1` FOREIGN KEY (`idEjercicio`) REFERENCES `ejercicio` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
