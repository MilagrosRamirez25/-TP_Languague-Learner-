-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-07-2025 a las 06:50:44
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
(10, 'Lucas', 'Fernández', '23456689'),
(11, 'Marta', 'García', '3456799'),
(12, 'Fernando', 'López', '46678901'),
(13, 'Martina', 'Álvarez', '45678912'),
(14, 'Carlos', 'Sánchez', '56789012'),
(15, 'Lucia', 'Bravo', '67890123');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno_curso`
--

CREATE TABLE `alumno_curso` (
  `id` int(11) NOT NULL,
  `id_alumno` int(11) NOT NULL,
  `id_curso` int(11) NOT NULL,
  `fecha_inscripcion` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alumno_curso`
--

INSERT INTO `alumno_curso` (`id`, `id_alumno`, `id_curso`, `fecha_inscripcion`) VALUES
(1, 4, 1, '2025-07-01'),
(2, 5, 1, '2025-07-01'),
(3, 6, 2, '2025-07-02'),
(4, 10, 3, '2025-07-03'),
(5, 11, 3, '2025-07-03'),
(6, 12, 4, '2025-07-04'),
(7, 13, 4, '2025-07-04'),
(8, 14, 5, '2025-07-05'),
(9, 15, 5, '2025-07-05');

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
  `idExamen` int(11) DEFAULT NULL,
  `tipo` varchar(50) NOT NULL DEFAULT 'selección simple',
  `puntos` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ejercicio`
--

INSERT INTO `ejercicio` (`id`, `contenido`, `idExamen`, `tipo`, `puntos`) VALUES
(1, 'Selecciona la forma correcta del verbo \"to be\".', 1, 'selección múltiple', 4),
(2, 'Escoge la conjugación correcta en pretérito.', 1, 'selección simple', 3),
(3, 'Traduce estas palabras al inglés.', 1, 'selección múltiple', 3),
(4, 'Identifica el sonido correcto de la palabra \"ch\" en alemán.', 2, 'selección simple', 3),
(5, 'Elige la estructura correcta de la oración subordinada.', 2, 'selección múltiple', 4),
(6, 'Completa la frase con la forma correcta del verbo.', 2, 'selección simple', 3),
(7, 'Traduce las siguientes palabras al francés.', 3, 'selección múltiple', 3),
(8, 'Relaciona cada palabra con su significado.', 3, 'selección simple', 4),
(9, 'Selecciona el género correcto de cada sustantivo.', 3, 'selección múltiple', 3),
(10, 'Escoge la pronunciación correcta de la palabra \"ach\".', 4, 'selección simple', 2),
(11, 'Identifica la fonética correcta para \"ch\" en alemán.', 4, 'selección múltiple', 3),
(12, 'Relaciona sonidos con letras en alemán.', 4, 'selección simple', 5),
(13, 'Elige la forma correcta del verbo en oraciones italianas.', 5, 'selección múltiple', 3),
(14, 'Selecciona la estructura sintáctica correcta.', 5, 'selección simple', 4),
(15, 'Completa la frase con la palabra adecuada.', 5, 'selección múltiple', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `examen`
--

CREATE TABLE `examen` (
  `id` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `idClase` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `examen`
--

INSERT INTO `examen` (`id`, `titulo`, `idClase`) VALUES
(1, 'Examen Inicial Inglés', 1),
(2, 'Examen Final Español', 2),
(3, 'Examen Intermedio Francés', 3),
(4, 'Examen de Fonética Alemana', 4),
(5, 'Examen Final Italiano', 5);

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
(9, 3, 'house', 1),
(10, 3, 'cat', 1),
(11, 3, 'dog', 0),
(12, 3, 'cheval', 1),
(13, 4, 'como en “ich” (palatal fricativa)', 1),
(14, 4, 'como en “chico” (africada)', 0),
(15, 4, 'como en “ach” (uvular fricativa)', 1),
(16, 5, 'Que el verbo vaya al final', 1),
(17, 5, 'Que comience con sujeto', 0),
(18, 5, 'Uso correcto de la conjunción \"weil\"', 1),
(19, 5, 'El verbo en infinitivo primero', 0),
(20, 6, 'Yo como', 1),
(21, 6, 'Tú comes', 0),
(22, 6, 'Él comió', 1),
(23, 7, 'maison', 1),
(24, 7, 'chat', 1),
(25, 7, 'perro', 0),
(26, 7, 'chien', 1),
(27, 8, 'libro - book', 1),
(28, 8, 'agua - water', 1),
(29, 8, 'fuego - fire', 0),
(30, 9, 'El', 1),
(31, 9, 'La', 1),
(32, 9, 'Los', 0),
(33, 10, 'Como en “ach” suave', 1),
(34, 10, 'Como en “ach” fuerte', 0),
(35, 11, 'Sonido palatal', 1),
(36, 11, 'Sonido gutural', 1),
(37, 11, 'Sonido nasal', 0),
(38, 12, 'Letra A', 1),
(39, 12, 'Letra B', 0),
(40, 12, 'Letra C', 1),
(41, 13, 'Mangiare', 1),
(42, 13, 'Correre', 1),
(43, 13, 'Andare', 0),
(44, 14, 'Sujeto + Verbo + Complemento', 1),
(45, 14, 'Verbo + Sujeto + Complemento', 0),
(46, 15, 'Completa con \"amico\"', 1),
(47, 15, 'Completa con \"amica\"', 1),
(48, 15, 'Completa con \"amici\"', 0);

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
(10, 'lucas1', 'lucas@mail.com', '123', 2),
(11, 'martas', 'marta@mail.com', '123', 2),
(12, 'fernin10', 'fernin@mail.com', '123', 2),
(13, 'martu1', 'martu@mail.com', '123', 2),
(14, 'carlas1', 'carlas@mail.com', '123', 2),
(15, 'luciax', 'luciax@mail.com', '123', 2);

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
-- Indices de la tabla `alumno_curso`
--
ALTER TABLE `alumno_curso`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_alumno` (`id_alumno`),
  ADD KEY `id_curso` (`id_curso`);

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
  ADD KEY `examen_ibfk_1` (`idClase`);

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
-- AUTO_INCREMENT de la tabla `alumno_curso`
--
ALTER TABLE `alumno_curso`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `clase`
--
ALTER TABLE `clase`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `curso`
--
ALTER TABLE `curso`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `ejercicio`
--
ALTER TABLE `ejercicio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `examen`
--
ALTER TABLE `examen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `respuesta`
--
ALTER TABLE `respuesta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

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
-- Filtros para la tabla `alumno_curso`
--
ALTER TABLE `alumno_curso`
  ADD CONSTRAINT `alumno_curso_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `alumno` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `alumno_curso_ibfk_2` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`) ON DELETE CASCADE;

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
  ADD CONSTRAINT `examen_ibfk_1` FOREIGN KEY (`idClase`) REFERENCES `clase` (`id`) ON DELETE CASCADE;

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
