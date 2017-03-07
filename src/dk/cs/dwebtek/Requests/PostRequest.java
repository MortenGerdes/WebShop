package dk.cs.dwebtek.Requests;

/**
 * Created by mortenkrogh-jespersen on 07/02/2017.
 */
public interface PostRequest<E> extends Request<E> {

    E getPostBody();

}
