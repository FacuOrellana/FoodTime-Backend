using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FoodTime_DataAccess;
using FoodTime_Modelo.Modelo;
using FoodTime_Service.DTOs;

namespace FoodTime_Service.Servicios
{
    public class PedidosService
    {
        private DataContext DB = new DataContext();

        public List<Pedido> GetPedidos()
        {
            return DB.Pedidos.ToList();
        }

        public Pedido Save(Pedido pedido)
        {
            DB.Pedidos.Add(pedido);
            DB.SaveChanges();
            return pedido;
        }

        public Pedido FindPedido(int id)
        {
            return DB.Pedidos.Find(id);
        }

        public Pedido Delete(int id)
        {
            Pedido pedido = DB.Pedidos.Find(id);
            if (pedido == null)
            {
                throw new KeyNotFoundException();
            }
            DB.Pedidos.Remove(pedido);
            DB.Entry(pedido).State = EntityState.Deleted;
            DB.SaveChanges();
            return pedido;

        }

        public Pedido Update(Pedido pedido)
        {
            Pedido _pedido = DB.Pedidos.Find(pedido.Id);
            if (_pedido == null)
            {
                throw new KeyNotFoundException();
            }
            _pedido.tipoEntrega = pedido.tipoEntrega;
            _pedido.Estado = pedido.Estado;
            _pedido.HoraEntrega = pedido.HoraEntrega;
            _pedido.LineaPedidos = pedido.LineaPedidos;
           // _pedido.Persona = pedido.Persona;
            _pedido.Total = pedido.Total;
            _pedido.Fecha = pedido.Fecha;
            
            DB.Entry(_pedido).State = EntityState.Modified;
            DB.SaveChanges();
            return _pedido;
        }

    }
}
