package youtube_statePattern;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private User user;
    private List<Video> playlist = new ArrayList<>();
    private Video currentVideo;

    public Playlist(User user, List<Video> playlist) {
        this.user = user;
        this.playlist = playlist;
        currentVideo = playlist.get(0);
    }

    public void lockVideo() {
        currentVideo.lockVideo();
    }

    public void playVideo() {
        currentVideo.playVideo();
    }

    public void playNextVideo() {
        currentVideo.playNextVideo(this);
    }

    public void playNextVideoSuccess() {
        Video nextVideo = playlist.get(indexOfVideo(currentVideo) + 1);
        currentVideo = nextVideo;
    }

    public int indexOfVideo(Video video) {
        for (int i = 0; i < playlist.size(); i++) {
            if (playlist.get(i).equals(video)) {
                return i;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        // user
        User user = new User("user");
        // playlist
        Produccer p = new Produccer("Up");
        Video video = new Video("video", p, 10.0);
        Video video2 = new Video("video2", p, 10.0);

        List<Video> videos = new ArrayList<>();
        videos.add(video);
        videos.add(video2);

        Playlist playlist = new Playlist(user, videos);
        System.out.println(playlist.currentVideo.getTitle());
        playlist.playVideo();
        playlist.playNextVideo();
    }
}
