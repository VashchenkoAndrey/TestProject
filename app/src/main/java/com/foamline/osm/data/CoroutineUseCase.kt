package com.foamline.osm.data

interface CoroutineUseCase<IN : Any?, OUT : Any?> {
    suspend fun invoke(params: IN): OUT
}