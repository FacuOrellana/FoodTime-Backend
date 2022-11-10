using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class LineaPedido
    {
        [Key]
        public int Id { get; set; }
        public virtual Menu Menu { get; set; }
        public int Cantidad { get; set; }

    }
}
