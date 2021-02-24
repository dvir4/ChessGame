package board;

public class LocationFactory {
    public static final File[] files  = File.values();
    public static Location build(Location currLocation, int fileOffset, int rankOffset){
        int  fileLoc = currLocation.getFile().ordinal();
        int size = File.values().length - 1;
        // in case of invalid File enum value, insert null
        if(fileLoc + fileOffset > size || fileLoc + fileOffset < 0){
            return new Location(null ,currLocation.getRank() + rankOffset);
        }
        return new Location(files[fileLoc + fileOffset] ,currLocation.getRank() + rankOffset);
    }
}
