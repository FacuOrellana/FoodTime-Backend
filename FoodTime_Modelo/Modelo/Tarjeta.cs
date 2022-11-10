using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class Tarjeta
    {
        [Key]
        public int Id { get; set; }
        public string NombreCompleto { get; set; }
        public string NumeroTarjeta { get; set; }
        public string FechaHasta { get; set; }

    }
}
