package com.andreisoimu.cine_mille;

import com.andreisoimu.cine_mille.model.dao.*;
import com.andreisoimu.cine_mille.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${database-init}")
    private Boolean databaseinit;

	private final List<User> users = List.of(
		new User("Andrei", "Soimu", "a.soimu", "password", true, "ROLE_ADMIN")
	);

    private final List<Movie> movies = List.of(
            new Movie("Il padrino"),
            new Movie("Schindler's List"),
            new Movie("Pulp Fiction"),
            new Movie("Il buono, il brutto, il cattivo"),
            new Movie("Fight Club"),
            new Movie("Forrest Gump"),
            new Movie("Inception"),
            new Movie("Matrix"),
            new Movie("Interstellar"),
            new Movie("Salvate il soldato Ryan"),
            new Movie("Oppenheimer"),
            new Movie("Top Gun: Maverick"),
            new Movie("Hamilton"),
            new Movie("Joker"),
            new Movie("Le Mans '66 - La grande sfida"),
            new Movie("Green Book"),
            new Movie("Tre manifesti a Ebbing, Missouri"),
            new Movie("La battaglia di Hacksaw Ridge"),
            new Movie("Room"),
            new Movie("Grand Budapest Hotel")
    );

    private final List<Room> rooms = List.of(
            new Room("1", true),
            new Room("2", true),
            new Room("3", true),
            new Room("4", true),
            new Room("5", true)
    );

    private final List<Showtime> showtimes = List.of(
            new Showtime(LocalTime.of(9, 0)),
            new Showtime(LocalTime.of(12, 0)),
            new Showtime(LocalTime.of(15, 0)),
            new Showtime(LocalTime.of(18, 0)),
            new Showtime(LocalTime.of(21, 0))
    );

    @Autowired
    public DatabaseInitializer(UserService userService,
                               MovieService movieService,
                               RoomService roomService,
                               ScreeningService screeningService,
                               ShowtimeService showtimeService,
                               PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.movieService = movieService;
        this.roomService = roomService;
        this.screeningService = screeningService;
        this.showtimeService = showtimeService;
        this.passwordEncoder = passwordEncoder;
    }

    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningService screeningService;
    private final ShowtimeService showtimeService;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        if(databaseinit) {

            users.forEach(user -> {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                try {
                    userService.upsert(user);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            movies.forEach(movie -> {
                try {
                    movieService.insert(movie);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            rooms.forEach(room -> {
                try {
                    roomService.insert(room);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            showtimes.forEach(showtime -> {
                try {
                    showtimeService.insert(showtime);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            List<Movie> dbMovies = movieService.getAll();
            List<Room> dbRooms = roomService.getAll();
            List<Showtime> dbShowtimes = showtimeService.getAll();

            LocalDate today = LocalDate.now();
            LocalDate mondayLastLWeek = today.minusWeeks(2).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate sundayLastLWeek = today.minusWeeks(2).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

            for (int i = 0; i < 5; i++) {
                Screening screening = new Screening();
                screening.setShowtimes(dbShowtimes);
                screening.setStartDate(mondayLastLWeek);
                screening.setEndDate(sundayLastLWeek);
                screening.setRoom(dbRooms.get(i % dbRooms.size()));
                screening.setMovie(dbMovies.get(i));
                try {
                    screeningService.insert(screening);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            LocalDate mondayLastWeek = today.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate sundayLastWeek = today.minusWeeks(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

            for (int i = 5; i < 10; i++) {
                Screening screening = new Screening();
                screening.setShowtimes(dbShowtimes);
                screening.setStartDate(mondayLastWeek);
                screening.setEndDate(sundayLastWeek);
                screening.setRoom(dbRooms.get(i % dbRooms.size()));
                screening.setMovie(dbMovies.get(i));
                try {
                    screeningService.insert(screening);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            LocalDate mondayThisWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate sundayThisWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

            for (int i = 10; i < 15; i++) {
                Screening screening = new Screening();
                screening.setShowtimes(dbShowtimes);
                screening.setStartDate(mondayThisWeek);
                screening.setEndDate(sundayThisWeek);
                screening.setRoom(dbRooms.get(i % dbRooms.size()));
                screening.setMovie(dbMovies.get(i));
                try {
                    screeningService.insert(screening);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            LocalDate mondayNextWeek = today.plusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate sundayNextWeek = today.plusWeeks(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

            for (int i = 15; i < 20; i++) {
                Screening screening = new Screening();
                screening.setShowtimes(dbShowtimes);
                screening.setStartDate(mondayNextWeek);
                screening.setEndDate(sundayNextWeek);
                screening.setRoom(dbRooms.get(i % dbRooms.size()));
                screening.setMovie(dbMovies.get(i));
                try {
                    screeningService.insert(screening);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
