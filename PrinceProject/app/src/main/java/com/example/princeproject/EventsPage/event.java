import java.util.Date;
import java.util.List;
import java.util.Random;

// Unfinished, will finish soon. -Nate
public class Event {
    // Rvent details
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;
    private int maxParticipants;

    // Organizer info
    private User organizer;

    // Pooling system
    private List<User> waitingList;
    private List<User> selectedParticipants; 

    // Status and check-ins
    private boolean isOpenForRegistration;

    // Constructor
    public Event(String title, String description, Date startDate, Date endDate, String location, int maxParticipants, User organizer, boolean isOpenForRegistration) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.organizer = organizer;
        this.isOpenForRegistration = isOpenforRegistration;
    }

    // TODO add getters and setters


    // Add user to the waiting list
    public void addToWaitingList(User user) {
        if (!waitingListIds.contains(user)) {
            waitingListIds.add(user);
        }
    }

    // Draw lottery for participants
    public void drawLottery() {
        Random rand = new Random();
        
        while (this.selectedParticipants.size() < this.maxParticipants) {
            int randUserIndex = rand.nextInt(this.waitingList.size());
            this.selectedParticipants.add(this.waitingList[randUserIndex]);
            this.waitingList.remove(randUserIndex);
        }
    }

    // Remove a participant
    public void cancelParticipant(User user) {
        selectedParticipantsIds.remove(user);

        this.drawLottery();
    }
}