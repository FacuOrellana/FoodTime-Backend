using CoreApiResponse;
using FoodTime_Modelo.Modelo;
using FoodTime_Service.DTOs;
using FoodTime_Service.Servicios;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using System.Net;

namespace FoodTime_Backend.Controllers
{
    
    [EnableCors("cors")]
    [Route("")]
    [ApiController]
    public class MenusController: ControllerBase
    {
        private MenusService menusService = new MenusService();

        [HttpGet]
        [Route("/GestionarMenus/all")]
        public IActionResult Get()
        {
            var menus = menusService.GetMenus();
            return Ok(menus);            
        }

        [HttpPost]
        [Route("/GestionarMenus/Crear")]
        public IActionResult Post([FromBody] Menu menu)
        {            
            menu.Disponibilidad = true;
            menusService.Save(menu);
            return Ok(menu);          
        }

        [HttpGet]
        [Route("GestionarMenus/{id}")]
        public IActionResult Get(int id)
        {
            var menuReturn = menusService.GetMenu(id);
            return Ok(menuReturn);
        }

        [HttpPut]
        [Route("GestionarMenus/{id}")]
        public IActionResult Put(int id, [FromBody] Menu menu)
        {
            
            var menuReturn = menusService.Update(menu);
            return Ok(menuReturn);
        }

        [HttpDelete]
        [Route("/GestionarMenus/{id}")]
        public IActionResult Delete(int id)
        {
            var menu = menusService.Delete(id);
            return Ok(menu);
        }

    }
}
