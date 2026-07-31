-- Stefano es la contraseña
INSERT INTO usuarios (apellidos, dni, email, fecha_registro, nombres, password, role)
SELECT
    'Gonzales Reyna',
    '69342393',
    'stefano@gmail.com',
    '2026-01-17',
    'Stefano Alexandro',
    '$2a$12$aZHQKiJao7uA00gugvTddOolBQtj2Cxo1Y/RIt9w3PYyCKb0yuJKa',
    'ADMINISTRADOR'
    WHERE NOT EXISTS (
  SELECT 1 FROM usuarios WHERE email = 'stefano@gmail.com'
);
