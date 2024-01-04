package youtube_statePattern;

public class ReadyState extends ViewState {

    @Override
    public ViewState lock() {
        return new LockedState();
    }

    @Override
    public ViewState play() {
        System.out.println("start playing video");
        return new PlayingState();
    }

    @Override
    public ViewState next(Playlist playlist) {
        System.err.println("Error: Locked (cannot move to next video while paused)");
        return this;
    }
    
}
