package mk.ukim.finki.wp.kol2023.g2.service.impl;

import mk.ukim.finki.wp.kol2023.g2.model.Director;
import mk.ukim.finki.wp.kol2023.g2.model.exceptions.InvalidDirectorIdException;
import mk.ukim.finki.wp.kol2023.g2.repository.DirectorRepository;
import mk.ukim.finki.wp.kol2023.g2.service.DirectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {

    DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    /**
     * returns the director with the given id
     *
     * @param id The id of the director that we want to obtain
     * @return
     * @throws InvalidDirectorIdException when there is no director with the given id
     */
    @Override
    public Director findById(Long id) {
        return directorRepository.findById(id).orElseThrow(InvalidDirectorIdException::new);
    }

    /**
     * @return List of all directors in the database
     */
    @Override
    public List<Director> listAll() {
        return directorRepository.findAll();
    }

    /**
     * This method is used to create a new director, and save it in the database.
     *
     * @param name
     * @return The director that is created. The id should be generated when the director is created.
     */
    @Override
    public Director create(String name) {

        Director director = new Director(name);

        directorRepository.save(director);

        return director;
    }
}
