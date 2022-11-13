package us.scarandtay.csproj.api;

public interface QueueAction<T> {
    void queue();

    void complete();
}
