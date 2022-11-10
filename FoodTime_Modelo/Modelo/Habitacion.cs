using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class Habitacion
    {
        [Key]
        public int Id { get; set; }
        public string Piso { get; set; }
        public string Numero { get; set; }

        public Habitacion(string piso, string numero)
        {
            Piso = piso;
            Numero = numero;
        }
    }
}
