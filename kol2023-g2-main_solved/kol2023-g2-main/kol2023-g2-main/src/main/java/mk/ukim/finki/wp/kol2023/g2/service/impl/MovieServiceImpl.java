package mk.ukim.finki.wp.kol2023.g2.service.impl;

import mk.ukim.finki.wp.kol2023.g2.model.Director;
import mk.ukim.finki.wp.kol2023.g2.model.Genre;
import mk.ukim.finki.wp.kol2023.g2.model.Movie;
import mk.ukim.finki.wp.kol2023.g2.model.exceptions.InvalidDirectorIdException;
import mk.ukim.finki.wp.kol2023.g2.model.exceptions.InvalidMovieIdException;
import mk.ukim.finki.wp.kol2023.g2.repository.DirectorRepository;
import mk.ukim.finki.wp.kol2023.g2.repository.MovieRepository;
import mk.ukim.finki.wp.kol2023.g2.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;
    DirectorRepository directorRepository;

    public MovieServiceImpl(MovieRepository movieRepository, DirectorRepository directorRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
    }

    /**
     * @return List of all movies in the database
     */
    @Override
    public List<Movie> listAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * returns the movie with the given id
     *
     * @param id The id of the movie that we want to obtain
     * @return
     * @throws InvalidMovieIdException when there is no movie with the given id
     */
    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(InvalidMovieIdException::new);
    }

    /**
     * This method is used to create a new movie, and save it in the database.
     *
     * @param name
     * @param description
     * @param rating
     * @param genre
     * @param director
     * @return The movie that is created. The id should be generated when the movie is created.
     * @throws InvalidDirectorIdException when there is no director with the given id
     */
    @Override
    public Movie create(String name, String description, Double rating, Genre genre, Long director) {

        Director director1 = directorRepository.findById(director).orElseThrow(InvalidDirectorIdException::new);

        Movie movie = new Movie(name, description, rating, genre, director1);

        movieRepository.save(movie);

        return movie;
    }

    /**
     * This method is used to update a movie, and save it in the database.
     *
     * @param id The id of the movie that is being edited
     * @param name
     * @param description
     * @param rating
     * @param genre
     * @param director
     * @return The movie that is updated.
     * @throws InvalidMovieIdException when there is no movie with the given id
     * @throws InvalidDirectorIdException when there is no director with the given id
     */
    @Override
    public Movie update(Long id, String name, String description, Double rating, Genre genre, Long director) {

        Movie movie = movieRepository.findById(id).orElseThrow(InvalidMovieIdException::new);

        Director director1 = directorRepository.findById(director).orElseThrow(InvalidDirectorIdException::new);

        movie.setName(name);
        movie.setDescription(description);
        movie.setRating(rating);
        movie.setGenre(genre);
        movie.setDirector(director1);

        movieRepository.save(movie);

        return movie;
    }

    /**
     * Method that should delete a movie. If the id is invalid, it should throw InvalidMovieIdException.
     *
     * @param id
     * @return The movie that is deleted.
     * @throws InvalidMovieIdException when there is no movie with the given id
     */
    @Override
    public Movie delete(Long id) {

        Movie movie = movieRepository.findById(id).orElseThrow(InvalidMovieIdException::new);

        movieRepository.delete(movie);
        return movie;
    }

    /**
     * Method that should vote for a movie. If the id is invalid, it should throw InvalidMovieIdException.
     *
     * @param id
     * @return The movie that is voted for.
     * @throws InvalidMovieIdException when there is no movie with the given id
     */
    @Override
    public Movie vote(Long id) {

        Movie movie = movieRepository.findById(id).orElseThrow(InvalidMovieIdException::new);

        movie.setVotes(movie.getVotes()+1);

        movieRepository.save(movie);

        return movie;
    }

    /**
     * The implementation of this method should use repository implementation for the filtering.
     *
     * @param rating          Double that is used to filter the movies that have less rating than this value.
     *                        This param can be null, and is not used for filtering in this case.
     * @param genre           Used for filtering the movies that are from this genre.
     *                        This param can be null, and is not used for filtering in this case.
     * @return The movies that meet the filtering criteria
     */
    @Override
    public List<Movie> listMoviesWithRatingLessThenAndGenre(Double rating, Genre genre) {

        if(rating == null && genre == null){
            return movieRepository.findAll();
        }else if(rating == null){
            return movieRepository.findAllByGenre(genre);
        }else if( genre == null){
            return movieRepository.findAllByRatingLessThan(rating);
        }else {
            return movieRepository.findAllByRatingLessThanAndGenre(rating, genre);
        }
    }
}
