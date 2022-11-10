using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public class Usuario
    {
        [Key]
        public int Id { get; set; }
        public string User { get; set; }
        public string Password { get; set; }
        /*
        public Usuario(string user, string pass)
        {
            User = user;
            Password = pass;
        }
        */
    }
}
