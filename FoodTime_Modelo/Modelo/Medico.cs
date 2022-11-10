using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class Medico : Persona
    {
        public string Especialidad { get; set; }
        public int Matricula { get; set; }
        [Key]       
        public int Legajo { get; set; }

        
    }
}
