package Datos.Libro;

import Entidades.Libro;
import java.util.List;

public interface LibroDAO {
     public List<Libro> select();

     public Libro select(Libro libro);

     public Libro select_disponible();

     public void insert(Libro libro);

     public void update(Libro libro);

     public void delete(Libro libro);
}
