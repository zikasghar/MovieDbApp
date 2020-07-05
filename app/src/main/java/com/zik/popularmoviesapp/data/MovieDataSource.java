package com.zik.popularmoviesapp.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PageKeyedDataSource;

import com.zik.popularmoviesapp.model.PopularMovie;
import com.zik.popularmoviesapp.model.TMDBApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zik.popularmoviesapp.constants.Constants.RequestType;

/**
 * uses Retrofit to get list of movies and details
 * uses Gson to convert to "PopularMovie" object
 * <p>
 * Created by Zik Asghar 06/2020
 */

public class MovieDataSource extends PageKeyedDataSource<Integer, PopularMovie> {
    public static final int PAGE_SIZE = 20;
    public static final int FIRST_PAGE = 1;
    private RequestType requestType;
    private String searchString;

    public MovieDataSource(RequestType requestType, String searchString) {
        this.requestType = requestType;
        this.searchString = searchString;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, PopularMovie> callback) {
        getCall(FIRST_PAGE, searchString)
                .enqueue(new Callback<TMDBApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TMDBApiResponse> call,
                                           @NonNull Response<TMDBApiResponse> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            callback.onResult(response.body().results, null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TMDBApiResponse> call, @NonNull Throwable t) {
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params,
                           @NonNull final LoadCallback<Integer, PopularMovie> callback) {
        getCall(params.key, searchString).
                enqueue(new Callback<TMDBApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TMDBApiResponse> call,
                                           @NonNull Response<TMDBApiResponse> response) {
                        if (response.isSuccessful()) {
                            Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                            assert response.body() != null;
                            callback.onResult(response.body().results, adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TMDBApiResponse> call, @NonNull Throwable t) {
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, PopularMovie> callback) {
        getCall(params.key, searchString)
                .enqueue(new Callback<TMDBApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TMDBApiResponse> call,
                                           @NonNull Response<TMDBApiResponse> response) {
                        assert response.body() != null;
                        boolean more = response.body().total_pages > params.key;
                        Integer key = more ? params.key + 1 : null;
                        callback.onResult(response.body().results, key);
                    }

                    @Override
                    public void onFailure(@NonNull Call<TMDBApiResponse> call, @NonNull Throwable t) {
                    }
                });
    }

    private Call<TMDBApiResponse> getCall(int page, @Nullable String searchString) {
        DBApi dbApi = RetrofitClient.getInstance().getTMDBApi();
        Call<TMDBApiResponse> call;
        switch (requestType) {
            case POPULAR:
                call = dbApi.getMoviesByPopular(page);
                break;
            case RATING:
                call = dbApi.getMoviesByRated(page);
                break;
            case SEARCH_BY:
                call = dbApi.getMovieBySearchString(searchString, page);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestType);
        }
        return call;
    }
}