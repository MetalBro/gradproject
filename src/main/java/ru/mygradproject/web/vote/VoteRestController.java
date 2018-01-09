package ru.mygradproject.web.vote;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mygradproject.AuthorizedUser;
import ru.mygradproject.model.User;
import ru.mygradproject.model.Vote;
import ru.mygradproject.service.VoteService;
import ru.mygradproject.util.validation.DateBetweenException;
import ru.mygradproject.util.validation.VoteException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL)
public class VoteRestController {

    static final String REST_URL = "/rest/votes/";

    private final VoteService voteService;

    public VoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping(value = "/restaurants/{restaurantId}")
    public ResponseEntity<Void> createWithLocation(@PathVariable("restaurantId") int restaurantId) {            // +++
        Vote created = voteService.save(restaurantId, AuthorizedUser.id());
        if (created == null) throw new VoteException("You can not vote today more");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/restaurants/{restaurantId}/dishes/{date}")
    public ResponseEntity<Void> createWithLocation(@PathVariable("restaurantId") int restaurantId,              // +++
                                                   @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate localDate) {            // +++
        Vote created = voteService.save(restaurantId, AuthorizedUser.id(), localDate);
        if (created == null) throw new VoteException("You can not vote today more");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{date}")                                                                           // +++
    public void delete(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localdate){
        voteService.delete(AuthorizedUser.id(), localdate);
    }

    @GetMapping(value = "/{date}", produces = MediaType.APPLICATION_JSON_VALUE)                                 // +++
    public Vote get(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localdate){
        return voteService.get(AuthorizedUser.id(), localdate);
    }

    @GetMapping(value = "/restaurants/{restaurantId}/between", produces = MediaType.APPLICATION_JSON_VALUE)     // +++
    public List<Vote> getBetween(@PathVariable("restaurantId") int restaurantId,
                                @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        if (startDate == null || endDate == null) throw new DateBetweenException("You must input both dates");
        return voteService.findAllByDateBetweenAndRestaurantId(startDate, endDate, restaurantId);
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)                                   // +++
    public List<Vote> getByUser(){
        return voteService.findAllByUserId(AuthorizedUser.id());
    }

    @GetMapping(value = "/restaurants/{restaurantId}/usersBy", produces = MediaType.APPLICATION_JSON_VALUE)          // +++
    public List<User> getUsersToLunchThisDayAndRestaurant(@PathVariable("restaurantId") int restaurantId,
                                                          @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate){
        return voteService.findUsersForRestaurantAndDate(restaurantId, localDate);
    }

    @GetMapping(value = "/restaurants/{restaurantId}/by", produces = MediaType.APPLICATION_JSON_VALUE)          // +++
    public List<Vote> getByRestaurantAndDate(@PathVariable("restaurantId") int restaurantId,
                                             @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate){
        return voteService.findAllByRestaurantAndDate(restaurantId, localDate);
    }

    @GetMapping(value = "/restaurants/{restaurantId}/count", produces = MediaType.APPLICATION_JSON_VALUE)       // +++
    public long countByRestaurant(@PathVariable("restaurantId") int restaurantId){
        return voteService.countAllByRestaurantId(restaurantId);
    }

    @GetMapping(value = "/countAll", produces = MediaType.APPLICATION_JSON_VALUE)                               // +++
    public long countAll(){
        return voteService.countAll();
    }
}
