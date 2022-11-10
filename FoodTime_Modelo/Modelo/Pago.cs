using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class Pago
    {
        [Key]
        public int Id { get; set; }
        public Pedido Pedido { get; set; }
        public MetodoPago MetodoPago { get; set; }
        public float Total { get; set; }


        public Pago(Pedido pedido, MetodoPago metodoPago)
        {
            Pedido = pedido;
            MetodoPago = metodoPago;
        }
    }
}
