package com.audhil.medium.samplegithubapp.dispatcher

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockServerDispatcher {

    //  response dispatcher
    class ResponseDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            if (request.path.equals("/todos/1", true))
                return MockResponse().setResponseCode(200).setBody(
                    "{\n" +
                            "  \"id\" : 1,\n" +
                            "  \"title\" : \"delectus aut autem\",\n" +
                            "  \"userId\" : 1,\n" +
                            "  \"completed\" : false\n" +
                            "}"
                )
            return MockResponse().setResponseCode(400)
        }
    }

    //  error dispatcher
    class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse = MockResponse().setResponseCode(400)
    }
}