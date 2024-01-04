package youtube_observerPattern;

import java.util.ArrayList;
import java.util.List;

public class Produccer {
    String name;
    List<User> subscribers = new ArrayList<>();
    List<Video> videos = new ArrayList<>();

    public Produccer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void subscribeBy(User subscriber) {
        subscribers.add(subscriber);
    }

    public void updateVideo(Video video) {
        videos.add(video);

        subscribers.stream().forEach(e->e.notiy(this, video));
    }
    
}
