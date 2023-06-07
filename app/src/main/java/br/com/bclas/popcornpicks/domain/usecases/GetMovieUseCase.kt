package br.com.bclas.popcornpicks.domain.usecases

import br.com.bclas.popcornpicks.domain.entity.ListMovieEntity
import br.com.bclas.popcornpicks.domain.util.Result
import kotlinx.coroutines.flow.Flow
import br.com.bclas.popcornpicks.domain.repositories.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal interface GetMovieUseCase {
    operator fun invoke(type : String): Flow<Result<ListMovieEntity, Unit>>
}
internal class GetMovieUseCaseImpl(
    private val movieRepository: MovieRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetMovieUseCase {
    override fun invoke(type: String): Flow<Result<ListMovieEntity, Unit>> {
        return flow{
            emit(movieRepository.getMovies(type))
        }.flowOn(coroutineDispatcher)
    }
}