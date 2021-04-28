import java.util.*;

public class Album {
    private String name;
    private String artist;
    private ArrayList<Song> songs;
    private ArrayList<Playlist> playlistName = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new ArrayList<>();
    }

    public Album() {

    }

    public boolean addSong(String title, double duration) {
        Song song = findSong(title);
        if (song != null) {
            System.out.println("Song already exists");
            return false;
        }
        this.songs.add(new Song(title, duration));
        System.out.println("New song added!");
        return true;
    }

    private Song findSong(String nameOfSong) {
        if(songs != null) {
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                if (song.getTitle().equals(nameOfSong)) {
                    return song;
                }
            }
        }
        return null;
    }


    public boolean addToPlaylist(String playlistName, int num) {
        Playlist playlist = findPlaylist(playlistName);
        int trackNum = num - 1;
        if ((trackNum >= 0) && songs.get(trackNum) == null) {
            System.out.println("No song in selected position.");
            return false;
        } else if ((trackNum >= 0) && (songs.get(trackNum) != null) && (!(playlist != null && playlist.getPlaylist().contains(songs.get(trackNum)))) && (songs.contains(songs.get(trackNum)))) {
            if (playlist != null) {
                playlist.getPlaylist().add(songs.get(trackNum));
                System.out.println("Added song to playlist.");
                return true;
            } else {
                System.out.println("Error adding song to playlist.");
                return false;
            }
        } else if (Objects.requireNonNull(playlist).getPlaylist().contains(songs.get(trackNum))) {
            System.out.println("Song already in playlist.");
            return false;
        } else {
            System.out.println("Failed to add song from album.");
            return false;
        }
    }

    public boolean addToPlaylist(String playlistName, String title) {
        Song song = findSong(title);
        Playlist playlist = findPlaylist(playlistName);
        if (song != null && (playlist != null) && (playlist.getPlaylist().contains(song))) {
            System.out.println("Song already exists in selected playlist");
            return false;
        } else if (song != null && (playlist != null) && (!playlist.getPlaylist().contains(song))) {
            playlist.getPlaylist().add(song);
            System.out.println("Song " + title + " added to playlist.");
            return true;
        } else if(song == null) {
            System.out.println("Could not find song");
            return false;
        }else {
            System.out.println("Error adding song");
            return false;
        }
    }

    public boolean removeSong(String songName) {
        System.out.println("Remove song from playlists too? \n" +
                "Yes - to remove song from all applicable playlists \n" +
                "No - to remove song from album");
        String answer = sc.nextLine();

        if(answer.toLowerCase(Locale.ROOT).equals("yes")) {
            Song song = findSong(songName);
            if (song != null && (playlistName != null && playlistName.contains(song))) {
                playlistName.remove(song);
                songs.remove(song);
                System.out.println("Removed song from playlists.");
                return true;
            }
        }
        Song song = findSong(songName);
        if (song != null && songs.contains(song)) {
            songs.remove(song);
            System.out.println("Song removed.");
        } else {
            System.out.println("Could not find song");
        }
        return false;
    }

    public void printList(String nameOfPlaylist) {
        Playlist playlist = findPlaylist(nameOfPlaylist);
        ListIterator<Song> playlistListIterator = playlist != null ? playlist.getPlaylist().listIterator() : null;
        while (playlistListIterator != null && playlistListIterator.hasNext()) {
            System.out.println("Song " + (playlistListIterator.nextIndex() + 1) + " " + playlistListIterator.next().toString());
        }

        System.out.println("End of list.");


    }

    public void play(String playlistName) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        boolean quit = false;
        boolean forward = true;
        Playlist playlist = findPlaylist(playlistName);
        ListIterator<Song> playlistIterator = null;
        if (playlist != null) {
            playlistIterator = playlist.getPlaylist().listIterator();
        } else {
            System.out.println("Playlist is empty.");
        }
        if ((playlist != null ? playlist.getPlaylist().size() : 0) == 0) {
            System.out.println("No songs in playlist");
        } else {
            System.out.println("Now playing " + playlistIterator.next().toString());
        }

        instructions3();

        while (!quit) {
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0:
                    if (playlist != null) {
                        System.out.println("Exiting " + playlist.getName());
                    }
                    System.out.println("Exiting Playlist.");
                    quit = true;
                    break;
                case 1:
                    if (!forward) {
                        if (playlistIterator.hasNext()) {
                            playlistIterator.next();
                        }
                    }
                    forward = true;
                    if (playlistIterator.hasNext()) {
                        System.out.println("Now playing " + playlistIterator.next().toString());
                    } else {
                        System.out.println("We are at the end of the playlist");
                        forward = false;
                    }
                    break;
                case 2:
                    if (forward) {
                        if (playlistIterator != null && playlistIterator.hasPrevious()) {
                            playlistIterator.previous();
                        }
                        forward = false;
                    }
                    if (playlistIterator != null && playlistIterator.hasPrevious()) {
                        System.out.println("Now playing " + playlistIterator.previous().toString());
                    } else {
                        System.out.println("We are at the start of the list");
                        forward = true;
                    }

                    break;
                case 3:
                    if (forward) {
                        if (playlistIterator != null && playlistIterator.hasPrevious()) {
                            System.out.println("Now replaying " + playlistIterator.previous().toString());
                            forward = false;
                        } else {
                            System.out.println("We are at the start of the list");
                        }
                    } else {
                        if (playlistIterator.hasNext()) {
                            System.out.println("Now replaying " + playlistIterator.next().toString());
                            forward = true;
                        } else {
                            System.out.println("We are at the end of the list");
                        }
                    }
                    break;


            }
        }

    }

    public boolean addPlaylist() {
        System.out.println("Enter new playlist name:");
        String name = sc.nextLine();
        Playlist playlist = findPlaylist(name);
        if (playlist != null) {
            System.out.println("Playlist already exists.");
            return false;
        }

        this.playlistName.add(new Playlist(name));
        System.out.println("Playlist added.");
        return true;
    }

    private Playlist findPlaylist(String name) {
        for (Playlist playlist : playlistName) {
            if (playlist.getName().equals(name)) {
                return playlist;
            }
        }

        return null;
    }

