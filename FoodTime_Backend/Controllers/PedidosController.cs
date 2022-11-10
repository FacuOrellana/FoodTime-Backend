using FoodTime_Modelo.Modelo;
using FoodTime_Service.DTOs;
using FoodTime_Service.Servicios;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.Metadata.Conventions;
using Newtonsoft.Json.Linq;




// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace FoodTime_Backend.Controllers
{
    [EnableCors("cors")]
    [Route("")]
    [ApiController]
    public class PedidosController : ControllerBase
    {
        private PedidosService pedidosService = new PedidosService();


        // GET: api/pedidos
        [HttpGet]
        [Route("/ListarPedido")]
        public IActionResult Get()
        {
            var pedidos = pedidosService.GetPedidos().Select(pedido =>
            {
                return new 
                {
                    Estado = pedido.Estado.ToString(),
                    Total = pedido.Total,
                    LineaPedidos = pedido.LineaPedidos,
                    Fecha = pedido.Fecha.ToString("dd-MM-yyyy"),
                    tipoEntrega = pedido.tipoEntrega.ToString(),
                    HoraEntrega = Convert.ToDateTime(pedido.HoraEntrega).ToString("dd-MM-yyyy"),
                    Id = pedido.Id
                };
            } ).ToList();
            return Ok(pedidos);
        }

        // GET api/pedidos/5
        [HttpGet]
        [Route("/Pedidos/{id}")]
        public IActionResult Get(int id)
        {
            Pedido pedido = pedidosService.FindPedido(id);
            var lineaPedido = pedido.LineaPedidos.Select(lp => new
            {
                Id = lp.Id,
                Menu = lp.Menu,
                cantidad = lp.Cantidad
            }
            ).ToList();
            var pedidoReturn = new
            {
               // Persona = pedido.Persona,
                EstadoPedido = pedido.Estado.ToString(),
                total = pedido.Total,
                Fecha = pedido.Fecha.ToString("dd-MM-yyyy"),
                TipoEntrega = pedido.tipoEntrega.ToString(),
                horaEntrega = Convert.ToDateTime(pedido.HoraEntrega).ToString("dd-MM-yyyy"),
                lineaPedidos = lineaPedido

            };
            return Ok(pedidoReturn);
        }

        // POST api/<PedidosController>
        [HttpPost]
        [Route("/Pedidos/Crear")]
        public IActionResult Post([FromBody] Pedido pedido)
        {
            Pedido _pedido = pedidosService.Save(pedido);
            return Ok(_pedido);
        }

        // PUT api/<PedidosController>/5
        [HttpPut]
        [Route("")]
        public IActionResult Put(int id, [FromBody] Pedido pedido)
        {
            Pedido _pedido = pedidosService.Update(pedido);
            return Ok(_pedido);
        }

        // DELETE api/<PedidosController>/5
        [HttpDelete]
        [Route("")]
        public IActionResult Delete(int id)
        {
            return Ok(pedidosService.Delete(id));
        }
    }
}
