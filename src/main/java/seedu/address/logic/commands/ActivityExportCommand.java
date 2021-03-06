package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.exportutil.ExportUtil;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;

/**
 * Exports an activity identified using it's displayed index from the address book.
 */
public class ActivityExportCommand extends ActivityCommand {

    public static final String COMMAND_WORD = "activityExport";
    public static final String COMMAND_ALIAS = "aExport";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the activity identified by the index number used in the displayed activity list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ACTIVITY_SUCCESS = "Exported Activity: %1$s";

    private final Index targetIndex;

    public ActivityExportCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Activity> filteredActivityList = model.getFilteredActivityList();

        if (targetIndex.getZeroBased() >= filteredActivityList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Activity exportActivity = model.generateExportedActivity(filteredActivityList.get(targetIndex.getZeroBased()));
        ExportUtil.exportActivity(exportActivity, model.getAttendingOfSelectedActivity());


        return new CommandResult(String.format(MESSAGE_SELECT_ACTIVITY_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityExportCommand // instanceof handles nulls
                && targetIndex.equals(((ActivityExportCommand) other).targetIndex)); // state check
    }
}
