package Datos.Sesion;

import Entidades.Sesion;

import java.util.List;

public interface SesionDAO {
     public List<Sesion> select();

     public Sesion select(Sesion sesion);

     public void insert(Sesion sesion);

     public void update(Sesion sesion);

     public void delete(Sesion sesion);
}