//
//    public void printMenu() {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("\nPress: ");
//        instructions();
//        int action = sc.nextInt();
//        sc.nextLine();
//        boolean quit = false;
//
//        while(!quit) {
//
//            switch (action) {
//                case 0:
//                    System.out.println("Exiting...");
//                    quit = true;
//                    break;
//                case 1:
//                    System.out.println("Enter song name: \n");
//                    String name = sc.nextLine();
//                    System.out.println("Enter song duration: \n");
//                    int duration = sc.nextInt();
//                    sc.nextLine();
//                    addSong(name,duration);
//                    break;
//                case 2:
//                    System.out.println("Enter choice: ");
//                    instructions2();
//                    int choice = sc.nextInt();
//                    sc.nextLine();
//                    switch(choice) {
//                        c
//                            System.out.println("Enter track number: \n");
//                            int num = sc.nextInt();
//                            sc.nextLine();
//                            addToPlaylist(num,LinkedList<Song> playlist);
//                    }
//            }
//        }
//    }


    public void instructions() {
        System.out.println("0 - to quit");
        System.out.println("1 - to add a song");
        System.out.println("2 - to add a new playlist");
        System.out.println("3 - to add an existing song to a playlist");
        System.out.println("4 - to print a list of songs in the playlist");
        System.out.println("5 - to play a playlist");
        System.out.println("6 - to print the menu");
        System.out.println("7 - to remove a song");
    }

    public void instructions2() {
        System.out.println("0 - to add to playlist by track number");
        System.out.println("1 - to add to playlist by song name");
    }

    public void instructions3() {
        System.out.println("0 - to exit playlist.");
        System.out.println("1 - to play the next song in playlist.");
        System.out.println("2 - to play the previous song in playlist.");
        System.out.println("3 - to replay the current song in playlist.");
    }


}
