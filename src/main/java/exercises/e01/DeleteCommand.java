package exercises.e01;

public class DeleteCommand extends Command {
    private final String filename;
    public DeleteCommand(String filename) { this.filename = filename; }
    public String getFilename() { return filename; }
    @Override public String execute() { return "Deleting " + filename; }
}
