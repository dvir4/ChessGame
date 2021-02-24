package board;


import java.util.Objects;

public class Location{
    // rank is A-H, equal to row.
    private final File file;
    // file is equal to col
    private final int rank;

    public Location(File file, int rank) {
        this.rank = rank;
        this.file = file;
    }

    public int getRank() {
        return rank;
    }

    public File getFile() {
        return file;
    }

    public void printLocation() {
        System.out.println(file + "," + rank);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return rank == location.rank &&
                file == location.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }


    public String toString(){
        return file.toString() + ',' + rank;
    }
}
