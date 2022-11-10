namespace FoodTime_DataAccess.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Migration : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Menus",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Titulo = c.String(),
                        Descripcion = c.String(),
                        Precio = c.Double(nullable: false),
                        TipoMenu = c.Int(nullable: false),
                        Disponibilidad = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Pedidos",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Estado = c.Int(nullable: false),
                        Total = c.Double(nullable: false),
                        tipoEntrega = c.Int(nullable: false),
                        HoraEntrega = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.LineaPedidoes",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Cantidad = c.Int(nullable: false),
                        Menu_Id = c.Int(),
                        Pedido_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Menus", t => t.Menu_Id)
                .ForeignKey("dbo.Pedidos", t => t.Pedido_Id)
                .Index(t => t.Menu_Id)
                .Index(t => t.Pedido_Id);
            
            CreateTable(
                "dbo.Personas",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Nombre = c.String(),
                        Apellido = c.String(),
                        Domicilio = c.String(),
                        Edad = c.Int(nullable: false),
                        Dni = c.Int(nullable: false),
                        Email = c.String(),
                        FechaNacimiento = c.DateTime(nullable: false),
                        Legajo = c.Int(),
                        Especialidad = c.String(),
                        Matricula = c.Int(),
                        Legajo1 = c.Int(),
                        Discriminator = c.String(nullable: false, maxLength: 128),
                        Usuario_Id = c.Int(),
                        Habitacion_Id = c.Int(),
                        Habitacion_Id1 = c.Int(),
                        Paciente_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Usuarios", t => t.Usuario_Id)
                .ForeignKey("dbo.Habitacions", t => t.Habitacion_Id)
                .ForeignKey("dbo.Habitacions", t => t.Habitacion_Id1)
                .ForeignKey("dbo.Personas", t => t.Paciente_Id)
                .Index(t => t.Usuario_Id)
                .Index(t => t.Habitacion_Id)
                .Index(t => t.Habitacion_Id1)
                .Index(t => t.Paciente_Id);
            
            CreateTable(
                "dbo.Usuarios",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        User = c.String(),
                        Password = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Habitacions",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Piso = c.String(),
                        Numero = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Personas", "Paciente_Id", "dbo.Personas");
            DropForeignKey("dbo.Personas", "Habitacion_Id1", "dbo.Habitacions");
            DropForeignKey("dbo.Personas", "Habitacion_Id", "dbo.Habitacions");
            DropForeignKey("dbo.Personas", "Usuario_Id", "dbo.Usuarios");
            DropForeignKey("dbo.LineaPedidoes", "Pedido_Id", "dbo.Pedidos");
            DropForeignKey("dbo.LineaPedidoes", "Menu_Id", "dbo.Menus");
            DropIndex("dbo.Personas", new[] { "Paciente_Id" });
            DropIndex("dbo.Personas", new[] { "Habitacion_Id1" });
            DropIndex("dbo.Personas", new[] { "Habitacion_Id" });
            DropIndex("dbo.Personas", new[] { "Usuario_Id" });
            DropIndex("dbo.LineaPedidoes", new[] { "Pedido_Id" });
            DropIndex("dbo.LineaPedidoes", new[] { "Menu_Id" });
            DropTable("dbo.Habitacions");
            DropTable("dbo.Usuarios");
            DropTable("dbo.Personas");
            DropTable("dbo.LineaPedidoes");
            DropTable("dbo.Pedidos");
            DropTable("dbo.Menus");
        }
    }
}
