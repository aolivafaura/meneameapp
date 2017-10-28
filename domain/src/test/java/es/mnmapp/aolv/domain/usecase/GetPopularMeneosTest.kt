package es.mnmapp.aolv.domain.usecase

import es.mnmapp.aolv.domain.repository.MeneosRepo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.runners.MockitoJUnitRunner

/**
 * Created by antoniojoseoliva on 12/08/2017.
 */

@RunWith(MockitoJUnitRunner::class)
class GetPopularMeneosTest {

    private lateinit var getPopularMeneos: GetPopularMeneos

    @Mock private lateinit var meneosRepo: MeneosRepo

    @Before
    fun setUp() {

        getPopularMeneos = GetPopularMeneos(meneosRepo)
    }

    @Test
    fun Given_UseCaseCalled_When_ParamsAreValid_Then_MeneosAreRetrievedFromRepoOneTime() {
        getPopularMeneos.buildUseCaseObservable(Unit)

        verify(meneosRepo, times(1)).getPopular()
    }
}