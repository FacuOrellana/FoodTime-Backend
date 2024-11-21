package foodtime.app.sanatorio.models;

import foodtime.app.common.BaseEntity;
import foodtime.app.sanatorio.dtos.UsuarioDto;

import javax.persistence.*;

@Entity(name = "Usuario")
@Table(name = "Usuario")
public class Usuario extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String contraseña;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    public Usuario() {}

    public Usuario (UsuarioDto usuarioDto) {
        this.tipoUsuario = TipoUsuario.findByName(usuarioDto.tipoUsuario());
        this.email = usuarioDto.email();
        this.contraseña = usuarioDto.password();
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
