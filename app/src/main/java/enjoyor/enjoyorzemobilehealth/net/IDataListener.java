package enjoyor.enjoyorzemobilehealth.net;

public interface IJsonDataListener<T> {
    void onSuccess(T m);
    void onFailure(T m);
}
