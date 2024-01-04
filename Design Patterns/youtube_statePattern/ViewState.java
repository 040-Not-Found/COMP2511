package youtube_statePattern;

public abstract class ViewState {

    public ViewState() {
    }

    public abstract ViewState lock();

    public abstract ViewState play();

    public abstract ViewState next(Playlist playlist);
}
