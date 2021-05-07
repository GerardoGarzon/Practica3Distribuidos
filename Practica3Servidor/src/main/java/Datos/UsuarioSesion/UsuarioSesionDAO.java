package Datos.UsuarioSesion;

import Entidades.UsuarioSesion;

import java.util.List;

public interface UsuarioSesionDAO {
     public List<UsuarioSesion> select();

     public UsuarioSesion select(UsuarioSesion usuarioSesion);

     public void insert(UsuarioSesion usuarioSesion);

     public void update(UsuarioSesion usuarioSesion);

     public void delete(UsuarioSesion usuarioSesion);
}
