import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Event bus implementation inspired on Dai {@link https://android.jlelse.eu/} kotlin rx bus
 */
object EventBus {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event : Any) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType : Class<T>) : Observable<T> = publisher.ofType(eventType)
}