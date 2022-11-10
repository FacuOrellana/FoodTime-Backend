using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class Empleado : Persona
    {
        [Key]
        public int Legajo { get; set; }
        /*
        public Empleado(string nombre, string apellido, string domicilio, int edad, int dni, string email, string usuario) : base(nombre, apellido, domicilio, edad, dni, email, usuario)
        {

        }
        */
    }
}
