package youtube_statePattern;

public class PlayingState extends ViewState {

    @Override
    public ViewState lock() {
        System.out.println("stop playing video");
        return new LockedState();
    }

    @Override
    public ViewState play() {
        System.out.println("paused video");
        return new ReadyState();
        
    }

    @Override
    public ViewState next(Playlist playlist) {
        playlist.playNextVideoSuccess();
        System.out.println("play next video");

        return new ReadyState();
    }

    
}
