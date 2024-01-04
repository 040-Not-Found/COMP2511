package youtube_observerPattern;

public class Video {
    private String title;
    Produccer producer;
    Double length;

    public Video(String title, Produccer produccer, Double length) {
        this.title = title;
        this.producer = produccer;
        this.length = length;
        produccer.updateVideo(this);
    }

    public String getTitle() {
        return title;
    }
}
