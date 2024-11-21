package foodtime.app.sanatorio.repositories;

import foodtime.app.common.BaseRepository;
import foodtime.app.sanatorio.models.Persona;

public interface PersonaRepository extends BaseRepository<Persona, Integer> {

    Persona findByDni(String dni);
}
