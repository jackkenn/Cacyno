package interfaces;

/**
 * The interface used for making calls to the User table
 */
public interface IUser {
    /**
     * when a user is being updated in database or being a user is being called from endpoint, this method handles
     * if either of this cases is successful
     * @return an int that indicates successful
     */
    int onSuccess();

    /**
     * when the volley request to get user fails or updating lobby in database fails,
     * this method will handle the error that has occurred
     * @return an int that indicates an error
     */
    int onError();
}
