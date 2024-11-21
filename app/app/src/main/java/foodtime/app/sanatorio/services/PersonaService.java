package foodtime.app.sanatorio.services;


import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.PersonaDto;
import foodtime.app.sanatorio.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import foodtime.app.sanatorio.models.Persona;

@Service
public class PersonaService extends BaseService<Persona, PersonaDto, Integer> {

    private final PersonaRepository personaRepository;

    @Autowired
    public PersonaService(PersonaRepository personaRepository){
        this.personaRepository = personaRepository;
    }

    @Override
    protected BaseRepository<Persona, Integer> getRepository() {
        return personaRepository;
    }

    @Override
    protected PersonaDto toDTO(Persona entity) {
        return new PersonaDto(entity);
    }

    @Override
    protected Persona toEntity(PersonaDto entity) {
        return new Persona(entity);
    }

}
