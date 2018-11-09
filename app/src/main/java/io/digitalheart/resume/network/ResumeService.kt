package io.digitalheart.resume.network

import io.digitalheart.resume.models.Resume
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface ResumeService {
    @Headers("Accept: application/json")
    @GET("qbait/f7c74d3e47f37df8704efe9bca88ea1b/raw/e82208ad032af582871d552f5da5573a80f36def/resume.json")
    fun fetch(): Observable<Resume>
}