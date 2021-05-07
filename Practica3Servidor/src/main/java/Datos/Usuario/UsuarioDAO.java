package Datos.Usuario;

import Entidades.Usuario;

import java.util.List;

public interface UsuarioDAO {
     public List<Usuario> select();

     public Usuario select(Usuario usuario);

     public void insert(Usuario usuario);

     public void update(Usuario usuario);

     public void delete(Usuario usuario);
}
