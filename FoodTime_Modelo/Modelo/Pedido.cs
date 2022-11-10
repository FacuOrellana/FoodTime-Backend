using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class Pedido
    {
        [Key]
        public int Id { get; set; }
        //public virtual Persona Persona { get; set; }
        public EstadoPedido Estado { get; set; }
        public virtual List<LineaPedido> LineaPedidos { get; set; }
        public DateTime Fecha;
        public double Total { get; set; }
        public TipoEntrega tipoEntrega { get; set; }
        public string HoraEntrega { get; set; }
       
        /*
        public Pedido(Persona persona)
        {
            this.Persona = persona;
            this.Estado = EstadoPedido.INICIADO;          
            this.LineaPedidos = new List<LineaPedido>();                      
        }

        public void agregarLineaPedido(Menu menu, int cantidad)
        {
            var linea = new LineaPedido(menu,cantidad);
            LineaPedidos.Add(linea);
        }

        public double calcularTotal()
        {
            var total = 0.0;
            foreach (var linea in LineaPedidos)
            {
                total += (float)linea.calcularSubTotal();
            }
            Total = total;
            return total;
        }
        */

    }
}
