using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodTime_Modelo.Modelo
{
    public enum EstadoPedido
    {
        INICIADO,
        PENDIENTE,
        ENPREPARACION,
        FINALIZADO,
        CANCELADO

    }

    /* INICIADO = estado asignado a un pedido cuando es creado 
     * pero no completado,puede ser cancelado por el usuario
     * 
     * PENDIENTE = estado de un pedido cuando se espera su confirmacion
     * 
     * EN PREPARACION = cuando el pedido esta siendo preparado
     * 
     * FINALIZADO = estado de un pedido cuando es entregado
     * 
     * CANCELADO = estado de un pedido cuando es cancelado 
     * 
     */
}
