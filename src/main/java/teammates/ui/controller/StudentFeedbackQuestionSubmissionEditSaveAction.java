package teammates.ui.controller;

import teammates.common.datatransfer.FeedbackQuestionBundle;
import teammates.common.datatransfer.FeedbackSessionAttributes;
import teammates.common.exception.EntityDoesNotExistException;
import teammates.common.util.Const;
import teammates.logic.api.GateKeeper;

public class StudentFeedbackQuestionSubmissionEditSaveAction extends
        FeedbackQuestionSubmissionEditSaveAction {

    @Override
    protected void verifyAccesibleForSpecificUser() {
        new GateKeeper().verifyAccessible(
                logic.getStudentForGoogleId(courseId, account.googleId),
                logic.getFeedbackSession(feedbackSessionName, courseId));
    }

    @Override
    protected String getUserEmailForCourse() {
        return logic.getStudentForGoogleId(courseId, account.googleId).email;
    }
    
    @Override
    protected String getUserSectionForCourse() {
        return logic.getStudentForGoogleId(courseId, account.googleId).section;
    }

    @Override
    protected FeedbackQuestionBundle getDataBundle(String userEmailForCourse)
            throws EntityDoesNotExistException {
        return logic.getFeedbackQuestionBundleForStudent(
                feedbackSessionName, courseId, feedbackQuestionId, userEmailForCourse);
    }

    @Override
    protected void setStatusToAdmin() {
        statusToAdmin = "Save question feedback and show student feedback question submission edit page<br>" +
                "Question ID: " + feedbackQuestionId + "<br>" +
                "Session Name: " + feedbackSessionName + "<br>" + 
                "Course ID: " + courseId;
    }

    @Override
    protected boolean isSessionOpenForSpecificUser(FeedbackSessionAttributes fs) {
        return fs.isOpened() || fs.isInGracePeriod();
    }

    @Override
    protected ShowPageResult createSpecificShowPageResult() {
        return createShowPageResult(Const.ViewURIs.STUDENT_FEEDBACK_QUESTION_SUBMISSION_EDIT, data);
    }

}
