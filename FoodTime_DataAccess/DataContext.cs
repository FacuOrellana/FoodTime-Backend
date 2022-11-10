using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FoodTime_Modelo.Modelo;

namespace FoodTime_DataAccess
{
    public class DataContext: DbContext
    {
       public DataContext(): base("FoodTime_DB")
        {

        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Pedido>().ToTable("Pedidos");
            modelBuilder.Entity<Persona>().ToTable("Personas");
            modelBuilder.Entity<Usuario>().ToTable("Usuarios");
            modelBuilder.Entity<Menu>().ToTable("Menus");
            
        }

        public DbSet<Pedido> Pedidos { get; set; }
        public DbSet<Persona> Personas { get; set; }
        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<Menu> Menus { get; set; }

    }


}
