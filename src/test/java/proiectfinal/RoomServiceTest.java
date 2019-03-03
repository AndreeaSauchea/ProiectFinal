package proiectfinal;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import proiectfinal.controller.dto.AvailabilityRequest;
import proiectfinal.controller.dto.RoomRequest;
import proiectfinal.controller.dto.RoomResponse;
import proiectfinal.model.Room;
import proiectfinal.repository.RoomRepository;
import proiectfinal.service.RoomService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static proiectfinal.service.RoomService.*;

public class RoomServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Test
    public void validateSaveRoomRequestIsNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(ROOM_REQUEST_IS_NULL);
        roomService.save(null);
    }

    @Test
    public void validateRequestNightlyPriceIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(THE_ROOM_NEEDS_A_NIGHTLY_PRICE);
        RoomRequest request = new RoomRequest();
        request.setNightlyPrice(0);
        roomService.save(request);
    }

    @Test
    public void validateRequestNumberPlacesIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(THE_ROOM_HAS_NO_NUMBER_OF_PLACES);
        RoomRequest request = new RoomRequest();
        request.setNightlyPrice(50);
        request.setNumberPlaces(0);
        roomService.save(request);
    }

    @Test
    public void validateRequestRoomNumberIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(THERE_IS_NO_NUMBER_FOR_THIS_ROOM);
        RoomRequest request = new RoomRequest();
        request.setNightlyPrice(50);
        request.setNumberPlaces(2);
        request.setRoomNumber(0);
        roomService.save(request);
    }

    @Test
    public void validateSave(){
        RoomRequest request = buildMockRoomRequest();
        Room room = buildMockRoom();
        Mockito.when(roomRepository.save(any())).thenReturn(room);
        RoomResponse response = roomService.save(request);
        Assert.assertEquals(request.getNightlyPrice(),response.getNightlyPrice(), 0);
        Assert.assertEquals(request.getNumberPlaces(),response.getNumberPlaces());
        Assert.assertEquals(request.getRoomNumber(),response.getRoomNumber());
    }

    @Test
    public void validateFindRoomByNumberPlacesRequestIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(REQUEST_IS_NULL);
        roomService.findRoomByNumberPlaces(null);
    }

    @Test
    public void validateFindRoomByNumberPlacesNumberOfPersonsIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(YOU_HAVE_TO_GIVE_THE_NUMBER_OF_PERSONS);
        AvailabilityRequest request = new AvailabilityRequest();
        roomService.findRoomByNumberPlaces(request);
    }

    @Test
    public void validateFindRoomByNumberPlacesCheckInIsNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(YOU_HAVE_TO_SPECIFY_CHECK_IN_DATE);
        AvailabilityRequest request = new AvailabilityRequest();
        request.setNumberOfPersons(2);
        roomService.findRoomByNumberPlaces(request);
    }

    @Test
    public void validateFindRoomByNumberPlacesCheckOutIsNull() throws ParseException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(YOU_HAVE_TO_SPECIFY_CHECK_OUT_DATE);
        AvailabilityRequest request = new AvailabilityRequest();
        request.setNumberOfPersons(2);
        request.setCheckIn(format.parse("2019-03-12"));
        roomService.findRoomByNumberPlaces(request);
    }

    @Test
    public void validateFindRoomByNumberPlacesCheckInIsAfterCheckOut() throws ParseException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(CHECK_IN_CAN_NOT_BE_AFTER_CHECK_OUT);
        AvailabilityRequest request = buildMockAvailabilityRequest();
        request.setCheckOut(format.parse("2019-03-10"));
        roomService.findRoomByNumberPlaces(request);
    }

    private AvailabilityRequest buildMockAvailabilityRequest() throws ParseException {
        AvailabilityRequest request = new AvailabilityRequest();
        request.setNumberOfPersons(2);
        request.setCheckIn(format.parse("2019-03-12"));
        request.setCheckOut(format.parse("2019-03-14"));
        return request;
    }

    private Room buildMockRoom() {
        Room room = new Room();
        room.setRoomNumber(1);
        room.setNightlyPrice(50);
        room.setNumberPlaces(2);
        return room;
    }

    private RoomRequest buildMockRoomRequest() {
        RoomRequest request =  new RoomRequest();
        request.setRoomNumber(1);
        request.setNightlyPrice(50);
        request.setNumberPlaces(2);
        return request;
    }
}
