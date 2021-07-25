package com.manish.shaadi.di.module

import com.manish.shaadi.helper.AppExecutors
import com.manish.shaadi.remoteUtils.WebService
import com.manish.shaadi.shaadimatch.data.MatchDTO
import com.manish.shaadi.shaadimatch.repo.MatchRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMatchRepository(dao: MatchDTO, webservice: WebService, executor: AppExecutors): MatchRepository {
        return MatchRepository(dao = dao,  webService = webservice, appExecutors = executor)
    }


}