INSERT INTO usuarios (apellido_materno,apellido_paterno, dni, email, fecha_registro, nombres, password, role)
SELECT
    'Reyna',
    'Gonzales',
    '69342393',
    'stefano@gmail.com',
    '2026-01-17',
    'Stefano Alexandro',
    '$2a$12$sbvklhKcX5.MJvr38yo2.u2U62/i435BXZzm4.ec08el3x9mXiVXa',
    'ADMINISTRADOR'
    WHERE NOT EXISTS (
  SELECT 1 FROM usuarios WHERE email = 'stefano@gmail.com'
);
