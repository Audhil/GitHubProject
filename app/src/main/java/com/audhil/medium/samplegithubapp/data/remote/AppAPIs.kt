package com.audhil.medium.samplegithubapp.data.remote

import com.audhil.medium.samplegithubapp.ui.SampleJson
import com.audhil.medium.samplegithubapp.util.ConstantsUtil
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface AppAPIs {

    @GET(
        ConstantsUtil.REPOS_API + ConstantsUtil.FORWARD_SLASH +
                "{userName}" + ConstantsUtil.FORWARD_SLASH +
                "{userRepo}" + ConstantsUtil.FORWARD_SLASH +
                ConstantsUtil.PULLS
    )
    fun getPullRequests(
        @Path(value = "userName", encoded = true)
        userName: String,
        @Path(value = "userRepo", encoded = true)
        userRepo: String,
        @Query(ConstantsUtil.PAGE)
        page: String
    ): Flowable<ResponseBody>


    @GET
    fun getSampleJson(
        @Url url: String
    ): Flowable<SampleJson>
}