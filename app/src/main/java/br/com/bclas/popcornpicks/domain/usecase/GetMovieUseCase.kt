package br.com.bclas.popcornpicks.domain.usecase

import br.com.bclas.popcornpicks.domain.entity.ListMovieEntity
import br.com.bclas.popcornpicks.domain.util.Result
import kotlinx.coroutines.flow.Flow
import br.com.bclas.popcornpicks.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal interface GetMovieUseCase {
    operator fun invoke(): Flow<Result<ListMovieEntity, Unit>>
}
internal class GetMovieUseCaseImpl(
    private val movieRepository: MovieRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetMovieUseCase {
    override fun invoke(): Flow<Result<ListMovieEntity, Unit>> {
        return flow{
            emit(movieRepository.getMovies())
        }.flowOn(coroutineDispatcher)
    }
}