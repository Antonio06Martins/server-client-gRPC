package com.antonio

import com.example.DemoServerServiceGrpcKt
import com.example.SaveUserRequest
import com.example.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class DemoServerEndpoint : DemoServerServiceGrpcKt.DemoServerServiceCoroutineImplBase() {
    override suspend fun saveUser(request: SaveUserRequest): UserResponse {
        return UserResponse.newBuilder()
            .setId(1)
            .setName(request.name)
            .setLastName(request.lastName)
            .build()
    }
    override fun saveUserStream(requests: Flow<SaveUserRequest>): Flow<UserResponse> = flow {
        var id = 1
        requests.collect {
            println("Salvando usuario...")
            emit(
                UserResponse.newBuilder()
                    .setId(id++)
                    .setName(it.name)
                    .setLastName(it.lastName)
                    .build()
            )
            println("Concluido...")
        }
    }
}