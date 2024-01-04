package youtube_statePattern;

public class Video {
    private String title;
    Produccer producer;
    Double length;
    ViewState viewState;

    public Video(String title, Produccer produccer, Double length) {
        this.title = title;
        this.producer = produccer;
        this.length = length;
        this.viewState = new ReadyState();
        produccer.updateVideo(this);
    }

    public String getTitle() {
        return title;
    }

    public void lockVideo() {
        viewState = viewState.lock();
    }

    public void playVideo() {
        viewState = viewState.play();
    }

    public void playNextVideo(Playlist playlist) {
        viewState = viewState.next(playlist);
    }
}
