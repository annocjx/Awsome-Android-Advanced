package cn.prim.http.lib_net.request;

import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.model.HttpMethod;
import cn.prim.http.lib_net.request.base.BodyRequest;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author prim
 * @version 1.0.0
 * @desc delete方式请求网络
 * @time 2019-05-06 - 17:45
 */
public class DeleteRequest<T> extends BodyRequest<T, DeleteRequest<T>> {
    public DeleteRequest(String url) {
        super(url);
    }

    @Override
    public T execute() {
        return generateExecute();
    }

    @Override
    public void enqueue(Callback<T> callback) {
        generateEnqueue(callback);
    }

    @Override
    public void enqueue() {
        generateEnqueue(callback);
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return generateService().delete(url, mParams.getCommonParams());
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.DELETE;
    }
}
