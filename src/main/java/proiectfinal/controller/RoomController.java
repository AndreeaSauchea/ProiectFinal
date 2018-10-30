package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proiectfinal.model.Room;
import proiectfinal.service.IRoomService;

import java.util.List;

@RestController
public class RoomController {

    @Autowired
    IRoomService roomService;

    @RequestMapping(method = RequestMethod.GET, value = {"/showRooms"})
    public List<Room> findRooms() {

        List<Room> rooms = (List<Room>) roomService.findAll();

        return rooms;
    }
}
