using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{

    public class Menu
    {
        [Key]
        public int Id { get; set; }
        public string Titulo {get; set; }
        public string Descripcion { get; set; }
        public double Precio { get; set; }
        public TipoMenu TipoMenu { get; set; }
        public bool Disponibilidad { get; set; }

        //public Menu(string descripcion, double precio, TipoMenu tipo)
        //{
        //    Descripcion = descripcion;
        //    Precio = precio;
        //    TipoMenu = tipo;
        //    Disponibilidad = true;
        //}

    }
}
