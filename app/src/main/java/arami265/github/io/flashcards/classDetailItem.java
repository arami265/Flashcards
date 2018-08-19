package arami265.github.io.flashcards;

/**
 * Created by arami265 on 4/28/2017.
 */

public class classDetailItem {
    String name;
    String field;
    String description;
    String room;
    String time;
    String instructor;

    public classDetailItem(String name, String field, String description, String room, String time, String instructor) {
        this.name = name;
        this.field = field;

        if(!description.isEmpty())
            this.description = description;
        else
            this.description = "N/A";

        if(!room.isEmpty())
            this.room = room;
        else
            this.room = "N/A";

        if(!time.isEmpty())
            this.time = time;
        else
            this.time = "N/A";

        if(!instructor.isEmpty())
            this.instructor = instructor;
        else
            this.instructor = "N/A";
    }

    public String getName() {
        return name;
    }

    public String getField() {
        return field;
    }

    public String getDescription() {
        return description;
    }

    public String getRoom() {
        return room;
    }

    public String getTime() {
        return time;
    }

    public String getInstructor() {
        return instructor;
    }
}
