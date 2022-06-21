package petros.efthymiou.groovy.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailsService @Inject constructor(
    private val playlistDetailsAPI: PlaylistDetailsAPI
) {
    suspend fun fetchPlaylistDetails(id: String) : Flow<Result<PlaylistDetails>> {
        return flow {
            emit(Result.success(playlistDetailsAPI.fetchPlaylistDetails(id)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}
