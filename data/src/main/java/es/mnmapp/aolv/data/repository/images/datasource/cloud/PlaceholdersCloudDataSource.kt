package es.mnmapp.aolv.data.repository.images.datasource.cloud

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import es.mnmapp.aolv.data.constants.FIREBASE_COLLECTION_PLACEHOLDERS
import es.mnmapp.aolv.data.entity.PlaceholderEntity
import es.mnmapp.aolv.data.testing.SimpleIdlingResource
import es.mnmapp.aolv.domain.repository.ScreenDensity
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.internal.operators.single.SingleCreate

class PlaceholdersCloudDataSource(
    private val idlingResource: SimpleIdlingResource,
    private val firestoneDb: FirebaseFirestore
) {

    fun getPlaceholders(screenDensity: ScreenDensity): Single<List<PlaceholderEntity>> {
        fun handleResponse(task: Task<QuerySnapshot>, emitter: SingleEmitter<List<PlaceholderEntity>>) {
            idlingResource.setIdleState(true)

            val result = task.result.documents
            if (task.isSuccessful && result.isNotEmpty()) {
                @Suppress("unchecked_cast")
                emitter.onSuccess(mapToPlaceholderCloudDto(result).map {
                    mapToPlaceholderEntity(
                        it,
                        screenDensity
                    )
                })
            } else {
                emitter.onError(Throwable())
            }
        }

        fun handleError(emitter: SingleEmitter<List<PlaceholderEntity>>) {
            idlingResource.setIdleState(true)
            emitter.onError(Throwable())
        }

        idlingResource.setIdleState(false)

        return SingleCreate { emitter ->
            firestoneDb.collection(FIREBASE_COLLECTION_PLACEHOLDERS)
                .get()
                .addOnCompleteListener { task ->
                    handleResponse(task, emitter)
                }
                .addOnFailureListener {
                    handleError(emitter)
                }
        }
    }
}