using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class Paciente : Persona
    {
        [Key]
        public int Id { get; set; }
        private Habitacion Habitacion { get; set; }
        /*
        public Paciente(string nombre, string apellido, string domicilio, int edad, int dni, string email, string usuario) : base(nombre, apellido, domicilio, edad, dni, email, usuario)
        {
        }
        */
    }
}
