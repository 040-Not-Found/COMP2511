package youtube_observerPattern;

public class User implements Observer {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void notiy(Produccer producer, Video video) {
        System.out.println("Hi " + getName() + ", " + producer.getName() + " has update video: " + video.getTitle() + ".");
    }
    
}
