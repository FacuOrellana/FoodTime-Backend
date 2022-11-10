using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class Comprobante
    {
        [Key]
        public int Id { get; set; }
        public Pedido Pedido { get; set; }
        public Pago Pago { get; set; }

        public Comprobante(Pedido pedido, Pago pago)
        {
            Pedido = pedido;
            Pago = pago;
        }

    }
}
