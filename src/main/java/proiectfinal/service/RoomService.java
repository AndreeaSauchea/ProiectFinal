package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.controller.dto.AvailabilityRequest;
import proiectfinal.controller.dto.AvailabilityResponse;
import proiectfinal.controller.dto.RoomRequest;
import proiectfinal.controller.dto.RoomResponse;
import proiectfinal.exception.RoomNotFoundException;
import proiectfinal.model.BookedRoom;
import proiectfinal.model.Room;
import proiectfinal.repository.BookedRoomRepository;
import proiectfinal.repository.RoomRepository;
import proiectfinal.utils.OptionalEntityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Component
public class RoomService {

    public static final String ROOM_WAS_NOT_FOUND = "Room was not found";
    public static final String ROOM_REQUEST_IS_NULL = "Room Request is null";
    public static final String THE_ROOM_NEEDS_A_NIGHTLY_PRICE = "The room needs a nightly price";
    public static final String THE_ROOM_HAS_NO_NUMBER_OF_PLACES = "The room has no number of places";
    public static final String THERE_IS_NO_NUMBER_FOR_THIS_ROOM = "There is no number for this room";
    public static final String REQUEST_IS_NULL = "Request is null";
    public static final String YOU_HAVE_TO_GIVE_THE_NUMBER_OF_PERSONS = "You have to give the number of persons";
    public static final String YOU_HAVE_TO_SPECIFY_CHECK_IN_DATE = "You have to specify check in date";
    public static final String YOU_HAVE_TO_SPECIFY_CHECK_OUT_DATE = "You have to specify check out date";
    public static final String CHECK_IN_CAN_NOT_BE_AFTER_CHECK_OUT = "Check in can not be after check out";
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookedRoomRepository bookedRoomRepository;

    public List<RoomResponse> findAll() {
        List<Room> rooms = roomRepository.findAll();
        Collections.sort(rooms);
        List<RoomResponse> responseList = new ArrayList<>();
        for (Room addRoom : rooms) {
            responseList.add(buildResponse(addRoom));
        }
        return responseList;
    }

    private RoomResponse buildResponse(Room room) {
        RoomResponse response = new RoomResponse();
        response.setNightlyPrice(room.getNightlyPrice());
        response.setNumberPlaces(room.getNumberPlaces());
        response.setRoomNumber(room.getRoomNumber());
        response.setId(room.getId());
        return response;
    }

    public RoomResponse save(RoomRequest roomRequest) {
        validateRequest(roomRequest);
        Room room = new Room();
        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setNightlyPrice(roomRequest.getNightlyPrice());
        room.setNumberPlaces(roomRequest.getNumberPlaces());
        Room saveRoom = roomRepository.save(room);
        return buildResponse(saveRoom);
    }

    private void validateRequest(RoomRequest request) {
        if (request == null) {
            throw new IllegalArgumentException(ROOM_REQUEST_IS_NULL);
        }
        if (request.getNightlyPrice() == 0) {
            throw new IllegalArgumentException(THE_ROOM_NEEDS_A_NIGHTLY_PRICE);
        }
        if (request.getNumberPlaces() == 0) {
            throw new IllegalArgumentException(THE_ROOM_HAS_NO_NUMBER_OF_PLACES);
        }
        if (request.getRoomNumber() == 0) {
            throw new IllegalArgumentException(THERE_IS_NO_NUMBER_FOR_THIS_ROOM);
        }
    }

    public RoomResponse findById(Long id) throws Exception {
        return buildResponse(findRoom(id));
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public RoomResponse updateRoom(int roomNumber, RoomRequest newRoomRequest) {
        Room room = roomRepository.findByRoomNumber(roomNumber);
        room.setNumberPlaces(newRoomRequest.getNumberPlaces());
        room.setNightlyPrice(newRoomRequest.getNightlyPrice());
        Room saveRoom = roomRepository.save(room);
        return buildResponse(saveRoom);
    }

    private Room findRoom(Long roomId) throws Exception {
        Room room = new OptionalEntityUtils<Room>().getEntityOrException(roomRepository.findById(roomId),
                new RoomNotFoundException(ROOM_WAS_NOT_FOUND));
        return room;
    }

    public List<AvailabilityResponse> findRoomByNumberPlaces(AvailabilityRequest newAvailabilityRequest) {
        validateAvailabilityRequest(newAvailabilityRequest);
        List<AvailabilityResponse> response = new ArrayList<>();
        Date checkIn = newAvailabilityRequest.getCheckIn();
        Date checkOut = newAvailabilityRequest.getCheckOut();
        List<Room> roomList = roomRepository.findByNumberPlacesGreaterThanEqual(newAvailabilityRequest.getNumberOfPersons());
        Date now = new Date();
        for (Room room : roomList) {
            List<BookedRoom> bookedRoomList = bookedRoomRepository.findAllByRoomAndCheckOutAfter(room, now);
            if (bookedRoomList == null || bookedRoomList.isEmpty()) {
                AvailabilityResponse newResponse = new AvailabilityResponse();
                newResponse.setRoomNumber(room.getRoomNumber());
                response.add(newResponse);
            } else {
                for (BookedRoom bookedRoom : bookedRoomList) {
                    boolean isBooked = checkRoomIfBooked(bookedRoom, checkIn, checkOut);
                    if (isBooked) {
                        AvailabilityResponse newResponse = new AvailabilityResponse();
                        newResponse.setRoomNumber(room.getRoomNumber());
                        response.add(newResponse);
                    }

                }
            }

        }
        return response;
    }

    private void validateAvailabilityRequest(AvailabilityRequest newAvailabilityRequest) {
        if (newAvailabilityRequest == null){
            throw new IllegalArgumentException(REQUEST_IS_NULL);
        }
        if (newAvailabilityRequest.getNumberOfPersons() == 0){
            throw new IllegalArgumentException(YOU_HAVE_TO_GIVE_THE_NUMBER_OF_PERSONS);
        }
        if (newAvailabilityRequest.getCheckIn() == null){
            throw new IllegalArgumentException(YOU_HAVE_TO_SPECIFY_CHECK_IN_DATE);
        }
        if (newAvailabilityRequest.getCheckOut() == null){
            throw new IllegalArgumentException(YOU_HAVE_TO_SPECIFY_CHECK_OUT_DATE);
        }
        if (newAvailabilityRequest.getCheckIn().after(newAvailabilityRequest.getCheckOut())){
            throw new IllegalArgumentException(CHECK_IN_CAN_NOT_BE_AFTER_CHECK_OUT);
        }
    }

    public boolean checkRoomIfBooked(BookedRoom bookedRoom, Date checkIn, Date checkOut) {
        boolean isRoomBooked = true;
        Date checkInBookedRoom = bookedRoom.getCheckIn();
        Date checkOutBookedRoom = bookedRoom.getCheckOut();
        if (checkIn.after(checkInBookedRoom) && checkIn.before(checkOutBookedRoom)) {
            isRoomBooked = false;
        }
        if (checkOut.after(checkInBookedRoom) && checkOut.before(checkOutBookedRoom)) {
            isRoomBooked = false;
        }
        if (checkIn.after(checkInBookedRoom) && checkIn.before(checkOutBookedRoom)
                && checkOut.after(checkInBookedRoom) && checkOut.before(checkOutBookedRoom)) {
            isRoomBooked = false;
        }
        if (checkInBookedRoom.after(checkIn) && checkInBookedRoom.before(checkOut)
                && checkOutBookedRoom.after(checkIn) && checkOutBookedRoom.before(checkOut)) {
            isRoomBooked = false;
        }

        return isRoomBooked;
    }

}