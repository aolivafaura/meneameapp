package es.mnmapp.aolv.data.repository.images

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import es.mnmapp.aolv.data.testing.SimpleIdlingResource
import es.mnmapp.aolv.domain.repository.ImagesRepo
import io.reactivex.BackpressureStrategy
import io.reactivex.Emitter
import io.reactivex.Flowable


class ImagesDataRepo(
        private val idlingResource: SimpleIdlingResource,
        private val firestoneDb: FirebaseFirestore
) : ImagesRepo {

    override fun getPlaceholderImage(category: String): Flowable<String> {
        fun handleResponse(task: Task<QuerySnapshot>, emitter: Emitter<String>) {
            idlingResource.setIdleState(true)

            val result = task.result.documents
            if (task.isSuccessful
                    && result.isNotEmpty()
                    && result[0].data?.isNotEmpty() == true) {
                emitter.onNext(result[0]!!.data!!["url"] as String)
            } else {
                emitter.onError(Throwable())
            }
        }

        idlingResource.setIdleState(false)

        return Flowable.create({ emitter ->
            firestoneDb.collection("news_placeholders")
                    .get()
                    .addOnCompleteListener { task ->
                        handleResponse(task, emitter)
                    }
        }, BackpressureStrategy.BUFFER)
    }
}