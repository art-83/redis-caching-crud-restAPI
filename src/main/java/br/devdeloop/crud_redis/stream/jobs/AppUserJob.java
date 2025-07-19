package br.devdeloop.crud_redis.stream.jobs;

public record AppUserJob(
        String username,
        String password
) {
}
