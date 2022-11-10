using FoodTime_DataAccess;
using FoodTime_Modelo.Modelo;
using FoodTime_Service.DTOs;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Service.Servicios
{
    public class MenusService
    {
        private DataContext DB = new DataContext();

        public List<Menu> GetMenus()
        {
            return DB.Menus.ToList();
        }

        public Menu Save(Menu menu)
        {
            DB.Menus.Add(menu);
            DB.SaveChanges();
            return menu;

        }

        public Menu GetMenu(int id)
        {
            return DB.Menus.Find(id);
        }

        public Menu Delete(int id)
        {
            Menu menu = DB.Menus.Find(id);
            if (menu == null)
            {
                throw new KeyNotFoundException();
            }
            DB.Menus.Remove(menu);
            DB.Entry(menu).State = EntityState.Deleted;
            DB.SaveChanges();
            return menu;
        }

        public Menu Update(Menu menu)
        {
            Menu _menu = DB.Menus.Find(menu.Id);
            if (_menu == null)
            {
                throw new KeyNotFoundException();
            }
            _menu.TipoMenu = menu.TipoMenu;
            _menu.Titulo = menu.Titulo;
            _menu.Disponibilidad = menu.Disponibilidad;
            _menu.Descripcion = menu.Descripcion;
            _menu.Precio = menu.Precio;
            DB.Entry(_menu).State = EntityState.Modified;
            DB.SaveChanges();
            return _menu;
        }
    }
}
