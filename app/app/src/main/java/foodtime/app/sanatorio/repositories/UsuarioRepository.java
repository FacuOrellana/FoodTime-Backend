package foodtime.app.sanatorio.repositories;

import foodtime.app.common.BaseRepository;
import foodtime.app.sanatorio.models.Persona;
import foodtime.app.sanatorio.models.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends BaseRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Usuario findByPersona(Persona persona);
}