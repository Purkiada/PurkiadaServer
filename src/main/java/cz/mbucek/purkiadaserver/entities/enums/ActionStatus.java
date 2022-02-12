package cz.mbucek.purkiadaserver.entities.enums;

/**
 * Determines the current state of an Action.
 * 
 * @author Matěj Bucek
 *
 */
public enum ActionStatus {
    AFTER_REGISTRATION,
    BEFORE_REGISTRATION,
    REGISTRATION_IN_PROGRESS,
    AFTER_ACTION,
    BEFORE_ACTION,
    ACTION_IN_PROGRESS
}
