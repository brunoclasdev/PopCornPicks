package br.com.bclas.popcornpicks.data.repository

import br.com.bclas.popcornpicks.data.model.ListMovieModel
import br.com.bclas.popcornpicks.data.model.toEntity
import br.com.bclas.popcornpicks.domain.entity.ListMovieEntity
import br.com.bclas.popcornpicks.domain.repository.MovieRepository
import br.com.bclas.popcornpicks.domain.util.Result
import retrofit2.Response

internal class MovieRepositoryImpl constructor(

): MovieRepository {
    override suspend fun getMovies() : Result<ListMovieEntity, Unit> {
        return try{
            val response : Response<ListMovieModel> = TODO()
            if(response.isSuccessful) {
                val listMovieModel = response.body() ?: return Result.Failure()
                Result.Success(listMovieModel.toEntity())
            }else{
                Result.Error(Unit)
            }
        }catch (e: Exception){
            Result.Failure(e)
        }
    }
}