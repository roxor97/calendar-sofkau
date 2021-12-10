package co.com.sofka.calendar.services;

import co.com.sofka.calendar.collections.CourseTime;
import co.com.sofka.calendar.collections.Program;
import co.com.sofka.calendar.collections.Time;
import co.com.sofka.calendar.model.ProgramDate;
import co.com.sofka.calendar.repositories.ProgramRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTest {

    @InjectMocks
    SchedulerService schedulerService;

    @Mock
    ProgramRepository repository;

    @Test
   
    void generateCalendar() {
        var programId = "xxxx";
        var startDate = LocalDate.of(2022, 1, 1);

        Program program = getProgramDummy();

        Mockito.when(repository.findById(programId)).thenReturn(Mono.just(program));

        Flux<ProgramDate> response = schedulerService.generateCalendar(programId, startDate);

        StepVerifier.create(response)
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-03")
                            && programDate.getCategoryName().equals("Principios");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-04")
                            && programDate.getCategoryName().equals("Principios");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-05")
                            && programDate.getCategoryName().equals("Bases");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-06")
                            && programDate.getCategoryName().equals("Bases");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-07")
                            && programDate.getCategoryName().equals("Fundamentos");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-10")
                            && programDate.getCategoryName().equals("Fundamentos");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-11")
                            && programDate.getCategoryName().equals("Fundamentos");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-12")
                            && programDate.getCategoryName().equals("Fundamentos");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-13")
                            && programDate.getCategoryName().equals("Fundamentos avazandos");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-14")
                            && programDate.getCategoryName().equals("Fundamentos avazandos");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-17")
                            && programDate.getCategoryName().equals("Fundamentos avazandos");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-18")
                            && programDate.getCategoryName().equals("Fundamentos avazandos");
                })
                .expectNextMatches(programDate -> {
                    return programDate.getDate().toString().equals("2022-01-19")
                            && programDate.getCategoryName().equals("Fundamentos avazandos");
                })
                .verifyComplete();

        StepVerifier.create(response).expectNextCount(13).verifyComplete();
        Mockito.verify(repository).findById(programId);
    }

    @Test
    void programNoFound() {
        var programId = "xxxx";
        var startDate = LocalDate.of(2022, 1, 1);

        Mockito.when(repository.findById(programId)).thenReturn(Mono.empty());

       

        Flux<ProgramDate> response = schedulerService.generateCalendar(programId, startDate);

        StepVerifier.create(response)
                .expectErrorMessage("no se encontro el programa academico")
                .verify();

        Mockito.verify(repository).findById(programId);


    }

    // no tocar
    private Program getProgramDummy() {
        var program = new Program();
        program.setId("xxxx");
        program.setName("Programa 2022");
        program.setCourses(new ArrayList<>());
        var timesForCourse1 = new ArrayList<Time>();
        timesForCourse1.add(new Time("1", 2, "Principios", List.of()));
        timesForCourse1.add(new Time("2", 2, "Bases", List.of()));
        timesForCourse1.add(new Time("3", 4, "Fundamentos", List.of()));
        timesForCourse1.add(new Time("3", 5, "Fundamentos avazandos", List.of()));

        program.getCourses().add(new CourseTime("xxx-z", "Introducción", timesForCourse1));
        return program;
    }

    // no tocar
    private String getSnapResult() {
        return "[{\"categoryName\":\"Principios\",\"date\":{\"year\":2022,\"month\":1,\"day\":3}},{\"categoryName\":\"Principios\",\"date\":{\"year\":2022,\"month\":1,\"day\":4}},{\"categoryName\":\"Bases\",\"date\":{\"year\":2022,\"month\":1,\"day\":5}},{\"categoryName\":\"Bases\",\"date\":{\"year\":2022,\"month\":1,\"day\":6}},{\"categoryName\":\"Fundamentos\",\"date\":{\"year\":2022,\"month\":1,\"day\":7}},{\"categoryName\":\"Fundamentos\",\"date\":{\"year\":2022,\"month\":1,\"day\":10}},{\"categoryName\":\"Fundamentos\",\"date\":{\"year\":2022,\"month\":1,\"day\":11}},{\"categoryName\":\"Fundamentos\",\"date\":{\"year\":2022,\"month\":1,\"day\":12}},{\"categoryName\":\"Fundamentos avazandos\",\"date\":{\"year\":2022,\"month\":1,\"day\":13}},{\"categoryName\":\"Fundamentos avazandos\",\"date\":{\"year\":2022,\"month\":1,\"day\":14}},{\"categoryName\":\"Fundamentos avazandos\",\"date\":{\"year\":2022,\"month\":1,\"day\":17}},{\"categoryName\":\"Fundamentos avazandos\",\"date\":{\"year\":2022,\"month\":1,\"day\":18}},{\"categoryName\":\"Fundamentos avazandos\",\"date\":{\"year\":2022,\"month\":1,\"day\":19}}]";
    }

}