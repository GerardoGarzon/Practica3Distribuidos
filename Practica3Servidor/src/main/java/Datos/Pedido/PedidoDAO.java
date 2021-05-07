package Datos.Pedido;

import Entidades.Pedido;

import java.util.List;

public interface PedidoDAO {
     public List<Pedido> select();

     public Pedido select(Pedido pedido);

     public void insert(Pedido pedido);

     public void update(Pedido pedido);

     public void delete(Pedido pedido);
}
