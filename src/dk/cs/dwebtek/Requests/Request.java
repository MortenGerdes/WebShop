package dk.cs.dwebtek.Requests;

/**
 * Created by mortenkrogh-jespersen on 07/02/2017.
 */
public interface Request<E> {

    boolean hasResponse();
    String getPath();
    E parseResponse(String message);

}
