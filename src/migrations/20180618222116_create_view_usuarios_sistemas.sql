--DOWN
DROP VIEW IF EXISTS vw_usuarios_sistemas;
--UP sqlite3
CREATE VIEW vw_usuarios_sistemas AS SELECT 
  S.id AS id, S.nombre AS nombre, 0 AS existe  
  FROM usuarios_sistemas US 
  INNER JOIN usuarios U ON U.id = US.usuario_id 
  INNER JOIN sistemas S ON S.id = US.sistema_id  
  LIMIT 2000;