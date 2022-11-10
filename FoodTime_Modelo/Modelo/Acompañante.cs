using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class Acompañante : Persona

    {
        [Key]
        public int Id { get; set; }
        public Paciente Paciente { get; set; }
        public Habitacion Habitacion { get; set; }
        /*
        public Acompañante(string nombre, string apellido, string domicilio, int edad, int dni, string email, string usuario) : base(nombre, apellido, domicilio, edad, dni, email, usuario)
        {           
        }
                */
    }
}
