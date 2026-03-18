package exercises.e01;

public class SaveCommand extends Command {
    private final String filename;
    public SaveCommand(String filename) { this.filename = filename; }
    public String getFilename() { return filename; }
    @Override public String execute() { return "Saving to " + filename; }
}
