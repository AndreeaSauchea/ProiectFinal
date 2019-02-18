package proiectfinal;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import proiectfinal.model.BookedRoom;
import proiectfinal.service.RoomService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CheckRoomAvailability {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private RoomService roomService;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Test
    public void validateCheckRoomIfBookedCheckOutInDates() throws ParseException {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckIn(format.parse("2019-02-11"));
        bookedRoom.setCheckOut(format.parse("2019-02-15"));
        boolean isBooked = roomService.checkRoomIfBooked(bookedRoom, format.parse("2019-02-09"), format.parse("2019-02-13"));
        Assert.assertTrue(!isBooked);
    }

    @Test
    public void validateCheckRoomIfBookedCheckInInDates() throws ParseException {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckIn(format.parse("2019-02-11"));
        bookedRoom.setCheckOut(format.parse("2019-02-15"));
        boolean isBooked = roomService.checkRoomIfBooked(bookedRoom, format.parse("2019-02-13"), format.parse("2019-02-20"));
        Assert.assertTrue(!isBooked);
    }

    @Test
    public void validateCheckRoomIfBookedBothDatesInDates() throws ParseException {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckIn(format.parse("2019-02-11"));
        bookedRoom.setCheckOut(format.parse("2019-02-15"));
        boolean isBooked = roomService.checkRoomIfBooked(bookedRoom, format.parse("2019-02-12"), format.parse("2019-02-14"));
        Assert.assertTrue(!isBooked);
    }

    @Test
    public void validateCheckRoomIfBookedDatesBetweenCheckInAndCheckOut() throws ParseException {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckIn(format.parse("2019-02-11"));
        bookedRoom.setCheckOut(format.parse("2019-02-15"));
        boolean isBooked = roomService.checkRoomIfBooked(bookedRoom, format.parse("2019-02-09"), format.parse("2019-02-20"));
        Assert.assertTrue(!isBooked);
    }

    @Test
    public void validateCheckRoomIfBookedPass() throws ParseException {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckIn(format.parse("2019-02-11"));
        bookedRoom.setCheckOut(format.parse("2019-02-15"));
        boolean isBooked = roomService.checkRoomIfBooked(bookedRoom, format.parse("2019-02-20"), format.parse("2019-02-25"));
        Assert.assertTrue(isBooked);
    }
}
