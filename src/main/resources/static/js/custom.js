function deleteUserConfirmation() {
    return window.confirm("Are you sure you want to delete this user?");
}

function deleteGoalConfirmation() {
    return window.confirm("Are you sure you want to delete this goal?");
}

function deleteMilestoneConfirmation() {
    return window.confirm("Are you sure you want to delete this milestone?")
}

function cancelConfirmation() {
    return window.confirm("Cancelling now will delete the goal and all associated milestones. Do you want to continue?");
}