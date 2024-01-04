package youtube_statePattern;
public class LockedState extends ViewState {

    @Override
    public ViewState lock() {
        System.out.println("the video is ready to play");
        return new ReadyState();
    }

    @Override
    public ViewState play() {
        System.out.println("the video is ready to play");
        return new ReadyState();
    }

    @Override
    public ViewState next(Playlist playlist) {
        System.err.println("Error: Locked");
        return this;
    }
    
}
