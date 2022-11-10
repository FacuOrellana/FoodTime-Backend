using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public abstract class Persona
    {
        
        [Key]
        public int Id { get; set; }
        
        public string Nombre { get; set; }
        public string Apellido { get; set; }
        public string Domicilio { get; set; }
        public int Edad {get; set; }
        public virtual Usuario Usuario { get; set; }         
        public int Dni { get; set; }
        public string Email { get; set; }
        public DateTime FechaNacimiento { get; set; }


        
    }
}
