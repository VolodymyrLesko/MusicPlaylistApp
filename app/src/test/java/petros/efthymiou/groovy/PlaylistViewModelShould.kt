package petros.efthymiou.groovy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import petros.efthymiou.groovy.utils.MainCoroutineScopeRule
import petros.efthymiou.groovy.utils.getValueForTest

class PlaylistViewModelShould {

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PlaylistViewModel
    private val repository: PlaylistRepository = mock()
    private val playlist = mock<List<Playlist>>()
    private val expected = Result.success(playlist)

    @Before
    fun setUp() {
        runBlocking {
            whenever(repository.getPlaylist()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        viewModel = PlaylistViewModel(repository)
    }

    @Test
    fun getPlaylistFromRepository() = runBlockingTest{
        viewModel.playlists.getValueForTest()

        verify(repository).getPlaylist()
    }

    @Test
    fun emitsPlaylistFromRepository() = runBlockingTest{

        assertEquals(expected, viewModel.playlists.getValueForTest())
    }
}